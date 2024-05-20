const mongoose = require("mongoose");

// const Product = require('./productModel');

const orderSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Customer",
        required: true,
        trim: true,
    },
    orderDateTime: { type: Date, required: true },
    status: { type: String, default: "now" },
    deliveryLocation: { type: String, default: "VietNam" },
    note: { type: String, default: "note" },
    discount: { type: Number, required: true, default: 0 },
    paymentMethod: { type: String, required: true },
    detailOrders: [
        {
            product: {
                type: mongoose.Schema.Types.ObjectId,
                ref: "Product",
                required: true,
            },
            amount: { type: Number, required: true },
            price: { type: Number, required: true },
        },
    ],
    totalPrice: { type: Number, required: true },
});

const Order = mongoose.model("Order", orderSchema);

module.exports = Order;
