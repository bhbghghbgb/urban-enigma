'use strict'
const express = require('express');
const router = express.Router();
const access = require('../../controllers/access.controller');
// signUp
router.post('/user/signup', access.signUp);
module.exports = router;