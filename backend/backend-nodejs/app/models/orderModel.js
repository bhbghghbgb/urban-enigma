const mongoose = require("mongoose");

const orderSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "FirebaseUser",
        required: true,
    },
    orderDateTime: { type: Date, required: true },
    status: { type: String, required: true },
    deliveryLocation: { type: String, required: true },
    note: { type: String, required: false },
    discount: { type: Number, required: false },
    paymentMethod: { type: String, required: true },
    products: [
        {
            product: {
                type: mongoose.Schema.ObjectId,
                ref: "Product",
                required: true,
            },
            amount: { type: Number, required: true },
        },
    ],
});

const Order = mongoose.model("Order", orderSchema);

module.exports = Order;
