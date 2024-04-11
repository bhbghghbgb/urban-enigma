'use strict'
import {Schema, model, Types} from "mongoose"
const Collection_Name = "Order"

const orderSchema = new Schema({
    customer: { type: Types.ObjectId, ref: 'Customer', required: true, trim: true },
    orderDateTime: { type: Date, required: true },
    status: { type: String, required: true },
    deliveryLocation: { type: String, required: true },
    note: { type: String, required: true },
    discount: { type: Number, required: true },
    detailOrders: [{
        product: { type: Types.ObjectId, ref: 'Product', required: true },
        amount: { type: Number, required: true }
    }]
}, {
    timestamps: true,
    collection: Collection_Name
});


module.exports =  model(Collection_Name, orderSchema);