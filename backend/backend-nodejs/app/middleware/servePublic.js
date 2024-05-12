// Lấy hình ảnh từ trong public
// that ra la lay het cac file trong public deu duoc
const express = require("express");
exports.servePublic = express.static("public");
