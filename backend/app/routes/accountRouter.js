const express = require('express');
const router = express.Router();
const accountController = require('../controllers/accountController');

//PASS
// -> lấy thông tin tài khoản bằng username
router.get('/:username', accountController.findAccountByUsername);
// -> tạo 1 tài khoản mới
router.post('/add', accountController.createAccount);
// -> đổi mật khẩu
router.patch('/update/:username', accountController.changePassword);
// -> thay đổi role của tài khoản
router.patch('/update/staff/:username', accountController.changeRoleOfAccount);

module.exports = router;