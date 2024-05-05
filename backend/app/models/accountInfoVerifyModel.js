const mongoose = require("mongoose");

const accountInfoVerifySchema = new mongoose.Schema({
    username: { type: String, required: true, trim: true },
    info: {type: String, required: true, trim: true}, 
    infoType: { type: String, required: true }, // email or phone
    verifyCode: { type: String, required: true },
});

const AccountInfoVerify = mongoose.model("AccountInfoVerify", accountInfoVerifySchema);

module.exports = AccountInfoVerify;
