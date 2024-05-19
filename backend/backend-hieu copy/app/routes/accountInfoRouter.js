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
router.post("/email/:username", requestChangeAccountEmail);
// yeu cau doi sdt cho tai khoan
router.post("/phone/:username", requestChangeAccountPhone);
// tien hanh doi email su dung verify code
router.patch("/email/:username", attemptChangeAccountEmail);
// tien hanh doi sdt su dung verify code
router.patch("/phone/:username", attemptChangeAccountPhone);

// DEBUG
// hien het cac yeu cau doi thong tin tai khoan (de xem verify code)
router.get("requests", getAllRequests);
module.exports = router;
