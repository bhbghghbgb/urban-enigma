const admin = require("firebase-admin");
async function getFirebaseUserFromUid(firebaseUid) {
    try {
        const userRecord = await admin.auth().getUser(firebaseUid);
        console.info(userRecord);
        return userRecord.toJSON();
    } catch (err) {
        console.error(err);
        return null;
    }
}
module.exports = {
    getFirebaseUserFromUid,
};
