const admin = require("firebase-admin");
const { FIREBASE_AUTH_USER_TEST_EMAIL } = require("../config/_APP");
const Account = require("../models/accountModel");
async function testFirebaseAuth() {
    try {
        const userRecord = await admin
            .auth()
            .getUserByEmail(FIREBASE_AUTH_USER_TEST_EMAIL);
        console.info(
            `Successfully test firebase auth fetched user data: ${JSON.stringify(
                userRecord.toJSON()
            )}`
        );
        return true;
    } catch (error) {
        console.error("Error test firebase auth fetching user data:", error);
        return false;
    }
}
async function checkUsernameExist(username) {
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
module.exports = { testFirebaseAuth, checkUsernameExist };
