'use strict'
import Cart from '../models/cart.model';
import Product from '../models/product.model';
import {Customer} from '../models/user.model.js';

const getCartOfUser = async (customerId) => {
    const customer = await Customer.findById(customerId);
    if (!customer) {
        const error = new Error('Customer not found');
        error.statusCode = 404;
        throw error;
    }
    return await Cart.findOne({customer: customerId}).populate('products.product');
}

const addToCart = async (customerId, productId) => {
    const customer = await Customer.findById(customerId);
    if (!customer) {
        const error = new Error('Customer not found');
        error.statusCode = 404;
        throw error;
    }

    const product = await Product.findById(productId);
    if (!product) {
        const error = new Error('Product not found');
        error.statusCode = 404;
        throw error;
    }

    let cart = await Cart.findOne({customer: customerId}).populate('products.product');
    if (!cart) {
        cart = new Cart({
            customer: customerId,
            total: product.price,
            products: [{productId, amount: 1, price: product.price}],
        });
    } else {
        const existingProductIndex = cart.products.findIndex(p => p.product._id.equals(product._id));
        if (existingProductIndex !== -1) {
            cart.products[existingProductIndex].amount += 1;
            cart.products[existingProductIndex].price = cart.products[existingProductIndex].product.price * cart.products[existingProductIndex].amount;
            cart.total += product.price;
        } else {
            cart.products.push({productId, amount: 1, price: product.price});
            cart.total += product.price;
        }
    }

    await cart.save();
    return cart;
};

const getIndexOfProduct = async (customerId, productId, cart) => {
    const customer = await Customer.findById(customerId);
    if (!customer) {
        const error = new Error('Customer not found');
        error.statusCode = 404;
        throw error;
    }

    if (!cart) {
        const error = new Error('Cart not found');
        error.statusCode = 404;
        throw error;
    }

    const productIndex = cart.products.findIndex(p => p.product._id.equals(productId));
    if (productIndex === -1) {
        const error = new Error('Product not found in cart');
        error.statusCode = 404;
        throw error;
    }

    return productIndex;
}

const increaseProductOfCart = async (customerId, productId) => {
    const cart = await Cart.findOne({customer: customerId}).populate('products.product');
    const product = cart.products[await getIndexOfProduct(customerId, productId, cart)];
    product.amount += 1;
    product.price = product.product.price * product.amount;
    cart.total += product.product.price;
    await cart.save();
};

const decreaseProductOfCart = async (customerId, productId) => {
    let cart = await Cart.findOne({customer: customerId}).populate('products.product');
    const productIndex = await getIndexOfProduct(customerId, productId, cart);
    const product = cart.products[productIndex];
    if (product.amount === 1) {
        cart.products.splice(productIndex, 1);
        cart.total -= product.price;
    } else {
        product.amount -= 1;
        product.price = product.product.price * product.amount;
        cart.total -= product.product.price;
    }
    await cart.save();
};

const resetCart = async (customerId) => {
    const customer = await Customer.findById(customerId);
    if (!customer) {
        const error = new Error('Customer not found');
        error.statusCode = 404;
        throw error;
    }
    const cart = await Cart.findOneAndUpdate(
        {customer: customerId},
        {$set: {total: 0, products: []}},
        {new: true}
    );
    if (!cart) {
        const error = new Error('Cart not found');
        error.statusCode = 404;
        throw error;
    }
}

module.exports = {
    getCartOfUser,
    addToCart,
    increaseProductOfCart,
    decreaseProductOfCart,
    resetCart
}