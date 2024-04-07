'use strict'
import {Product} from '../models/product.model';

const getProducts = async () => {
    try {
        return await Product.find().populate('category');
    } catch (error) {
        throw error;
    }
};

const getAllProductsPopular = async () => {
    try {
        return await Product.find({popular: true});
    } catch (error) {
        throw error;
    }
}

const getAllProductsOfCategory = async (id) => {
    try {
        return await Product.find({category: id});
    } catch (error) {
        throw error;
    }
}

const findProductById = async (id) => {
    try {
        return await Product.findById(id);
    } catch (error) {
        throw error;
    }
}

const findProductByName = async (name) => {
    try {
        const products = await Product.find({ name: { $regex: name, $options: 'i' } });
    } catch (err) {
        throw err;
    }
}

const createProduct = async (data) => {
    try {
        await new Product(data).save();
    } catch (error) {
        throw error;
    }
}
const updateProductById = async (productId, updateData) => {
    try {
        const updateProduct = Product.findByIdAndUpdate(productId, updateData, {new: true});
        if (!updateProduct) {
            const error =  new Error('Product is not found')
            error.statusCode = 404;
            throw error;
        }
    } catch (error) {
        throw error;
    }
}

const deleteProductById = async (productId) => {
    try {
        const deleteProduct = Product.findByIdAndDelete(productId);
        if (!deleteProduct) {
            const error =  new Error('Product is not found')
            error.statusCode = 404;
            throw error;
        }
    } catch (error) {
        throw error;
    }
}

const getProductsWithRating = async () => {
    try {
        const products = await Product.find();
        const productRatings = await Rating.aggregate([
            {
                $group: {
                    _id: "$product",
                    avgRating: { $avg: "$rating" },
                },
            },
        ]);

        const productsWithRatings = products.map((product) => {
            const avgRatingObj = productRatings.find(
                (rating) => rating._id.toString() === product._id.toString()
            );
            const avgRating = avgRatingObj ? avgRatingObj.avgRating : 0;
            return { ...product.toObject(), avgRating };
        });
    } catch (err) {
        throw err;
    }
}


module.exports = {
    getProducts,
    getAllProductsPopular,
    getAllProductsOfCategory,
    findProductById,
    createProduct,
    updateProductById,
    deleteProductById,
    getFullProducts: getProductsWithRating
};