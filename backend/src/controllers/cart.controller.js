'use strict'
import {
    getCartOfUser as _getetCartOfUser,
    addToCart as _addToCart,
    increaseProductOfCart as _increaseProductOfCart,
    decreaseProductOfCart as _decreaseProductOfCart,
    resetCart as _resetCart

} from '../services/cart.service';

class cartController{
    getCartOfUser = async (req, res) => {
        try {
            const cart = await _getetCartOfUser(req.params.user);
            res.status(200).json(cart);
        } catch (error) {
            res.status(error.statusCode).json({message: error.message});
        }
    }

    addToCart = async (req, res) => {
        try {
            await _addToCart(req.params.user, req.params.product);
            res.status(200).json({ message: 'Add to cart successfully!' });
        } catch (error) {
            res.status(error.statusCode).json({message: error.message});
        }
    }

    decreaseProductOfCart = async (req, res) => {
        try {
            const cart = await _decreaseProductOfCart(req.params.user, req.params.product);
            res.status(200).json({ message: 'Decrease product of cart successfully!' });
        } catch (error) {
            res.status(error.statusCode).json({message: error.message});
        }
    }

    increaseProductOfCart = async (req, res) => {
        try {
            const cart = await _increaseProductOfCart(req.params.user, req.params.product);
            res.status(200).json({ message: 'Increase product of cart successfully!' });
        } catch (error) {
            res.status(error.statusCode).json({message: error.message});
        }
    }

    resetCart = async (req, res) => {
        try {
            const cart = await _resetCart(req.params.user);
            res.status(200).json({ message: 'Reset cart successfully!' });
        } catch (error) {
            res.status(error.statusCode).json({message: error.message});
        }
    }
}
const Cart = new cartController();
module.export = Cart;