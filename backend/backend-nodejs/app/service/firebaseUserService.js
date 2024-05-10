const FirebaseUser = require("../models/firebaseUserModel");
const { createFirebaseUserByAdmin } = require("./firebaseAuthService");

async function s_findUserByFirebaseUid(firebaseUid) {
    return await FirebaseUser.findOne({
        firebaseUid,
    });
}
async function s_findAllStaffs() {
    return await FirebaseUser.find({ role: "staff" });
}
async function s_findAllCustomers() {
    return await FirebaseUser.find({ role: "customer" });
}
async function s_createStaff(firebaseUserFields) {
    return await createFirebaseUserByAdmin({
        ...firebaseUserFields,
        role: "staff",
    });
}
async function s_createCustomer(firebaseUserFields) {
    return await createFirebaseUserByAdmin({
        ...firebaseUserFields,
        role: "customer",
    });
}
async function s_updateUser(firebaseUid, firebaseUserFields) {
    const user = await s_findUserByFirebaseUid(firebaseUid);
    // user.role khong dc update o day ma phai dung update Role
    if (firebaseUserFields.name !== undefined)
        user.name = firebaseUserFields.name;
    if (firebaseUserFields.gender !== undefined)
        user.gender = firebaseUserFields.gender;
    if (firebaseUserFields.dateOfBirth !== undefined)
        user.dateOfBirth = firebaseUserFields.dateOfBirth;
    if (firebaseUserFields.address !== undefined)
        user.address = firebaseUserFields.address;
    if (firebaseUserFields.phone !== undefined)
        user.phone = firebaseUserFields.phone;
    await user.save();
    return user;
}
async function s_deleteUser(firebaseUid) {
    return await FirebaseUser.findOneAndDelete({ firebaseUid });
}
module.exports = {
    s_findUserByFirebaseUid,
    s_findAllStaffs,
    s_findAllCustomers,
    s_createStaff,
    s_createCustomer,
    s_updateUser,
    s_deleteUser,
};
