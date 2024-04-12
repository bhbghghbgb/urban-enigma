'use strict'
import express from 'express'

const app = express()
// Init middlewares

// Init Db
import 'databases/init.mongodb.js'

// Init routers
app.use('/', require('./routes/index.js'))
app.use(express.json)

// error handler

export default app;