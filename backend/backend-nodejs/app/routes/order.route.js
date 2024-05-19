'use strict'

const express = require('express');
const router = express.Router();
const order = require('../controllers/oder.controller.js');

//createOrder
router.post('create', order.createOrder);


module.exports = router;