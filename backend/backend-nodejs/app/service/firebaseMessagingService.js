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
    
    const message = {
        notification: {
            title,
            body,
        },
        token: token,
    };

    try {
        const response = await admin.messaging().send(message);
        console.log("Successfully sent message:", response);
        return response;
    } catch (error) {
        console.error("Error sending message:", error);
        throw error;
    }
}
module.exports = {
    send_notification,
};
