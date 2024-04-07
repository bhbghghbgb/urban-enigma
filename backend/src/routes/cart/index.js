'use strict'
import {Router} from 'express';

const router = Router();
import Cart from '../../controllers/cart.controller.js';

//getCartOfUser
router.get('/cart/getCartOfUser', Cart.getCartOfUser);
//addToCart
router.post('/cart/addProductToCart', Cart.addToCart);
//decreaseProductOfCart
router.patch('/cart/decreaseProductOfCart', Cart.decreaseProductOfCart);
//increaseProductOfCart
router.patch('/cart/deleteProductOfCart', Cart.increaseProductOfCart);
//resetCart
router.patch('/cart/resetCart', Cart.resetCart);
module.export = router;
