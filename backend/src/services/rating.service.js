'use strict'
import Rating from '../models/rating.model';
import Product from '../models/product.model';
import {Customer} from '../models/user.model';

const checkRating= async (user, product) => {
    try {
        const ratingCount = await Rating.countDocuments({ user: user, product: product })
        return ratingCount > 0;
    } catch (err) {
        return false;
    }
}

const createRating = async (product, customer, rating) => {
    try {
        const [productExist, userExist] = await Promise.all([
            Product.findById(product),
            Customer.findById(customer),
        ]);

        if (!productExist) {
            const errorProduct = new Error('Product not found');
            errorProduct.statusCode = 404;
            throw errorProduct;
        }

        if (!userExist) {
            const errorUser = new Error('User not found');
            errorUser.statusCode = 404;
            throw errorUser;
        }

        const ratingExists = await Rating.findOne({ customer, product });
        if (ratingExists) {
            const error = new Error('Rating already exists');
            error.statusCode = 400;
            throw error;
        }

        const ratingModel = new Rating({
            customer,
            product,
            rating: rating,
        });

        await ratingModel.save();
    } catch (err) {
        throw err;
    }
};

const changeRating = async (product, customer, rating) => {
    try {
        const [productExist, customerExist] = await Promise.all([
            Product.findById(product),
            Customer.findById(customer),
        ]);
        if (!productExist) {
            const errorProduct = new Error('Product not found');
            errorProduct.statusCode = 404;
            errorProduct.name = 'ProductNotFound';
            throw errorProduct;
        }
        if (!customerExist) {
            const errorCustomer = new Error('User not found');
            errorCustomer.statusCode = 404;
            errorCustomer.name = 'UserNotFound';
            throw errorCustomer;
        }

        if (checkRating(customer, product)) {
            var myQuery = { customer: customer, product: product };
            var newData = {
                $set: {
                    rating: rating
                }
            };
            await Rating.updateOne(myQuery, newData);
        } else {
        }
    } catch (err) {
        throw err;
    }


}
