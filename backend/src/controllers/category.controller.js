'use strict';
import categoryService from '../services/category.service.js';

class categoryController{

// Lấy tất cả các danh mục
    getAllCategories = async (req, res) => {
        try {
            const categories = await categoryService.getAllCategories();
            res.status(200).json(categories);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    };

// Tìm danh mục theo ID
    findCategoryById = async (req, res) => {
        try {
            const categoryId = req.params.id;
            const category = await categoryService.findCategoryById(categoryId);
            res.status(200).json(category);
        } catch (err) {
            res.status(err.statusCode).json({message: err.message});
        }
    };

// Tạo mới danh mục
    createCategory = async (req, res) => {
        try {
            const {name} = req.body;
            const category = await categoryService.createCategory(name);
            res.status(201).json({
                message: 'Category created successfully',
                metadata: {category}
            });
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    };

// Cập nhật danh mục theo ID
    updateCategoryById = async (req, res) => {
        try {
            const categoryId = req.params.id;
            const category = await categoryService.updateCategoryById(categoryId, req.body.name);
            if (category === null) {
                return res.status(404).json({message: 'Category not found'});
            }
            res.status(200).json({message: 'Category updated successfully', metadata: category});
        } catch (err) {
            res.status(404).json({message: err.message});
        }
    };

// Xóa danh mục theo ID
    deleteCategoryById = async (req, res) => {
        try {
            const categoryId = req.params.id;
            await categoryService.deleteCategoryById(categoryId);
            res.status(200).json({message: 'Category deleted successfully'});
        } catch (err) {
            res.status(err.statusCode).json({message: err.message});
        }
    };
}

module.exports = new categoryController();