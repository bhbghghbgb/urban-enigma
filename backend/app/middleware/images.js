// Lấy hình ảnh từ trong public
const express = require("express")
exports.publicImages = express.static("public");
