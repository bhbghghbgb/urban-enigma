'use strict'
// import {Router} from 'express';

const { Router } = require('express');
const router = Router();
// import Cart from '../../controllers/cart.controller.js';

const Cart = require('../../controllers/cart.controller.js');
//getCartOfUser
router.get('/cart/:customer', Cart.getCartOfUser);
//addToCart
router.post('/cart/:customer/cartload/:product', Cart.addToCart);
//decreaseProductOfCart
router.patch('/cart/:customer/decrease/:product', Cart.decreaseProductOfCart);
//increaseProductOfCart
router.patch('/cart/:customer/increase/:product', Cart.increaseProductOfCart);
//resetCart
router.put('/cart/:customer/reset', Cart.resetCart);
//updateCart
router.put('/cart/:customer/update', Cart.updateCart);
module.exports = router;
