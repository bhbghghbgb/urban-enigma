'use strict'
import Cart from '../models/cart.model';
import {Product} from '../models/product.model';
import {Customer} from '../models/user.model.js';

const getCartOfUser = async (_customer) => {
    try {
        const customer = await Customer.findOne({user: _customer});
        if (!customer) {
            const error = new Error('Customer not found');
            error.statusCode = 404;
            return;
        }
        return await findOne({user: user}).populate('products.product');
    } catch (err) {
        throw err;
    }
}

const addToCart = async (_custumer, product) => {
    try {
        const user = req.params.user;
        const customer = await Customer.findOne({user: _customer});
        if (!customer) {
            const error = new Error('Customer not found');
            error.statusCode = 404;
            return;
        }
        let cart = await findOne({user: customer}).populate('products.product');
        var product = await _findOne({_id: new ObjectId(product)})
        if (!cart) {
            cart = new Cart({
                user: user,
                total: product.price,
                products: [
                    {
                        product: product,
                        amount: 1,
                        price: product.price,
                    }
                ]
            })
            await cart.save();
        } else {
            const existingProduct = cart.products.find(p => p.product._id.toString() === product);
            if (existingProduct) {
                existingProduct.amount += 1;
                existingProduct.price = existingProduct.product.price * existingProduct.amount;
                cart.total += existingProduct.product.price;
            } else {
                cart.products.push({
                    product: product,
                    amount: 1,
                    price: product.price
                })
                cart.total += product.price;
            }
            await Cart.updateOne({ user: _custumer }, { $set: cart });
        }
    } catch (err) {
        throw err;
    }
}

const increaseProductOfCart = async (_customer, _product) => {
    try {
        const customer = await Customer.findOne({ user: _customer });
        if (!customer) {
            const error = new Error('Customer not found');
            error.statusCode = 404;
            return;
        }
        let cart = await Cart.findOne({ user: customer }).populate('products.product');
        const product = cart.products.find(p => p.product._id.toString() === _product);
        product.amount += 1;
        product.price = product.product.price * product.amount;
        cart.total += product.price.price;
        await Cart.updateOne({ user: customer }, { $set: cart });
    } catch (err) {
        throw err;
    }
}

const decreaseProductOfCart = async (_customer, _product) => {
    try {
        const customer = await Customer.findOne({ user: _customer });
        if (!customer) {
            const error = new Error('Customer not found');
            error.statusCode = 404;
            return;
        }
        let cart = await Cart.findOne({ user: user }).populate('products.product');
        const productIndex = cart.products.findIndex(p => p.product._id.toString() === _product);
        cart.total -= cart.products[productIndex].price;
        cart.products[productIndex].amount -= 1;
        cart.products[productIndex].price = cart.products[productIndex].amount * cart.products[productIndex].product.price;
        if (cart.products[productIndex].amount === 0) {
            cart.products.splice(productIndex, 1);
        }
        await Cart.updateOne({ user: _customer }, { $set: cart });
    } catch (err) {
        throw err;
    }
}

const resetCart = async (_customer) => {
    try {
        const customer = await Customer.findOne({ user: _customer });
        if (!customer) {
            const error = new Error('Customer not found');
            error.statusCode = 404;
            return;
        }
        await Cart.updateOne({ user: req.params.user }, { $set: { total: 0, products: [] } })
    } catch (err) {
        throw err;
    }
}

module.export = {
    getCartOfUser,
    addToCart,
    increaseProductOfCart,
    decreaseProductOfCart,
    resetCart
}