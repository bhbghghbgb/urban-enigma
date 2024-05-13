const admin = require("firebase-admin");
const Account = require("../models/accountModel");
const { Customer, Staff } = require("../models/userModel");
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
        username: firebaseUid,
    });
    // neu ko co account nghia la client yeu cau tao user moi
    return account || createAccountByClient(firebaseUid);
}
// tao user moi ma khong co them thong tin gi (do dang nhap bang firebase)
// neu can sua thong tin thi sua sau
async function createAccountByClient(firebaseUid) {
    const account = Account({
        username: firebaseUid,
        role: "customer",
    });
    await account.save();
    await default_customer(account._id, firebaseUid).save();
    return account;
}
// tao user moi bang cach admin add thu cong
// add kieu nay thi se add dc thong tin chung luon
// nho phai tao user tren firebase auth truoc roi lay id no
async function createAccountByAdmin(accountFields) {
    const account = Account({
        firebaseUid: accountFields.firebaseUid,
        role: accountFields.role || "customer",
        name: accountFields.name,
        gender: accountFields.gender,
        dateOfBirth: accountFields.dateOfBirth,
        address: accountFields.address,
        phone: accountFields.phone,
        cashbackPoint: accountFields.cashbackPoint,
    });
    await account.save();
    if (account.role === "customer") {
        await default_customer(account._id, accountFields.firebaseUid);
    } else if (account.role === "staff") {
        await default_staff(account._id, accountFields.firebaseUid);
    }
    return account;
}

const default_customer = (accountOid, username) =>
    Customer({
        qrCode: `doansgu/customer/${username}`,
        membershipPoint: 0,
        commonuser: default_commonuser(accountOid, username),
    });
const default_staff = (accountOid, username) =>
    Staff({
        address: "Somewhere in Vietnam",
        position: "unspecified",
        commonuser: default_commonuser(accountOid, username),
    });
const default_commonuser = (accountOid, username) => {
    return {
        phone: "+84.",
        name: username,
        gender: "unspecified",
        dateOfBirth: preDates20Years().toISOString(),
        account: accountOid,
    };
};
function preDates20Years() {
    const currentDate = new Date();
    currentDate.setFullYear(currentDate.getFullYear() - 20);
    return currentDate;
}
module.exports = {
    getOrCreateAccountFromIdToken,
    createAccountByClient,
    createAccountByAdmin,
};
