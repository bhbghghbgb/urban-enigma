const express = require("express");
const { firebaseAuthBearer } = require("../middleware/firebaseAuthPassport");
const router = express.Router();
const notificationController = require("../controllers/notificationController")

router.put("/device-link", firebaseAuthBearer, notificationController.bindDeviceToAccount)
router.delete("/device-link", firebaseAuthBearer, notificationController.unbindDeviceFromAccount)

// POST because creating a new request to send a notification
router.post("/", notificationController.sendNotificationToAccount)
module.exports = router