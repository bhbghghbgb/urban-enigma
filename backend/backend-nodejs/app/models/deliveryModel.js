const mongoose = require("mongoose");

const deliverySchema = new mongoose.Schema({
    order: {
        type: mongoose.Schema.ObjectId,
        ref: "Order",
        required: true,
    },
    staff: {
        type: mongoose.Schema.ObjectId,
        ref: "FirebaseUser",
        required: true,
    },
    locationNow: {
        latitude: { type: Number, required: true },
        longitude: { type: Number, required: true },
    },
    status: { type: String, required: true },
});

const delivery = mongoose.model("Delivery", deliverySchema);

module.exports = delivery;
