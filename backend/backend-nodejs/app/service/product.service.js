'use strict'
// import Product from '../models/product.model';
// import Rating from '../models/rating.model';

const Product = require('../models/productModel');
const Rating = require('../models/ratingModel');
class ProductService{
    getProducts = async () => {
        return await Product.find().populate('category');
    };

    getAllProductsPopular = async () => {
        return await Product.find({popular: true});
    }

    getAllProductsOfCategory = async (id) => {
        return await Product.find({category: id});
    }

    findProductById = async (id) => {
        return await Product.findById(id).exec();
    }

    findProductByName = async (name) => {
        return await Product.find({name: {$regex: name, $options: 'i'}});
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
        const products = await Product.find();
        const productRatings = await Rating.aggregate([
            {
                $group: {
                    _id: "$product",
                    avgRating: {$avg: "$rating"},
                },
            },
        ]);

        return products.map((product) => {
            const avgRatingObj = productRatings.find(
                (rating) => rating._id.toString() === product._id.toString()
            );
            const avgRating = avgRatingObj ? avgRatingObj.avgRating : 0;
            return {...product.toObject(), avgRating};
        });
    }

    getProductsWithRating_Hung = async () => {
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
    };
}


module.exports = new ProductService();