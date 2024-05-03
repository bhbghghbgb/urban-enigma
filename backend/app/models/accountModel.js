const mongoose = require('mongoose');

const accountSchema = new mongoose.Schema({
    username: { type: String, required: true, trim: true },
    password: { type: String, required: true },
    role: { type: String, required: true }
});

const Account = mongoose.model('Account', accountSchema);

module.exports = Account;