const express = require("express");
const app = express();
const cors = require("cors");
const bodyParser = require("body-parser");
const db = require("./app/config/connect");

// Routes
const productRoutes = require("./app/routes/productRouter");
const categoryRoutes = require("./app/routes/categoryRouter");
const accountRoutes = require("./app/routes/accountRouter");
const orderRoutes = require("./app/routes/orderRouter");
const cartRoutes = require("./app/routes/cartRouter");
const ratingRoutes = require("./app/routes/ratingRouter");
const userRoutes = require("./app/routes/userRouter");
const authRoutes = require("./app/routes/authRouter");
const deliveryRoutes = require("./app/routes/deliveryRouter");
const accountInfoRoutes = require("./app/routes/accountInfoRouter");

// Middleware
const { authenticate } = require("./app/middleware/authenticate");
const { ipAccessControl } = require("./app/middleware/ipAccessControl");
const { error2jsonHandler } = require("./app/middleware/error2jsonHandler");
app.use(ipAccessControl);

// Sử dụng body-parser middleware
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());
app.use(express.json());
app.use(error2jsonHandler);
app.use("/products", productRoutes);
app.use("/category", categoryRoutes);
app.use("/account", accountRoutes);
app.use("/orders", orderRoutes);
app.use("/cart", authenticate, cartRoutes);
app.use("/rating", ratingRoutes);
app.use("/user", userRoutes);
app.use("", authRoutes);
app.use("/delivery", deliveryRoutes);
app.use("/accountinfo", accountInfoRoutes);

// API để thống kê doanh thu trong một ngày
app.get("/revenue/date", async (req, res) => {
    // Tìm các đơn hàng được giao trong ngày đã cho
    var myQuery = [
        {
            $match: {
                orderDateTime: {
                    $regex: req.params.date,
                    $options: "i",
                },
                status: "delivered",
            },
        },
        {
            $group: {
                _id: null,
                totalRevenue: {
                    $sum: {
                        $subtract: [
                            {
                                $add: [
                                    {
                                        $reduce: {
                                            input: "$detailOrders",
                                            initialValue: 0,
                                            in: {
                                                $add: [
                                                    "$$value",
                                                    {
                                                        $multiply: [
                                                            "$$this.amount",
                                                            "$$this.product.price",
                                                        ],
                                                    },
                                                ],
                                            },
                                        },
                                    },
                                    "$deliveryFee",
                                ],
                            },
                            {
                                $multiply: [
                                    "$discount",
                                    {
                                        $reduce: {
                                            input: "$detailOrders",
                                            initialValue: 0,
                                            in: {
                                                $add: [
                                                    "$$value",
                                                    {
                                                        $multiply: [
                                                            "$$this.amount",
                                                            "$$this.product.price",
                                                        ],
                                                    },
                                                ],
                                            },
                                        },
                                    },
                                ],
                            },
                        ],
                    },
                },
            },
        },
    ];
    const check = await db.collection("Order").aggregate(myQuery).toArray();
    res.json(check);
    db.close();
});

// API endpoint để thống kê doanh thu trong một tháng
app.get("/revenue/month/:year/:month", async (req, res) => {
    try {
        const year = req.params.year;
        const month = req.params.month;
        myQuery = [
            {
                $match: {
                    orderDateTime: {
                        $regex: year + "/" + month,
                        $options: "i",
                    },
                    status: "delivered",
                },
            },
            {
                $group: {
                    _id: null,
                    totalRevenue: {
                        $sum: {
                            $subtract: [
                                {
                                    $add: [
                                        {
                                            $reduce: {
                                                input: "$detailOrders",
                                                initialValue: 0,
                                                in: {
                                                    $add: [
                                                        "$$value",
                                                        {
                                                            $multiply: [
                                                                "$$this.amount",
                                                                "$$this.product.price",
                                                            ],
                                                        },
                                                    ],
                                                },
                                            },
                                        },
                                        "$deliveryFee",
                                    ],
                                },
                                {
                                    $multiply: [
                                        "$discount",
                                        {
                                            $reduce: {
                                                input: "$detailOrders",
                                                initialValue: 0,
                                                in: {
                                                    $add: [
                                                        "$$value",
                                                        {
                                                            $multiply: [
                                                                "$$this.amount",
                                                                "$$this.product.price",
                                                            ],
                                                        },
                                                    ],
                                                },
                                            },
                                        },
                                    ],
                                },
                            ],
                        },
                    },
                },
            },
        ];
        const check = await db.collection("Order").aggregate(myQuery).toArray();
        res.json(check);
        db.close();
    } catch (err) {
        console.log(err);
    }
});

app.listen(3001, function (req, res) {
    console.log("Hello");
});
