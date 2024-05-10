'use strict'
// import {Router} from 'express';

const {Router} = require('express');
const router = Router();
// const rating = require('../../controllers/rating.controller')

const rating = require('../../controllers/rating.controller');
// getRating
// createRating
router.post('/products/:product/customers/:customer/rating/create', rating.createRating);
// changeRating
router.put('/products/:product/customers/:customer/ratings/change', rating.changeRating);
// deleteRating
router.delete('/products/:product/customers/:customer/ratings/delete', rating.deleteRating);
module.exports = router;