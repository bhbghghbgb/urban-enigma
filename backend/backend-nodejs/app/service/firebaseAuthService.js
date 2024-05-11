const admin = require("firebase-admin");
const Account = require("../models/accountModel");
async function getOrCreateAccountFromIdToken(firebaseIdToken) {
    if (!firebaseIdToken) {
        return null;
    }
    const decodedIdToken = await admin.auth().verifyIdToken(firebaseIdToken);
    const firebaseUid = decodedIdToken?.uid;
    if (!firebaseUid) {
        return null;
    }
    const account = await Account.findOne({
        firebaseUid,
    });
    // client yeu cau tao user moi
    return !account ? createAccountByClient(firebaseUid) : null;
}
// tao user moi ma khong co them thong tin gi (do dang nhap bang firebase)
// neu can sua thong tin thi sua sau
async function createAccountByClient(firebaseUid) {
    const user = Account({
        username: firebaseUid,
        role: "customer",
    });
    await user.save();
    return user;
}
// tao user moi bang cach admin add thu cong
// add kieu nay thi se add dc thong tin chung luon
// nho phai tao user tren firebase auth truoc roi lay id no
async function createAccountByAdmin(accountFields) {
    const user = Account({
        firebaseUid: accountFields.firebaseUid,
        role: accountFields.role || "customer",
        name: accountFields.name,
        gender: accountFields.gender,
        dateOfBirth: accountFields.dateOfBirth,
        address: accountFields.address,
        phone: accountFields.phone,
        cashbackPoint: accountFields.cashbackPoint,
    });
    await user.save();
    return user;
}
module.exports = {
    getOrCreateAccountFromIdToken,
    createAccountByClient,
    createAccountByAdmin,
};
