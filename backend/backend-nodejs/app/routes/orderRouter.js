const express = require("express");
const router = express.Router();
const orderController = require("../controllers/orderController");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const { userRole } = require("../middleware/connectRoles");
//Pass
// -> lấy danh sách orders
router.get("/", orderController.getAllOrders);
// -> lấy order của khách hàng chưa được giao
router.get(
    "/not-yet-delivered",
    firebaseAuthBearer,
    userRole.is("customer"),
    orderController.getAllOrderByUserNotYetDelivered,
);
// -> lấy order của khách hàng đã được giao
router.get(
    "/delivered",
    firebaseAuthBearer,
    userRole.is("customer"),
    orderController.getAllOrderByUserDelivered,
);
// -> lấy 1 order bằng id
router.get(
    "/:id",
    firebaseAuthBearer,
    userRole.is("customer"),
    orderController.findOrderById,
);
// -> lấy những order đã [now, delivering, delivered]
router.get("/status/:status", orderController.getOrdersOfStatus);
// -> lấy 1 order bằng id
router.get("/admin/:id", orderController.findOrderByIdAdmin);
// -> thêm 1 order
router.post("/add", orderController.createOrder);
// -> cập nhật trạng thái của order
router.patch("/update/:id", orderController.updateStatus);

module.exports = router;
