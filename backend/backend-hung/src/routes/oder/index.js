'use strict'

const express = require('express');
const router = express.Router();
const order = require('../../controllers/oder.controller.js');
//getAllorders
router.get('/orders', order.getAllOders);
//findOrderById
router.get('/order/:id', order.findOrderById);
//createOrder
router.post('/order/create', order.createOrder);
//updateStatus
router.patch('/order/:id/status/update', order.updateStatus);
//getOrdersOfStatus
router.get('/order/status/:status', order.getOrdersOfStatus);
module.exports = router;