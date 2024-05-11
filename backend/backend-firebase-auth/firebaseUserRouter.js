const express = require("express");
const router = express.Router();
const {
    changeUserRole,
    findUserByFirebaseUid,
    createUser,
} = require("../controllers/filebaseUserController");

// -> lấy thông tin tài khoản bằng firebaseUid
router.get("/:firebaseUid", findUserByFirebaseUid);
// -> tạo 1 tài khoản mới
router.post("/add", createUser);
// -> thay đổi role của tài khoản
router.patch("/update/staff/:firebaseUid", changeUserRole);

module.exports = router;
