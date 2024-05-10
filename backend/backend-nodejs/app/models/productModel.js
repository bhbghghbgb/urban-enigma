const mongoose = require("mongoose");

const productSchema = new mongoose.Schema({
    name: { type: String, required: true, trim: true },
    image: { type: String, required: false },
    description: { type: String, required: false },
    price: { type: Number, required: false },
    popular: { type: Boolean, required: true },
    category: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Category",
        required: true,
    },
});

const Product = mongoose.model("Product", productSchema);

module.exports = Product;
