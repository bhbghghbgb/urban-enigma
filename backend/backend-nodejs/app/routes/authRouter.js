const express = require("express");
const router = express.Router();
const { testAuthorization } = require("../controllers/authController");
const { userRole } = require("../middleware/connectRoles");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
// goi route nay de check token con xai dc ko
router.get(
    "/customer",
    firebaseAuthBearer,
    userRole.is("customer"),
    testAuthorization
);
router.get(
    "/staff",
    firebaseAuthBearer,
    userRole.is("staff"),
    testAuthorization
);
module.exports = router