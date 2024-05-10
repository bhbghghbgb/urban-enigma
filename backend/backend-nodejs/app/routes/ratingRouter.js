const express = require("express");
const router = express.Router();
const ratingController = require("../controllers/ratingController");

//PASS

// -> thêm đánh giá user cho product (vote star)
router.post("/add/:user/:product", ratingController.createRating);
// -> cập nhật lại đánh giá user cho product
router.put("/update/:user/:product", ratingController.changeRating);

module.exports = router;
