const express = require("express");
const router = express.Router();
const {
    requestChangeAccountEmail,
    requestChangeAccountPhone,
    attemptChangeAccountEmail,
    attemptChangeAccountPhone,
    getAllRequests,
} = require("../controllers/accountInfoController");

// CHUA TEST
// yeu cau doi email cho tai khoan
router.post("/email", requestChangeAccountEmail);
// yeu cau doi sdt cho tai khoan
router.post("/phone", requestChangeAccountPhone);
// tien hanh doi email su dung verify code
router.patch("/email", attemptChangeAccountEmail);
// tien hanh doi sdt su dung verify code
router.patch("/phone", attemptChangeAccountPhone);

// DEBUG
// hien het cac yeu cau doi thong tin tai khoan (de xem verify code)
router.get("requests", getAllRequests);
module.exports = router;
