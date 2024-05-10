'use strict'
// import {Schema, models, Types} from 'mongoose';
const { Schema, model, Types } = require('mongoose');
const Collection_Name = 'Cart'
const cartSchema = new Schema({
    customer: { type: Types.ObjectId, ref: 'Customer', required: true, trim: true },
    total: { type: Number, required: true },
    products: [{
        product: { type: Types.ObjectId, ref: 'Product', required: true },
        amount: { type: Number, required: true },
        price: { type: Number, required: true },
    }]
}, {
    timestamps: true,
    collection: Collection_Name
});

module.exports = model(Collection_Name, cartSchema);