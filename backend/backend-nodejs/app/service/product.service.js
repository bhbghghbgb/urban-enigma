'use strict'
// import Product from '../models/product.model';
// import Rating from '../models/rating.model';

const Product = require('../models/productModel');
class ProductService{
    getProducts = async () => {
        try {
            return await Product.find().populate('category');
        } catch (error) {
            throw error;
        }
    };

    getAllProductsPopular = async () => {
        try {
            return await Product.find({popular: true});
        } catch (error) {
            throw error;
        }
    }

    getAllProductsOfCategory = async (id) => {
        try {
            return await Product.find({category: id});
        } catch (error) {
            throw error;
        }
    }

    findProductById = async (id) => {
        try {
            return await Product.findById(id).exec();
        } catch (error) {
            throw error;
        }
    }

    findProductByName = async (name) => {
        try {
            return await Product.find({name: {$regex: name, $options: 'i'}});
        } catch (err) {
            throw err;
        }
    }

    createProduct = async (data) => {
        return await Product.create({
            ...data
        });
    }
    updateProductById = async (productId, updateData) => {
        const updateProduct = await Product.findByIdAndUpdate(productId, updateData, {new: true}).exec();
        if (!updateProduct) {
            const error = new Error('Product is not found')
            error.status = 404;
            throw error;
        }
        return updateProduct;
    }

    deleteProductById = async (productId) => {
        const deleteProduct = await Product.findByIdAndDelete(productId).exec();
        if (!deleteProduct) {
            const error = new Error('Product is not found')
            error.status = 404;
            throw error;
        }
        return deleteProduct;
    }

    getProductsWithRating = async () => {
        try {
            const products = await Product.find();
            const productRatings = await Rating.aggregate([
                {
                    $group: {
                        _id: "$product",
                        avgRating: {$avg: "$rating"},
                    },
                },
            ]);

            const productsWithRatings = products.map((product) => {
                const avgRatingObj = productRatings.find(
                    (rating) => rating._id.toString() === product._id.toString()
                );
                const avgRating = avgRatingObj ? avgRatingObj.avgRating : 0;
                return {...product.toObject(), avgRating};
            });
        } catch (err) {
            throw err;
        }
    }

    getProductsWithRating_Hung = async () => {
        try {
            return await Product.aggregate([
                {
                    $lookup: {
                        from: 'ratings',
                        localField: '_id',
                        foreignField: 'product',
                        as: 'ratings',
                    },
                },
                {
                    $addFields: {
                        avgRating: {
                            $avg: '$ratings.rating',
                        },
                    },
                },
                {
                    $project: {
                        ratings: 0,
                    },
                },
            ]);
        } catch (err) {
            throw err;
        }
    };
}


module.exports = new ProductService();