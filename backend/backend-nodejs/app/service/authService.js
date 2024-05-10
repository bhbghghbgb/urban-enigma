const Token = require("../models/tokenModel");

async function authenticate(token) {
    try {
        const tokenDoc = await Token.findOne({ token });
        if (!tokenDoc) {
            return false;
        }
        return true;
    } catch (error) {
        console.error("Error:", error);
        return false;
    }
}

module.exports = { authenticate };
