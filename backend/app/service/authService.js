const Token = require('../models/tokenModel');
const jwt = require('jsonwebtoken');
const { ACCESS_TOKEN } = require('../config/_APP')

async function authenticate(token) {
    try {
        const tokenDoc = await Token.findOne({ token });
        if (!tokenDoc) {
            return false;
        }
        // const decoded = jwt.verify(token, ACCESS_TOKEN, { algorithms: 'HS256' });
        return true;
    } catch (error) {
        console.error('Error:', error);
        return false;
    }
}

module.exports = { authenticate };