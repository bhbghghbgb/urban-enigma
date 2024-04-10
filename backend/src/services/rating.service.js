'use strict'
import Rating from '../models/rating.model';
import Product from '../models/product.model';
import {Customer} from '../models/user.model';

const createRating = async (productId, customerId, rating) => {
    try {
        const [productExist, userExist] = await Promise.all([
            Product.findById(productId),
            Customer.findById(customerId),
        ]);

        if (!productExist || !userExist) {
            const error = new Error(
                `${!productExist ? 'Product' : ''} ${
                    !productExist && !userExist ? 'and' : ''
                } ${!userExist ? 'User' : ''} not found`
            );
            error.statusCode = 404;
            throw error;
        }

        const ratingExists = await Rating.exists({ customer: customerId, product: productId });
        if (ratingExists) {
            const error = new Error('Rating already exists');
            error.statusCode = 409; // Conflict
            throw error;
        }

        await Rating.create({
            customer: customerId,
            product: productId,
            rating,
        })
    } catch (err) {
        throw err;
    }
};

const changeRating = async (productId, customerId, rating) => {
    try {
        const [productExist, customerExist] = await Promise.all([
            Product.findById(productId),
            Customer.findById(customerId),
        ]);

        if (!productExist) {
            const error = new Error('Product not found');
            error.name = 'ProductNotFound';
            error.statusCode = 404;
            throw error;
        }

        if (!customerExist) {
            const error = new Error('User not found');
            error.name = 'UserNotFound';
            error.statusCode = 404;
            throw error;
        }

        const query = { customer: customerId, product: productId };
        const ratingData = { $set: { rating } };
        const options = { new: true, useFindAndModify: false };

        const updatedRating = await Rating.findOneAndUpdate(query, ratingData, options);

        if (!updatedRating) {
            const error = new Error('Rating not found');
            error.statusCode = 404;
            throw error;
        }

        return updatedRating;
    } catch (err) {
        throw err;
    }
};
module.exports = {
    createRating,
    changeRating
};
