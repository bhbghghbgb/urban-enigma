const mongoose = require("mongoose");

const ratingSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.ObjectId,
        ref: "FirebaseUser",
        required: true,
    },
    product: {
        type: mongoose.Schema.ObjectId,
        ref: "Product",
        required: true,
    },
    rating: { type: Number, required: true, min: 0, max: 5 },
});

const Rating = mongoose.model("Rating", ratingSchema);

module.exports = Rating;
