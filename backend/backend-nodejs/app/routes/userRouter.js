const express = require("express");
const router = express.Router();
const userController = require("../controllers/userController");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const { userRole } = require("../middleware/connectRoles");
//PASS

// -> lấy danh sách staff
router.get("/staff", userController.getStaffs);
// -> lấy danh sách staff
router.get(
    "",
    firebaseAuthBearer,
    userRole.is("staff"),
    userController.getInfo
);
// -> lấy danh sách customer
router.get("/customer", userController.getCustomers);
// -> thêm 1 staff
router.post("/add/staff", userController.createStaff);
// -> thêm 1 customer
router.post("/add/customer", userController.createCustomer);
// -> cập nhật 1 staff
router.put("/update/staff/:id", userController.updateStaff);
// -> cập nhật 1 customer
router.put("/update/customer/:id", userController.updateCustomer);
// -> xóa 1 customer bằng id
router.delete("/delete/:id", userController.deleteStaff);
// -> lấy thông tin user
router.get(
    "/infocustomer",
    firebaseAuthBearer,
    userRole.is("customer"),
    userController.getCustomerInfo
);

module.exports = router;
