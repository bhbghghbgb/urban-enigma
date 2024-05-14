const express = require("express");
const { basicAuth } = require("../middleware/basicAuth");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const { getUser } = require("../controllers/authController");
const { productRole } = require("../middleware/connectRoles");
const { account2customer } = require("../service/account2shits");
const Account = require("../models/accountModel");
const productController = require("../controllers/productController")
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
    getUser,
);
router.get(
    "/basic-auth/nopopular",
    basicAuth,
    productRole.is("nopopular"),
    getUser,
);
router.get("/current-firebase-user", firebaseAuthBearer, getUser);

// lay 3 list product de hien len Banner, Best Seller va For You tren man hinh Home
router.get("/homescreen", productController.getHomeScreenProducts)

// account2shits
router.get(
    "/account2shits/null-req-user",
    /* Nothing ,*/ async (req, res, next) => {
        try {
            return res.json(await account2customer(req.user));
        } catch (err) {
            return next(err);
        }
    },
);
router.get(
    "/account2shits/bad-type-req-user",
    (req, res, next) => {
        req.user = "id hay deo gi day";
        return next();
    },
    async (req, res, next) => {
        try {
            return res.json(await account2customer(req.user));
        } catch (err) {
            return next(err);
        }
    },
);
router.get(
    "/account2shits/wrong-role-req-user",
    (req, res, next) => {
        req.user = { role: "staff" };
        return next();
    },
    async (req, res, next) => {
        try {
            return res.json(await account2customer(req.user));
        } catch (err) {
            return next(err);
        }
    },
);
router.get(
    "/account2shits/correct-req-user",
    async (req, res, next) => {
        req.user = Account.findOne().then((user) => {
            req.user = user;
            next();
        });
    },
    async (req, res, next) => {
        try {
            return res.json(await account2customer(req.user));
        } catch (err) {
            return next(err);
        }
    },
);
module.exports = router;
