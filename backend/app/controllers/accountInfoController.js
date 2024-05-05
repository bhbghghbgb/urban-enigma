const AccountInfoVerify = require("../models/accountInfoVerifyModel");
import { changeEmail, changePhone } from "../service/accountInfoService";
import { generate6digit as verifyCodeGen } from "../utils/generate6digit";
exports.getAllRequests = async (req, res) => {
    try {
        res.json(AccountInfoVerify.find())
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}
exports.requestChangeAccountEmail = async (req, res) => {
    try {
        const username = req.body.username;
        const email = req.body.email;
        if (!(await checkUserNameExist(username))) {
            return res.status(404).json({ message: "Username is not exist" });
        }
        await new AccountInfoVerify({
            username,
            info: email,
            infoType: "email",
            verifyCode: verifyCodeGen(),
        }).save();
        return res
            .status(200)
            .json({ message: "Email change request created" });
    } catch (err) {
        return res.status(500).json({ message: err.message });
    }
};
exports.requestChangeAccountPhone = async (req, res) => {
    try {
        const username = req.body.username;
        const phone = req.body.phone
        if (!(await checkUserNameExist(username))) {
            return res.status(404).json({ message: "Username is not exist" });
        }
        await new AccountInfoVerify({
            username,
            info: phone,
            infoType: "phone",
            verifyCode: verifyCodeGen(),
        }).save();
        return res
            .status(200)
            .json({ message: "Phone change request created" });
    } catch (err) {
        return res.status(500).json({ message: err.message });
    }
};
exports.attemptChangeAccountEmail = async (req, res) => {
    try {
        const username = req.body.username;
        const verifyCode = req.body.verifyCode;
        if (!(await checkUserNameExist(username))) {
            return res.status(404).json({ message: "Username is not exist" });
        }
        const verifyRequest = await AccountInfoVerify.findOne({
            username,
            infoType: "email",
        });
        if (!verifyRequest) {
            return res.status(404).json({
                message: "Account does not have existing email change request",
            });
        }
        // don't use !== to allow type coerecion of digits
        if (verifyRequest.verifyCode != verifyCode) {
            return res.status(403).json({
                message: "Verification code mismatch",
            });
        }
        await changeEmail(username, verifyRequest.info);
        await verifyRequest.remove();
        return res.status(200).json({ message: "Account email changed" });
    } catch (err) {
        return res.status(500).json({ message: err.message });
    }
};
exports.attemptChangeAccountPhone = async (req, res) => {
    try {
        const username = req.body.username;
        const verifyCode = req.body.verifyCode;
        if (!(await checkUserNameExist(username))) {
            return res.status(404).json({ message: "Username is not exist" });
        }
        const verifyRequest = await AccountInfoVerify.findOne({
            username,
            infoType: "phone",
        });
        if (!verifyRequest) {
            return res.status(404).json({
                message: "Account does not have existing phone change request",
            });
        }
        // don't use !== to allow type coerecion of digits
        if (verifyRequest.verifyCode != verifyCode) {
            return res.status(403).json({
                message: "Verification code mismatch",
            });
        }
        await changePhone(username, verifyCode);
        await verifyRequest.remove();
        return res.status(200).json({ message: "Account phone changed" });
    } catch (err) {
        return res.status(500).json({ message: err.message });
    }
};
