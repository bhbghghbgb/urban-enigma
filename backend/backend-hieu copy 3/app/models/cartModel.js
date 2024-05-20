const mongoose = require('mongoose');
// const Product = require('./productModel');

const cartSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'Customer', required: true, trim: true },
    total: { type: Number, required: true },
    products: [{
        product: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
        amount: { type: Number, required: true },
        price: { type: Number, required: true },
    }]
});

const Cart = mongoose.model('Cart', cartSchema);

module.exports = Cart;

