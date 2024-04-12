import Category from '../models/category.model.js';
import Product from '../models/product.model.js';

class CategoryService {
    getAllCategories = async () => {
        try {
            return await Category.find({});
        } catch (err) {
            throw err;
        }
    }
    findCategoryById = async (id) => {
        const category = await Category.findById(id);
        if (!category) {
            const error = new Error('Category is not found');
            error.statusCode = 404;
            throw error;
        }
        return category;
    };
    createCategory = async (name) => {
        try {
            const category = new Category({ name });
            return await category.save();
        } catch (err) {
            throw err;
        }
    }

    updateCategoryById = async (id, name) => {
        try {
            return await Category.findByIdAndUpdate(id, {name}, {new: true});
        } catch (err) {
            throw err;
        }
    }

    async checkCategoryId(category) {
        try {
            const productCount = await Product.countDocuments({category: category})
            return productCount > 0;
        } catch (error) {
            console.log(error);
            return false;
        }
    }

    deleteCategoryById = async (categoryId) => {
        const category = await Category.findById(categoryId);
        if (!category) {
            const error = new Error('Category is not found');
            error.statusCode = 404;
            throw error;
        }
        const categoryExists = await this.checkCategoryId(categoryId);
        if (!categoryExists) {
            await Category.findByIdAndDelete(categoryId);
        } else {
            const err = new Error('There are products of this categor that cannot be deleted');
            err.statusCode = 500;
            throw err;
        }
    }
}

module.exports = new CategoryService();