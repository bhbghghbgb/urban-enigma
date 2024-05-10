const jwt = require("jsonwebtoken");
const Token = require("../models/tokenModel");
const { ACCESS_TOKEN } = require("../config/_APP");
const { Customer, Staff } = require("../models/userModel");

exports.authenticate = async (req, res, next) => {
    const token = req.headers.authorization;
    try {
        const tokenDoc = await Token.findOne({ token });
        if (!tokenDoc) {
            res.status(401).json({ message: "Invalid token" });
            return;
        }
        const decoded = jwt.verify(token, ACCESS_TOKEN, {
            algorithms: "HS256",
        });
        const custom = await Customer.findOne({
            "commonuser.account": decoded.userId,
        });
        req.user = custom._id.toString();
        next();
    } catch (error) {
        console.log(error);
        res.status(401).json({ message: "Invalid token" });
        return;
    }
};

exports.authenticateStaff = async (req, res, next) => {
    const token = req.headers.authorization;
    try {
        const tokenDoc = await Token.findOne({ token });
        if (!tokenDoc) {
            res.status(401).json({ message: "Invalid token" });
            return;
        }
        const decoded = jwt.verify(token, ACCESS_TOKEN, {
            algorithms: "HS256",
        });
        const staff = await Staff.findOne({
            "commonuser.account": decoded.userId,
        });
        req.user = staff._id.toString();
        next();
    } catch (error) {
        console.log(error);
        res.status(401).json({ message: "Invalid token" });
        return;
    }
};
