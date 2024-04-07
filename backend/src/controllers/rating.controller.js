'use strict'
import {
    checkRating as _checkRatingService,
    createRating as _createRatingService,
    changeRating as _changeRatingService,
} from '../services/rating.service';
class ratingController {
    checkRating = async (req, res) => {
        try {
            const {
                customer,
                product
            } = req.params;
            const result = await _checkRatingService(customer, product);
            return res.status(200).json(result);
        } catch (error) {
            res.status(500).json({ message: error.message });
            return false;
        }
    }

    createRating = async (req, res) => {
        try {
            const {
                customer,
                product,
                rating
            } = req.body;
            const result = await _createRatingService(product, customer, rating);
            return res.status(200).json({ message: 'Rating created successfully' });
        } catch (error) {
            res.status(error.statusCode).json({ message: error.message });
            return false;
        }
    }

    changeRating = async (req, res) => {
        try {
            const {
                customer,
                product,
                rating
            } = req.body;
            const result = await _changeRatingService(product, customer, rating);
            return res.status(200).json({ message: 'Rating changed successfully' });
        } catch (error) {
            res.status(error.statusCode).json({ message: error.message });
            return false;
        }
    }
}

module.exports = new ratingController();


