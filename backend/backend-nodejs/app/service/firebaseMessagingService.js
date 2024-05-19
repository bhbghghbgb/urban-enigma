const admin = require("firebase-admin");
const { StatusCodes } = require("http-status-codes");

async function send_notification(accountDocument, title, body) {
    const token = accountDocument?.notificationDeviceToken;
    if (!token) {
        const err = new Error(
            "This account have not been linked with a device token."
        );
        err.statusCode = StatusCodes.NOT_FOUND;
        throw err;
    }
    return await admin.messaging().send({
        notification: {
            title,
            body,
        },
        token,
    });
}
module.exports = {
    send_notification,
};
