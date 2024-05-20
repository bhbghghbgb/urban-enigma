const mongoose = require('mongoose');

const ratingSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'Customer', required: true, trim: true },
    product: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
    rating: { type: Number, required: true, min: 0, max: 5 }
});

const Rating = mongoose.model('Rating', ratingSchema);

module.exports = Rating;