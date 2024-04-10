'use strict'
import {Router} from 'express';

const router = Router();
import Oder from '../../controllers/oder.controller.js';
//getAllOders
router.get('/oder', Oder.getAllOders);
//findOrderById
router.get('/oder/:id', Oder.findOrderById);
//createOrder
router.post('/oder/create', Oder.createOrder);
//updateStatus
router.patch('/oder/:is/status/update', Oder.updateStatus);
//getOrdersOfStatus
router.get('/oder/status/:status', Oder.getOrdersOfStatus);