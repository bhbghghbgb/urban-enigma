'use strict'
import {Router} from 'express';

const router = Router();
import Cart from '../../controllers/cart.controller.js';

//getCartOfUser
router.get('/cart/:user', Cart.getCartOfUser);
//addToCart
router.post('/cart/:user/cartload/:product', Cart.addToCart);
//decreaseProductOfCart
router.patch('/cart/:user/decrease/:product', Cart.decreaseProductOfCart);
//increaseProductOfCart
router.patch('/cart/:user/increase/:product', Cart.increaseProductOfCart);
//resetCart
router.put('/cart/:user/reset', Cart.resetCart);
module.export = router;
