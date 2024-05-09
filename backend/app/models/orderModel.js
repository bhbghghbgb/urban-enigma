const mongoose = require('mongoose');

// const Product = require('./productModel');

const orderSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'Customer', required: true, trim: true },
    orderDateTime: { type: Date, required: true },
    status: { type: String, required: true },
    deliveryLocation: { type: String, required: true },
    note: { type: String, required: true },
    discount: { type: Number, required: true },
    paymentMethod: { type: String, required: true },
    detailOrders: [{
        product: { type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true },
        amount: { type: Number, required: true }
    }]
});

const Order = mongoose.model('Order', orderSchema);

module.exports = Order;