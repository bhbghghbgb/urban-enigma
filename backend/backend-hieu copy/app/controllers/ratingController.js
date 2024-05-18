const Rating = require('../models/ratingModel');
const Product = require('../models/productModel');
const { Customer } = require('../models/userModel');
const { ObjectId } = require('mongodb');

async function checkRating(user, product) {
    try {
        const ratingCount = await Rating.countDocuments({ user: user, product: product })
        return ratingCount > 0;
    } catch (err) {
        res.status(500).json({ message: err.message });
        return false;
    }
}

exports.createRating = async (req, res) => {
    try {
        const productExist = await Product.findById(req.params.product);
        if (!productExist) {
            res.status(404).json({ message: 'Product is not found' });
            return;
        }
        const userExist = await Product.findById(req.params.user);
        if (!userExist) {
            res.status(404).json({ message: 'User is not found' });
            return;
        }
        const rating = new Rating({
            user: req.params.user,
            product: req.params.product,
            rating: req.body.rating
        });
        if (!checkRating(req.params.user, req.params.product)) {
            await rating.save();
            res.status(200).json({ message: 'Rating created successfully' });
        } else {
            res.status(500).json({ message: 'This rating has already exist!' })
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}

exports.changeRating = async (req, res) => {
    try {
        const productExist = await Product.findById(req.params.product);
        if (!productExist) {
            res.status(404).json({ message: 'Product is not found' });
            return;
        }
        const userExist = await Product.findById(req.params.user);
        if (!userExist) {
            res.status(404).json({ message: 'User is not found' });
            return;
        }
        if (checkRating(req.params.user, req.params.product)) {
            var myQuery = { user: req.params.user, product: req.params.product };
            var newData = {
                $set: {
                    rating: req.body.rating
                }
            };
            await Rating.updateOne(myQuery, newData);
            res.status(201).json({ message: 'Rating updated successfully' });
        } else {
            res.status(500).json({ message: 'This rating does not exist!' })
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
}