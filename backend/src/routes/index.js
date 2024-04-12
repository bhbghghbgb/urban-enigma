'use strict'
import {Router} from 'express';

const router = Router();

router.use('/v1/api', require('./product'));
router.use('/v1/api', require('./rating'));
router.use('/v1/api', require('./cart'));
router.use('/v1/api', require('./category'));

module.exports = router;