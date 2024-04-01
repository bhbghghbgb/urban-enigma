const express = require('express');
const router = express.Router();
const Product = require('../models/product');

// Lấy danh sách sản phẩm
router.get('/products', async (req, res) => {
  try {
    const products = await Product.find();
    res.json(products);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

// Thêm sản phẩm mới
router.post('/products', async (req, res) => {
  const product = new Product({
    product_id: req.body.product_id,
    name: req.body.name,
    category: req.body.category,
    price: req.body.price
  });

  try {
    const newProduct = await product.save();
    res.status(201).json(newProduct);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
});

// Sửa thông tin sản phẩm
router.put('/products/:id', async (req, res) => {
  // Implement your update logic here
});

// Xóa sản phẩm
router.delete('/products/:id', async (req, res) => {
  // Implement your delete logic here
});

module.exports = router;