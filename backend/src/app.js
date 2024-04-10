'use strict'
import express from 'express'

const app = express()
// Init middlewares

// Init Db
import '/databases/init.mongodb'

// Init routers
app.use('/', require('./routes/index.js').default)

// error handler

module.exports = app;