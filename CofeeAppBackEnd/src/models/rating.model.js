'use strict'
// import {Schema, models, Types} from 'mongoose';

const { Schema, model, Types } = require('mongoose');
const Collection_Name = 'Rating'
const ratingSchema = new Schema({
    customer: {type: Types.ObjectId, ref: 'Customer', required: true, trim: true},
    product: {type: Types.ObjectId, ref: 'Product', required: true},
    rating: {type: Number, required: true, min: 0, max: 5}
}, {
    timestamps: true,
    collection: Collection_Name
});
module.exports = model(Collection_Name, ratingSchema);