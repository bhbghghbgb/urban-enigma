const admin = require("firebase-admin");
async function getFirebaseUidFromIdToken(firebaseIdToken) {
    return await admin.auth().verifyIdToken(firebaseIdToken)
}
