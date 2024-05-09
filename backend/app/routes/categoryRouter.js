const express = require("express");
const router = express.Router();
const categoryController = require("../controllers/categoryController");

//PASS
// -> lấy danh sách category
router.get("/", categoryController.getAllCategories);
// -> tìm kiếm category bằng id
router.get("/:id", categoryController.findCategoryById);
// -> thêm mới 1 category
router.post("/add", categoryController.createCategory);
// -> cập nhật 1 category
router.put("/update/:id", categoryController.updateCategoryById);
// -> xóa 1 category
router.delete("/delete/:id", categoryController.deleteCategoryById);

module.exports = router;
