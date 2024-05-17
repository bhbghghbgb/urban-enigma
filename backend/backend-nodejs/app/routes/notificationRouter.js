const express = require("express");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const router = express.Router();
const notificationController = require("../controllers/notificationController")

router.put("/device-link", firebaseAuthBearer, notificationController.bindDeviceToAccount)
router.delete("/device-link", firebaseAuthBearer, notificationController.unbindDeviceFromAccount)

module.exports = router