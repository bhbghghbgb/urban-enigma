const { Customer, Staff } = require("../models/userModel");
const { Document } = require("mongoose");
async function account2customer(accountDocument) {
    if (!accountDocument) {
        throw new Error("Null accountDocument, forgot to add auth middleware?");
    }
    if (!(accountDocument instanceof Document)) {
        throw new Error(
            `!(accountDocument instanceof Document) (type ${typeof accountDocument}), don't use Account._id`,
        );
    }
    const role = accountDocument.role;
    if (accountDocument.role !== "customer") {
        throw new Error(`Using account2customer, but account role is ${role}.`);
    }
    const account_id = accountDocument._id;
    const customer = await Customer.findOne({
        "commonuser.account": account_id,
    });
    if (!customer) {
        throw new Error(
            `Account ${account_id} is not referenced by any Customer.commonuser.account`,
        );
    }
    return customer;
}

async function account2staff(accountDocument) {
    if (!accountDocument) {
        throw new Error(
            "Null Account Document, forgot to add auth middleware?",
        );
    }
    if (!(accountDocument instanceof Document)) {
        throw new Error(
            `!(accountDocument instanceof Document) (type ${typeof accountDocument}), don't use Account._id`,
        );
    }
    const role = accountDocument.role;
    if (accountDocument.role !== "staff") {
        throw new Error(`Using account2staff, but account role is ${role}.`);
    }
    const account_id = accountDocument._id;
    const staff = await Staff.findOne({ "commonuser.account": account_id });
    if (!staff) {
        throw new Error(
            `Account ${account_id} is not referenced by any Staff.commonuser.account`,
        );
    }
    return staff;
}

module.exports = {
    account2customer,
    account2staff,
};
