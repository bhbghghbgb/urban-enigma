const express = require("express");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const router = express.Router();
const notificationController = require("../controllers/notificationController")
router.put("/", firebaseAuthBearer, notificationController.bindDeviceToAccount)
router.delete("/", firebaseAuthBearer, notificationController.unbindDeviceFromAccount)