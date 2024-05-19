const Category = require('../models/categoryModel');
const Product = require('../models/productModel');
const Rating = require('../models/ratingModel');
const { ObjectId } = require('mongodb');

exports.getAllProducts = async (req, res) => {
    try {
        const products = await Product.find().populate('category');
        res.status(200).json(products);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getAllProductsPopular = async (req, res) => {
    try {
        const products = await Product.find({ popular: true }).populate('category');
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
        res.status(200).json(productsWithRatings);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getAllProductsPopularLimit = async (req, res) => {
    try {
        const products = await Product.find({ popular: true }).populate('category').limit(5);
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
        res.status(200).json(productsWithRatings);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.getAllProductsOfCategory = async (req, res) => {
    try {
        const products = await Product.find({ category: req.params.id });
        res.status(200).json(products);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.findProductById = async (req, res) => {
    try {
        const product = await Product.findById(req.params.id).populate('category');
        if (product) {
            res.status(200).json(product);
        } else {
            res.status(404).json({ message: 'Product is not fount' });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.findProductByName = async (req, res) => {
    try {
        const name = req.params.name;
        const products = await Product.find({ name: { $regex: name, $options: 'i' } }).populate('category');
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
        res.status(200).json(productsWithRatings);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.createProduct = async (req, res) => {
    try {
        const product = new Product(req.body);
        await product.save();
        res.status(201).json({ message: 'Product created successfully' });
        return;
    } catch (error) {
        res.status(500).json({ message: err.message });
    }
}

exports.updateProductById = async (req, res) => {
    try {
        const product = Product.findById(req.params.id);
        if (!product) {
            res.status(404).json({ message: 'Product is not found' });
            return;
        }
        const { name, image, description, price, popular, category } = req.body;
        const myQuery = { _id: new ObjectId(req.params.id) };
        const newValues = {
            $set: {
                name: name,
                image: image,
                description: description,
                price: price,
                popular: popular,
                category: category,
            }
        };
        await Product.updateOne(myQuery, newValues);
        res.status(201).json({ message: 'Product updated successfully' });
        return;
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
};

exports.deleteProductById = async (req, res) => {
    try {
        const product = Product.findById(req.params.id);
        if (!product) {
            res.status(404).json({ message: 'Product is not found' });
            return;
        }
        await Product.deleteOne({ _id: new ObjectId(req.params.id) })
        res.status(200).json({ message: 'Product deleted successfully.' });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.getFullProducts = async (req, res) => {
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
        res.status(200).json(productsWithRatings);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

