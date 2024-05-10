'use strict'
// import Rating from '../models/rating.model';
// import Product from '../models/product.model';
// import {Customer} from '../models/user.model';


const Rating = require('../models/rating.model');
const Product = require('../models/product.model');
const {Customer} = require('../models/user.model');

function calculateTotalRating(productId) {
    return new Promise((resolve, reject) => {
        let totalRating = 0;
        Rating.find({product: productId})
            .exec()
            .then(ratings => {
                ratings.forEach(rating => {
                    totalRating += rating.rating;
                });
                resolve(totalRating);
            })
            .catch(error => {
                reject(error);
            });
    });
}

class RatingService {
    createRating = async (productId, customerId, rating) => {
        const [productExist, customerExits] = await Promise.all([
            Product.findById(productId).exec(),
            Customer.findById(customerId).exec(),
        ]);

        if (!productExist || !customerExits) {
            const error = new Error(
                `${!productExist ? 'Product' : ''} ${
                    !productExist && !customerExits ? 'and' : ''
                } ${!customerExits ? 'User' : ''} not found`
            );
            error.status = 404;
            throw error;
        }

        const ratingExists = await Rating.exists({customer: customerId, product: productId});
        if (ratingExists) {
            const error = new Error('Rating already exists');
            error.status = 409; // Conflict
            throw error;
        }

        const newRating = new Rating({
            customer: customerId,
            product: productId,
            rating: rating,
        })

        productExist.avgRating = (productExist.avgRating * productExist.ratings.length + rating) / (productExist.ratings.length + 1)
        productExist.ratings.push(newRating._id);

        console.log(productExist)
        console.log(newRating)

        await productExist.save();
        await newRating.save();

    };

    changeRating = async (productId, customerId, rating) => {
        try {
            const [productExist, customerExist] = await Promise.all([
                Product.findById(productId).exec(),
                Customer.findById(customerId).exec(),
            ]);

            if (!productExist) {
                const error = new Error('Product not found');
                error.name = 'ProductNotFound';
                error.status = 404;
                throw error;
            }

            if (!customerExist) {
                const error = new Error('User not found');
                error.name = 'UserNotFound';
                error.status = 404;
                throw error;
            }

            await Rating.findOneAndUpdate({product: productId, customer: customerId}, {rating: rating})
            productExist.avgRating = (productExist.avgRating * productExist.ratings.length + rating) / (productExist.ratings.length + 1)
            await productExist.save();
        } catch (err) {
            throw err;
        }
    };

    deleteRating = async (productId, customerId) => {
        const [productExist, customerExist] = await Promise.all([
            Product.findById(productId).exec(),
            Customer.findById(customerId).exec(),
        ]);

        if (!productExist) {
            const error = new Error('Product not found');
            error.name = 'ProductNotFound';
            error.status = 404;
            throw error;
        }

        if (!customerExist) {
            const error = new Error('User not found');
            error.name = 'UserNotFound';
            error.status = 404;
            throw error;
        }
        const deletedRating = await Rating.findOne({product: productId, customer: customerId}).exec();
        console.log("deletedRating" + deletedRating._id)
        calculateTotalRating(productId)
            .then(async total => {
                const newRating = productExist.ratings.filter(rating => rating !== deletedRating._id);
                console.log("newRating" + newRating)
                // productExist.avgRating = (total-deletedRating.rating) / newRating.length;
                // productExist.ratings = newRating;
            })
            .catch(error => {
                throw error;
            });
        // await productExist.save();
        // await Rating.findOneAndDelete({product: productId, customer: customerId})
    }
}

module.exports = new RatingService();