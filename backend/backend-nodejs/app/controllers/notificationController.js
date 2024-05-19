const { StatusCodes } = require("http-status-codes");
const Account = require("../models/accountModel");
const { send_notification } = require("../service/firebaseMessagingService");
exports.bindDeviceToAccount = async function (req, res) {
    const account = req.user;
    account.notificationDeviceToken = req.query["device-token"];
    await account.save();
};

exports.unbindDeviceFromAccount = async function (req, res) {
    const account = req.user;
    account.notificationDeviceToken = null;
    await account.save();
};

exports.sendNotificationToAccount = async function (req, res) {
    try {
        const account = await Account.findOne({ username: req.body.account });
        const message_id = await send_notification(
            account,
            req.body.title,
            req.body.desc
        );
        res.status(StatusCodes.OK).json({
            message: "Send notification success",
            fcm_message_id: message_id,
        });
    } catch (err) {
        return res.status(err.statusCode || 500).json({ message: err.message });
    }
};
