'use strict'
import {Schema, model} from 'mongoose'

const commonUser = new Schema({
    name: {type: String, required: true, trim: true},
    gender: {type: String, required: true},
    dateOfBirth: {type: Date, required: true},
    phone: {type: String, required: true},
    account: {type: Schema.Types.ObjectId, ref: 'Account', required: true}
}, {
    timestamps: true,
})

const staffSchema = new Schema({
    commonuser: commonUser,
    address: {type: String, required: true},
    position: {type: String, required: true}
}, {
    timestamps: true,
    collection: "Staff"
})

const customerSchema = new Schema({
    commonuser: commonUser,
    qrCode: {type: String, required: true},
    membershipPoint: {type: Number, required: true}
}, {
    timestamps: true,
    collection: "Customer"
})

const Staff = model('Staff', staffSchema);
const Customer = model('Customer', customerSchema);

module.exports = {Staff, Customer}