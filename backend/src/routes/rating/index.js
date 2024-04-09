'use strict'
import {Router} from 'express';

const router = Router();
const rating = require('../../controllers/rating.controller')
// createRating
router.post('/rating/createRating/:productId/:customerId', rating.createRating);
// changeRating
router.put('/rating/changeRating/:productId/:customerId', rating.changeRating);
module.exports = router;