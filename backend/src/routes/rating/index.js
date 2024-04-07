'use strict'
import {Router} from 'express';

const router = Router();
const rating = require('../../controllers/rating.controller')
//checkRating
router.get('/rating/checkRating', rating.checkRating);
// createRating
router.post('/rating/createRating', rating.createRating);
// changeRating
router.put('/rating/changeRating', rating.changeRating);
module.exports = router;