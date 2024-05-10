const admin = require("firebase-admin");
const FirebaseUser = require("../models/firebaseUserModel");
async function getFirebaseUserFromIdToken(firebaseIdToken) {
    const decodedIdToken = await admin.auth().verifyIdToken(firebaseIdToken);
    const firebaseUid = decodedIdToken?.uid;
    if (!firebaseUid) {
        return null;
    }
    const firebaseUser = await FirebaseUser.findOne({
        firebaseUid,
    });
    // client yeu cau tao user moi
    return !firebaseUser ? createFirebaseUserByClient(firebaseUid) : null;
}
// tao user moi ma khong co them thong tin gi (do dang nhap bang firebase)
// neu can sua thong tin thi sua sau
async function createFirebaseUserByClient(firebaseUid) {
    const user = FirebaseUser({ firebaseUid, role: "customer" });
    await user.save();
    return user;
}
// tao user moi bang cach admin add thu cong
// add kieu nay thi se add dc thong tin chung luon
// nho phai tao user tren firebase auth truoc roi lay id no
async function createFirebaseUserByAdmin(firebaseUserFields) {
    const user = FirebaseUser({
        firebaseUid: firebaseUserFields.firebaseUid,
        role: firebaseUserFields.role || "customer",
        name: firebaseUserFields.name,
        gender: firebaseUserFields.gender,
        dateOfBirth: firebaseUserFields.dateOfBirth,
        address: firebaseUserFields.address,
        phone: firebaseUserFields.phone,
        cashbackPoint: firebaseUserFields.cashbackPoint,
    });
    await user.save();
    return user;
}
module.exports = {
    getFirebaseUserFromIdToken,
    createFirebaseUserByClient,
    createFirebaseUserByAdmin,
};
