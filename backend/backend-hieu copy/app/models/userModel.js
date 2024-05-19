const mongoose = require('mongoose')

const commonUser = new mongoose.Schema({
    name: { type: String, required: true, trim: true },
    gender: { type: String, required: true },
    dateOfBirth: { type: Date, required: true },
    phone: { type: String, required: true },
    account: { type: mongoose.Schema.Types.ObjectId, ref: 'Account', required: true }
})

const staffSchema = new mongoose.Schema({
    commonuser: commonUser,
    address: { type: String, required: true },
    position: { type: String, required: true }
})

const customerSchema = new mongoose.Schema({
    commonuser: commonUser,
    qrCode: { type: String, required: true },
    membershipPoint: { type: Number, required: true }
})

const Staff = mongoose.model('Staff', staffSchema);
const Customer = mongoose.model('Customer', customerSchema);

module.exports = {
    Staff,
    Customer
}