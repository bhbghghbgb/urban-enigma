const Account = require("../models/accountModel");
const { ObjectId } = require("mongodb");

exports.findAccountByUsername = async (req, res) => {
    try {
        const account = await Account.findOne({
            username: req.params.username,
        });
        res.status(200).json(account);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
exports.findAccountByAllIdentifiable = async (req, res) => {
    try {
        const identifiable = req.params.identifiable;
        const account = await Account.findOne({
            $or: [
                { identifiable },
                { $and: [{ email: identifiable }, { emailVerified: true }] },
                { $and: [{ phone: identifiable }, { phoneVerified: true }] },
            ],
        });
        res.status(200).json(account);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
async function checkUserNameExist(username) {
    try {
        const existingUsername = await Account.countDocuments({
            username: username,
        });
        return existingUsername > 0;
    } catch (err) {
        console.log(err);
        return false;
    }
}

exports.createAccount = async (req, res) => {
    try {
        const account = new Account(req.body);
        const checkUsername = await checkUserNameExist(account.username);
        if (!checkUsername) {
            await account.save();
            res.status(201).json({ message: "Account created successfully" });
        } else {
            res.status(500).json({
                message: "This account has already existed",
            });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.changePassword = async (req, res) => {
    try {
        var myQuery = { username: req.params.username };
        var newPassword = {
            $set: {
                password: req.body.password,
            },
        };
        const checkUsername = await checkUserNameExist(req.params.username);
        if (checkUsername) {
            await Account.updateOne(myQuery, newPassword);
            res.status(201).json({ message: "Changed password successfully" });
        } else {
            res.status(500).json({ message: "Username is not exist" });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.changeRoleOfAccount = async (req, res) => {
    try {
        var myQuery = { username: req.params.username };
        var newRole = {
            $set: {
                role: req.body.role,
            },
        };
        const checkUsername = await checkUserNameExist(req.params.username);
        if (checkUsername) {
            await Account.updateOne(myQuery, newRole);
            res.status(201).json({ message: "Changed role successfully" });
        } else {
            res.status(500).json({ message: "Username is not exist" });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
