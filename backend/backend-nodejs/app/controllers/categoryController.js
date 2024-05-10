const Category = require("../models/categoryModel");
const Product = require("../models/productModel");
const { ObjectId } = require("mongoose").Types;

exports.getAllCategories = async (req, res) => {
    try {
        const catogeries = await Category.find();
        res.status(200).json(catogeries);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.findCategoryById = async (req, res) => {
    try {
        const category = await Category.findById(req.params.id);
        if (category) {
            res.status(200).json(category);
            return;
        } else {
            res.status(404).json({ message: "Category not found" });
            return;
        }
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.createCategory = async (req, res) => {
    try {
        const category = new Category(req.body);
        await category.save();
        res.status(200).json({ message: "Category created successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.updateCategoryById = async (req, res) => {
    try {
        const category = await Category.findById(req.params.id);
        if (!category) {
            res.status(404).json({ message: "Category not found" });
            return;
        }
        const { name } = req.body;
        var myQuery = { _id: new ObjectId(req.params.id) };
        var newData = {
            $set: {
                name: name,
            },
        };
        await Category.updateOne(myQuery, newData);
        res.status(201).json({ message: "Category updated successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

async function checkCategoryId(category) {
    try {
        const productCount = await Product.countDocuments({
            category: category,
        });
        return productCount > 0;
    } catch (error) {
        console.log(error);
        return false;
    }
}

exports.deleteCategoryById = async (req, res) => {
    try {
        const categoryId = req.params.id;
        const category = await Category.findById(categoryId);
        if (!category) {
            res.status(404).json({ message: "Category not found" });
            return;
        }
        const categoryExists = await checkCategoryId(categoryId);
        if (!categoryExists) {
            await Category.deleteOne({ _id: new ObjectId(categoryId) });
            res.status(200).json({ message: "Category deleted successfully." });
        } else {
            res.status(500).json({
                message:
                    "There are products of this categor that cannot be deleted",
            });
        }
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
