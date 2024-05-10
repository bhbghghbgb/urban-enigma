const mongoose = require("mongoose");

const cartSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "FirebaseUser",
        required: true,
    },
    total: { type: Number, required: true },
    products: [
        {
            product: {
                type: mongoose.Schema.ObjectId,
                ref: "Product",
                required: true,
            },
            amount: { type: Number, required: true },
            price: { type: Number, required: true },
        },
    ],
});

const Cart = mongoose.model("Cart", cartSchema);

module.exports = Cart;
