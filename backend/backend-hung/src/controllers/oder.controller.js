'use strict'
// import {
//     getAllOrders as _getAllOders,
//     findOrderById as _findOrderById,
//     createOrder as _createOrder,
//     updateStatus as _updateStatus,
//     getOrdersOfStatus as _getOrdersOfStatus
// } from '../../../../WebstormProjects/CofeeBackEnd/src/services/oder.service';

const oderService = require('../services/oder.service');
class oderController {
    getAllOders = async (req, res) => {
        try {
            const orders = await oderService.getAllOrders();
            res.status(200).json(orders);
        } catch (error) {
            res.status(500).json(error);
        }
    }

    findOrderById = async (req, res) => {
        try {
            const order = await oderService.findOrderById(req.params.id);
            if (order) {
                res.status(200).json(order);
            }
        } catch (error) {
            res.status(error.statusCode ? error.statusCode : 500).json(error.message);
        }
    }

    createOrder = async (req, res) => {
        try {
            const order = await oderService.createOrder(req.body);
            res.status(200).json({ message: 'Order created successfully' });
        } catch (error) {
            res.status(500).json(error.message);
        }
    }

    updateStatus = async (req, res) => {
        try {
            console.log("[P]:::Update Status")
            const order = await oderService.updateStatus(req.params.id, req.body.status);
            res.status(200).json({ message: 'Order updated successfully' });
        } catch (error) {
            res.status(error.statusCode ? error.statusCode : 500).json(error.message);
        }
    }

    getOrdersOfStatus = async (req, res) => {
        try {
            const orders = await oderService.getOrdersOfStatus(req.params.status);
            res.status(200).json(orders);
        } catch (error) {
            res.status(error.statusCode ? error.statusCode : 500).json(error.message);
        }
    }
}

module.exports = new oderController();