const express = require("express");
const router = express.Router();
const cartController = require("../controllers/cartController");

// Pass
// -> lấy thông tin giỏ hàng của customer
router.get("", cartController.getCartOfUser);
// -> add to cart
router.post("/add-to-cart", cartController.addToCart);
// -> tăng số lượng 1 product trong cart
router.patch("/increase/:productId", cartController.increaseProductOfCart);
// -> giảm số lượng 1 product trong cart
router.patch(
    "/add-to-cart/decrease/:user",
    cartController.decreaseProductOfCart
);
// -> xóa hết giỏ hàng
router.patch("/reset", cartController.resetCart);
// -> xoá sản phẩm trong giỏ hàng
router.patch("/delete", cartController.deleteProductOfCart);

// Hung
router.post("", cartController.updateCart);
module.exports = router;
