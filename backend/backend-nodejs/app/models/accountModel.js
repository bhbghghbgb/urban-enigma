const mongoose = require("mongoose");

const accountSchema = new mongoose.Schema({
    username: { type: String, required: true, trim: true },
    // password: { type: String, required: true },
    role: { type: String, required: true },
    email: { type: String, required: false },
    emailVerified: { type: String, default: false },
    phone: { type: String, required: false },
    phoneVerified: { type: String, default: false },
});

const Account = mongoose.model("Account", accountSchema);

module.exports = Account;
