const mongoose = require("mongoose");
const tokenSchema = new mongoose.Schema({
    token: {
        type: String,
        required: true,
    },
    createdAt: {
        type: Date,
        default: Date.now,
        expires: "1d",
    },
});
const Token = mongoose.model("Token", tokenSchema);
module.exports = Token;
