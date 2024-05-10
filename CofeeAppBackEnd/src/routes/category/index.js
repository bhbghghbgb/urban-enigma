'use strict'
// import {Router} from 'express';
//
// const router = Router();

const express = require('express');
const router = express.Router();
// import categoryController from '../../controllers/category.controller.js';

const categoryController = require('../../controllers/category.controller.js');
// Route để lấy tất cả các danh mục
router.get('/category', categoryController.getAllCategories);//pass

// Route để tìm danh mục theo ID
router.get('/category/find/:id', categoryController.findCategoryById);//pass

// Route để tạo mới danh mục
router.post('/category/create', categoryController.createCategory);//pass

// Route để cập nhật danh mục theo ID
router.patch('/category/update/:id', categoryController.updateCategoryById);

// Route để xóa danh mục theo ID
router.delete('/category/delete/:id', categoryController.deleteCategoryById);

module.exports = router;