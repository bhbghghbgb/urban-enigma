const express = require("express");
const router = express.Router();
const { servePublic } = require("../middleware/servePublic");
const { uploadImage } = require("../controllers/imagesController");
const fileUpload  = require("express-fileupload");
// can GET static files now
router.use(servePublic);
// upload image route
router.post("/images", fileUpload(), uploadImage);
module.exports = router;
