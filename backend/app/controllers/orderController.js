const Order = require('../models/orderModel');
const { Customer } = require('../models/userModel');
const { ObjectId } = require('mongodb');

exports.getAllOrders = async (req, res) => {
    try {
        const orders = await Order.find().populate('detailOrders.product');
        res.status(200).json(orders);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getAllOrderByUserNotYetDelivered = async (req, res) => {
    try {
        const customer = await Customer.findOne({ _id: req.user });
        if (!customer) {
            res.status(404).json({ message: 'User not found' });
            return;
        }
        const orders = await Order.find({ status: { $ne: 'delivered' }, user: req.user }).populate({
            path: 'user',
            model: 'Customer'
        })
            .populate({
                path: 'detailOrders.product',
                populate: {
                    path: 'category'
                }
            });
        res.status(200).json(orders);
    } catch (err) {
        res.status(500).json({ message: err.message })
    }
}

exports.getAllOrderByUserDelivered = async (req, res) => {
    try {
        const customer = await Customer.findOne({ _id: req.user });
        if (!customer) {
            res.status(404).json({ message: 'User not found' });
            return;
        }
        const orders = await Order.find({ status: 'delivered', user: req.user }).populate({
            path: 'user',
            model: 'Customer'
        })
            .populate({
                path: 'detailOrders.product',
                populate: {
                    path: 'category'
                }
            });
        res.status(200).json(orders);
    } catch (err) {
        res.status(500).json({ message: err.message })
    }
}

exports.findOrderById = async (req, res) => {
    try {
        const customer = await Customer.findOne({ _id: req.user });
        if (!customer) {
            res.status(404).json({ message: 'User not found' });
            return;
        }
        const order = await Order.findOne({ _id: req.params.id, user: req.user }).populate({
            path: 'user',
            model: 'Customer'
        })
            .populate({
                path: 'detailOrders.product',
                populate: {
                    path: 'category'
                }
            });
        if (order) {
            res.status(200).json(order);
        } else {
            res.status(404).json({ message: 'Order is not found' });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}
exports.findOrderByIdAdmin = async (req, res) => {
    try {
        const order = await Order.findOne({ _id: req.params.id }).populate({
            path: 'user',
            model: 'Customer',
            populate: {
                path: 'commonuser.account',
                model: 'Account'
            }
        })
            .populate({
                path: 'detailOrders.product',
                populate: {
                    path: 'category'
                }
            });
        if (order) {
            res.status(200).json(order);
        } else {
            res.status(404).json({ message: 'Order is not found' });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.createOrder = async (req, res) => {
    try {
        const { user, orderDateTime, status, deliveryLocation, note, discount, detailOrders } = req.body;
        const parsedDetailOrders = JSON.parse(detailOrders);
        const newOrder = new Order({
            user,
            orderDateTime,
            status,
            deliveryLocation,
            note,
            discount,
            detailOrders: parsedDetailOrders,
        });
        await newOrder.save();
        res.status(200).json({ message: 'Order created successfully' });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.updateStatus = async (req, res) => {
    try {
        const order = Order.findById(req.params.id);
        if (!order) {
            res.status(404).json({ message: 'Order is not found' })
        }
        const { status } = req.body;
        var myQuery = { _id: req.params.id };
        var newData = {
            $set: {
                status: status,
            }
        }
        await Order.updateOne(myQuery, newData);
        res.status(201).json({ message: 'Order updated successfully' })
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getOrdersOfStatus = async (req, res) => {
    try {
        var numDesc = -1
        if(req.params.status == 'now') {
            numDesc = 1
        }
        const orders = await Order.find({ status: req.params.status })
            .populate('detailOrders.product')
            .sort({ orderDateTime: numDesc });
        res.status(200).json(orders);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}