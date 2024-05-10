const admin = require("firebase-admin");
const { FIREBASE_AUTH_USER_TEST_EMAIL } = require("../config/_APP");
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
module.exports = { testFirebaseAuth };
