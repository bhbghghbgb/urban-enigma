const { Schema, model } = require("mongoose");

const firebaseUserSchema = new Schema({
    firebaseUid: { type: String, required: true },
    role: {type: String, default: "customer"},
    name: { type: String, required: false, trim: true },
    gender: { type: String, required: false },
    dateOfBirth: { type: Date, required: false },
    address: { type: String, required: false },
    phone: { type: String, required: false },
    cashbackPoint: { type: Number, default: 0 },
});

const FirebaseUser = model("FirebaseUser", firebaseUserSchema);
module.exports = FirebaseUser;
