const { Staff, Customer } = require("../models/userModel");
const { ObjectId } = require("mongoose").Types;
const { account2staff, account2customer } = require("../service/account2shits");
const Account = require('../models/accountModel');
exports.getStaffs = async (req, res) => {
    try {
        const staffs = await Staff.find().populate("commonuser.account");
        res.status(200).json(staffs);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.getUsernameAndName = async (req, res) => {
    try {
        const staffs = await Staff.find().populate('commonuser.account', 'username');
        const staffData = staffs.map(staff => {
            return {
                name: staff.commonuser.name,
                username: staff.commonuser.account.username
            };
        });
        const customers = await Customer.find().populate('commonuser.account', 'username');
        const customerData = customers.map(customer => {
            return {
                name: customer.commonuser.name,
                username: customer.commonuser.account.username
            };
        });
        const userData = staffData.concat(customerData);
        res.json(userData);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getInfo = async (req, res) => {
    try {
        const user = await (
            await account2staff(req.user)
        ).populate("commonuser.account");
        if (!user) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        res.status(200).json(user);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return;
    }
};

exports.getCustomerInfo = async (req, res) => {
    try {
        const user = await (
            await account2customer(req.user)
        ).populate("commonuser.account");
        if (!user) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        res.status(200).json(user);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return;
    }
};

exports.getCustomers = async (req, res) => {
    try {
        const customers = await Customer.find().populate("commonuser.account");
        res.status(200).json(customers);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.createStaff = async (req, res) => {
    try {
        const { name, gender, dateOfBirth, phone, account, address, position } =
            req.body;
        const newStaff = new Staff({
            commonuser: {
                name: name,
                gender: gender,
                dateOfBirth: dateOfBirth,
                phone: phone,
                account: account,
            },
            address: address,
            position: position,
        });
        await newStaff.save();
        res.status(200).json({ message: "Create staff successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.createCustomer = async (req, res) => {
    try {
        const {
            name,
            gender,
            dateOfBirth,
            phone,
            account,
            qrCode,
            membershipPoint,
        } = req.body;
        const newCustomer = new Customer({
            commonuser: {
                name: name,
                gender: gender,
                dateOfBirth: dateOfBirth,
                phone: phone,
                account: account,
            },
            qrCode: qrCode,
            membershipPoint: membershipPoint,
        });
        await newCustomer.save();
        res.status(200).json({ message: "Create customer successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.updateStaff = async (req, res) => {
    try {
        const { name, gender, dateOfBirth, phone, address, position } =
            req.body;
        const staffId = req.params.id;
        const staff = await Staff.findById(staffId);
        if (!staff) {
            return res.status(404).json({ message: "Staff is not found" });
        }
        staff.commonuser.name = name;
        staff.commonuser.gender = gender;
        staff.commonuser.dateOfBirth = dateOfBirth;
        staff.commonuser.phone = phone;
        staff.address = address;
        staff.position = position;
        await staff.save();
        res.status(200).json({ message: "Staff updated successfully", staff });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.updateCustomer = async (req, res) => {
    try {
        const { name, gender, dateOfBirth, phone, qrCode, membershipPoint } =
            req.body;
        const customerId = req.params.id;
        const customer = await Customer.findById(customerId);
        if (!customer) {
            return res.status(404).json({ message: "Customer is not found" });
        }
        customer.commonuser.name = name;
        customer.commonuser.gender = gender;
        customer.commonuser.dateOfBirth = dateOfBirth;
        customer.commonuser.phone = phone;
        customer.qrCode = qrCode;
        customer.membershipPoint = membershipPoint;
        await customer.save();
        res.status(200).json({
            message: "Staff updated successfully",
            customer,
        });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.deleteStaff = async (req, res) => {
    try {
        const staff = Staff.findById(req.params.id);
        if (!staff) {
            res.status(404).json({ message: "Staff is not found" });
            return;
        }
        await Staff.deleteOne({ _id: new ObjectId(req.params.id) });
        res.status(200).json({ message: "Deleted staff successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.increaseMembershipPoint = async (req, res) => {
    try {
        const { username, point } = req.body;
        const account = await Account.findOne({ username: username });
        if (!account) {
            res.status(404).json({ message: "Account is not found" });
            return;
        }
        const customer = await Customer.findOne({
            "commonuser.account": account._id,
        }).populate("commonuser.account");
        if (!customer) {
            res.status(404).json({ message: "Customer is not found" });
            return;
        }
        customer.membershipPoint += point;
        await customer.save()
        res.status(200).json({
            message: "Increate Membership Point successfully",
            customer
        });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}
