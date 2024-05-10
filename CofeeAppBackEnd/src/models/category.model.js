'use strict'
// import {Schema, model } from 'mongoose'
const { Schema, model } = require('mongoose')
const Collection_Name = 'Category'
const categorySchema = new Schema({
    name: { type: String, required: true, trim: true },
},
    {
        timestamps: true,
        collection: Collection_Name
    });

module.exports = model(Collection_Name, categorySchema);