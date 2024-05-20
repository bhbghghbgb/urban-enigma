const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');

//PASS

// -> lấy danh sách product
router.get('/', productController.getAllProducts);
// -> lấy product bằng id
router.get('/:id', productController.findProductById);
// ->  tìm kiếm product theo name
router.get('/search/:name', productController.findProductByName);
// ->  lấy danh sách product phổ biến
router.get('/lists/popular', productController.getAllProductsPopular);
// ->  lấy danh sách product phổ biến
router.get('/lists/popular/limit', productController.getAllProductsPopularLimit);
// -> lấy product của 1 category nào đó
router.get('/category/:id', productController.getAllProductsOfCategory);
// -> lấy product mà có trường rating
router.get('/lists/full/rating', productController.getFullProducts);
//-> thêm 1 product
router.post('/add', productController.createProduct);
// -> cập nhật 1 product
router.put('/update/:id', productController.updateProductById);
// -> xóa 1 product
router.delete('/delete/:id', productController.deleteProductById);

module.exports = router;