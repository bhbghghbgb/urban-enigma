const express = require("express");
const { basicAuth } = require("../middleware/basicAuth");
const { getUser } = require("../controllers/authController");
const { productRole } = require("../middleware/connectRoles");
const router = express.Router();
router.use(productRole.middleware());
// test basic auth and connect-roles
// firebase KHONG test dc tren backend vi khong dang nhap dc bang adminSDK
// chi dang nhap dc bang clientSDK
router.get("/basic-auth", basicAuth, getUser);
router.get(
    "/basic-auth/popular",
    basicAuth,
    productRole.is("ispopular"),
    getUser
);
router.get(
    "/basic-auth/nopopular",
    basicAuth,
    productRole.is("nopopular"),
    getUser
);
module.exports = router;
