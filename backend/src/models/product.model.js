import {Schema, models, Types} from 'mongoose';

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
        type: Types.ObjectId,
        ref: 'Category',
        required: true
    },
    popular: {
        type: Boolean,
        default: false
    },
    rating: {
        type: Types.ObjectId,
        ref: 'Rating'
    }
}, {
    timestamps: true,
    collection: Collection_Name
});
export default models(Collection_Name, ProductSchema);