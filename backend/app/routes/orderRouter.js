const express = require('express');
const router = express.Router();
const orderController = require('../controllers/orderController');
const authenticate = require('../middleware/authenticate');

//Pass
// -> lấy danh sách orders
router.get('/', orderController.getAllOrders);
// -> lấy order của khách hàng chưa được giao
router.get('/not-yet-delivered', authenticate.authenticate, orderController.getAllOrderByUserNotYetDelivered);
// -> lấy 1 order bằng id
router.get('/:id', orderController.findOrderById);
// -> lấy những order đã [now, delivering, delivered]
router.get('/status/:status', orderController.getOrdersOfStatus);
// -> thêm 1 order
router.post('/add', orderController.createOrder);
// -> cập nhật trạng thái của order
router.patch('/update/:id', orderController.updateStatus);

module.exports = router;