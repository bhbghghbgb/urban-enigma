'use strict'
import {Router} from 'express';

const router = Router();
const rating = require('../../controllers/rating.controller')
//checkRating
router.post('/rating/checkRating', rating.checkRating);
// createRating
router.post('/rating/createRating', rating.createRating);
// changeRating
router.post('/rating/changeRating', rating.changeRating);
module.exports = router;