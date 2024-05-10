// import {Schema, models, Types} from 'mongoose';

const { Schema, model } = require('mongoose');
const Collection_Name = 'Product';
const ProductSchema = new Schema({
    name: {
        type: String,
        required: true
    },
    price: {
        type: Number,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    },
    category: {
        type: Schema.Types.ObjectId,
        ref: 'Category',
        required: true
    },
    popular: {
        type: Boolean,
        default: false
    },
    ratings: [{rating :{
        type: Schema.Types.ObjectId,
        ref: 'Rating',
    }}],
    avgRating: {
        type: Number,
        default: 0
    }
}, {
    timestamps: true,
    collection: Collection_Name
});
module.exports =  model(Collection_Name, ProductSchema);