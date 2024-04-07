'use strict'
import {Schema, models, Types} from 'mongoose';
const Collection_Name = 'Cart'
const cartSchema = new Schema({
    user: { type: Types.ObjectId, ref: 'Customer', required: true, trim: true },
    total: { type: Number, required: true },
    products: [{
        product: { type: Types.ObjectId, ref: 'Product', required: true },
        amount: { type: Number, required: true },
        price: { type: Number, required: true },
    }]
});

module.exports = models(Collection_Name, cartSchema);