'use strict'
import {config} from "dotenv";
config();
const config  = {
    app:{
        port: process.env.PORT
    },
    db:{
        url: process.env.DATA_URL
    }
};
module.exports = config;