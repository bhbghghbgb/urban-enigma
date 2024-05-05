const jwt = require("jsonwebtoken");
const Account = require("../models/accountModel");
const { ACCESS_TOKEN, TOKEN_TIME_LIFE } = require("../config/_APP");
const Token = require("../models/tokenModel");
const { ObjectId } = require("mongodb");
const authService = require("../service/authService");

exports.login = async (req, res) => {
    const { username, password } = req.body;
    try {
        const user = await Account.findOne({
            $or: [
                { username },
                { $and: [{ email: username }, { emailVerified: true }] },
                { $and: [{ phone: username }, { phoneVerified: true }] },
            ],
        });
        if (!user) {
            res.status(401).json({
                message: "Username/Email/Phone is not valid",
            });
            return;
        }
        const isValidPassword = password == user.password;
        if (!isValidPassword) {
            res.status(401).json({ message: "Password is not valid" });
            return;
        }

        const tokens = await Token.find().lean().exec();

        for (const token of tokens) {
            try {
                const decoded = jwt.verify(token.token, ACCESS_TOKEN, {
                    algorithms: ["HS256"],
                });
                if (decoded.userId == user._id) {
                    await Token.deleteOne({ _id: token._id });
                }
            } catch (error) {
                console.log(error);
            }
        }

        const token = jwt.sign({ userId: user._id }, ACCESS_TOKEN, {
            algorithm: "HS256",
        });
        await Token.create({ token });

        const userWithRole = await Account.findOne(
            { _id: user._id },
            { role: 1 }
        );
        const role = userWithRole.role;

        let roleOfAccount;
        switch (role) {
            case "admin":
                roleOfAccount = "admin";
                break;
            case "user":
                roleOfAccount = "user";
                break;
            default:
                roleOfAccount = "staff";
        }
        res.status(200).json({ token, roleOfAccount });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.logout = async (req, res) => {
    try {
        const token = req.headers.authorization;
        const deletedToken = await Token.findOneAndDelete({
            userId: new ObjectId(req.params.userId),
            token,
        });
        if (!deletedToken) {
            return res
                .status(404)
                .json({ message: "Token not found or already expired" });
        }
        res.status(200).json({ message: "Logout successful" });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.authenticate = async (req, res) => {
    const token = req.headers.authorization;
    try {
        const isValidToken = await authService.authenticate(token);
        if (!isValidToken) {
            return res
                .status(401)
                .json({ message: "Invalid token", isValid: false });
        }
        return res
            .status(200)
            .json({ message: "Token is valid", isValid: true });
    } catch (error) {
        return res
            .status(500)
            .json({ message: "Internal server error", isValid: false });
    }
};
