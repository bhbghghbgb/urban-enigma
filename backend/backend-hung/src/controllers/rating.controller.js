'use strict'
// import {
//     createRating as _createRatingService,
//     changeRating as _changeRatingService,
// } from '../../../../WebstormProjects/CofeeBackEnd/src/services/rating.service';

const ratingService = require('../services/rating.service')
class ratingController {
    createRating = async (req, res) => {
        try {
            const {
                customer,
                product
            } = req.params;
            const rating  = req.body.rating;
            await ratingService.createRating(product, customer, rating);
            return res.status(200).json({ message: 'Rating created successfully' });
        } catch (error) {
            res.status(error.status || 500).json({ message: error.message });
            return false;
        }
    }

    changeRating = async (req, res) => {
        try {
            const {
                customer,
                product
            } = req.params;
            const rating  = req.body.rating;
            await ratingService.changeRating(product, customer, rating);
            return res.status(200).json({ message: 'Rating changed successfully' });
        } catch (error) {
            res.status(error.status || 500).json({ message: error.message });
            return false;
        }
    }
    deleteRating = async (req, res) => {
        try {
            const {
                customer,
                product
            } = req.params;
            await ratingService.deleteRating(product, customer);
            return res.status(200).json({ message: 'Rating deleted successfully' });
        } catch (error) {
            res.status(error.status || 500).json({ message: error.message });
            return false;
        }
    }
}

module.exports = new ratingController();


