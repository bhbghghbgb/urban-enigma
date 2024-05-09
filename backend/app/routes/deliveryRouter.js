const express = require('express');
const router = express.Router();
const deliveryController = require('../controllers/deliveryController');
const middleware = require('../middleware/authenticate');

// -> lấy danh sách staff valid
router.get('/staffs', deliveryController.findAvailableStaff);

// -> lấy thông tin đơn hàng hiện delivering
router.get('/order', middleware.authenticateStaff, deliveryController.getOrdersByStaff);

// -> lấy danh sách đơn hàng đã giao
router.get('/order/delivered', middleware.authenticateStaff, deliveryController.findOrderOfStaff);

// -> thêm 1 order
router.post('/add', deliveryController.createNewDelivery);

// -> Cập nhật trạng thái đã giao cho đơn vận chuyển
router.put('/update/:id', deliveryController.changeStatusDelivery);

module.exports = router;