const Account = require("../models/accountModel");
const { checkUserNameExist } = require("../controllers/accountController");
exports.changeEmail = async (username, newEmail) => {
    if (!(await checkUserNameExist(username))) {
        throw new Error("Username is not exist");
    }
    await Account.updateOne(
        { username },
        {
            $set: {
                email: newEmail,
                emailVerified: true,
            },
        },
    );
};

exports.changePhone = async (username, newPhone) => {
    if (!(await checkUserNameExist(username))) {
        throw new Error("Username is not exist");
    }
    await Account.updateOne(
        { username },
        {
            $set: {
                phone: newPhone,
                phoneVerified: true,
            },
        },
    );
};
