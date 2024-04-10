'use strict'
import {Router} from 'express';

const router = Router();
const rating = require('../../controllers/rating.controller')
// createRating
router.post('/products/:product/customers/:customer/rating/create', rating.createRating);
// changeRating
router.put('/products/:product/customers/:customer/ratings/change', rating.changeRating);
module.exports = router;