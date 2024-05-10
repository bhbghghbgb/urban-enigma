const Delivery = require("../models/deliveryModel");
const { Staff } = require("../models/userModel");
const Order = require("../models/orderModel");

exports.createNewDelivery = async (req, res) => {
    try {
        const { orderId, staffId } = req.body;
        const order = Order.findById(orderId);
        if (!order) {
            res.status(400).json({ message: "Order is not found" });
            return;
        }
        const staff = Staff.findById(staffId);
        if (!staff) {
            res.status(400).json({ message: "Staff is not found" });
            return;
        }
        const newDelivery = new Delivery({
            order: orderId,
            staff: staffId,
            locationNow: { latitude: 0, longitude: 0 },
            status: "delivering",
        });
        await newDelivery.save();
        res.status(200).json({ message: "Successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.changeStatusDelivery = async (req, res) => {
    try {
        const delivery = await Delivery.findById(req.params.id);
        if (!delivery) {
            res.status(400).json({ message: "Delivery is not found" });
            return;
        }
        delivery.status = "delivered";
        await delivery.save();
        res.status(200).json({ message: "Successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return;
    }
};

exports.findAvailableStaff = async (req, res) => {
    try {
        const deliveringDeliveries = await Delivery.find({
            status: "delivering",
        });
        const deliveringStaffIds = deliveringDeliveries.map(
            (delivery) => delivery.staff,
        );
        const availableStaff = await Staff.find({
            _id: { $nin: deliveringStaffIds },
        });
        res.status(200).json(availableStaff);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.getOrdersByStaff = async (req, res) => {
    try {
        const staff = await Staff.findOne({ _id: req.user }).populate(
            "commonuser.account",
        );
        if (!staff) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        const deliveries = await Delivery.findOne({
            staff: staff,
            status: "delivering",
        });
        if (deliveries) {
            const orderIds = deliveries.order;
            const orders = await Order.findOne({ _id: orderIds })
                .populate({
                    path: "user",
                    model: "Customer",
                    populate: {
                        path: "commonuser.account",
                        model: "Account",
                    },
                })
                .populate({
                    path: "detailOrders.product",
                    populate: {
                        path: "category",
                    },
                });
            res.status(200).json(orders);
            return;
        }
        res.status(200).json(null);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return;
    }
};

exports.findOrderOfStaff = async (req, res) => {
    try {
        const staff = await Staff.findOne({ _id: req.user }).populate(
            "commonuser.account",
        );
        if (!staff) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        const deliveries = await Delivery.find({
            staff: staff,
            status: "delivered",
        });
        const orderIds = deliveries.map((item) => item.order);
        const orders = await Order.find({ _id: { $in: orderIds } })
            .populate({
                path: "user",
                model: "Customer",
                populate: {
                    path: "commonuser.account",
                    model: "Account",
                },
            })
            .populate({
                path: "detailOrders.product",
                populate: {
                    path: "category",
                },
            });
        res.status(200).json(orders);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return;
    }
};
