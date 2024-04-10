'use strict'
import {
    getAllOrders as _getAllOders,
    findOrderById as _findOrderById,
    createOrder as _createOrder,
    updateStatus as _updateStatus,
    getOrdersOfStatus as _getOrdersOfStatus
} from '../services/oder.service';

class oderController {
    getAllOders = async (req, res) => {
        try {
            const orders = await _getAllOders();
            res.status(200).json(orders);
        } catch (error) {
            res.status(500).json(error);
        }
    }

    findOrderById = async (req, res) => {
        try {
            const order = await _findOrderById(req.params.id);
            if (order) {
                res.status(200).json(order);
            } else {
                res.status(404).json({message: 'Order not found'});
            }
        } catch (error) {
            res.status(error.statusCode).json(error.message);
        }
    }

    createOrder = async (req, res) => {
        try {
            const order = await _createOrder(req.body);
            res.status(200).json({ message: 'Order created successfully' });
        } catch (error) {
            res.status(500).json(error.message);
        }
    }

    updateStatus = async (req, res) => {
        try {
            const order = await _updateStatus(req.params.id, req.body.status);
            res.status(200).json({ message: 'Order updated successfully' });
        } catch (error) {
            res.status(error.statusCode).json(error.message);
        }
    }

    getOrdersOfStatus = async (req, res) => {
        try {
            const orders = await _getOrdersOfStatus(req.params.status);
            res.status(200).json(orders);
        } catch (error) {
            res.status(error.statusCode).json(error.message);
        }
    }
}

module.exports = new oderController();