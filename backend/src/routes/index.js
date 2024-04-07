'use strict'
const express = require('express');
const router = express.Router();

router.use('/v1/api', require('./product'));
router.use('/v1/api', require('./rating'));
router.use('/v1/api', require('./cart'));

module.exports = router;