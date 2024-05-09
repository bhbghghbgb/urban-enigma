const mongoose = require('mongoose');

const deliverySchema = new mongoose.Schema({
    order: { type: mongoose.Schema.Types.ObjectId, ref: 'Order', required: true, trim: true },
    staff: { type: mongoose.Schema.Types.ObjectId, ref: 'Staff', required: true, trim: true },
    locationNow: {
        latitude: { type: Number, required: true },
        longitude: { type: Number, required: true }
    },
    status: { type: String, required: true },
});

const delivery = mongoose.model('Delivery', deliverySchema);

module.exports = delivery;