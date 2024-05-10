'use strict'
// import {Router} from 'express';

const { Router } = require('express');
const router = Router();
// import product from '../../controllers/product.controller'
const product = require('../../controllers/product.controller');
// getAll
router.get('/products', product.getAll);
//getAllProductsPopular
router.get('/product/popular', product.getAllProductsPopular);
//getAllProductsOfCategory
router.get('/product/category/:id', product.getAllProductsOfCategory);
//findProductById
router.get('/product/:id', product.findProductById);
//findProductByName
router.get('/product/name/:name', product.findProductByName);
//createProduct
router.post('/product/create', product.createProduct);
//updateProductById
router.put('/product/update/:id', product.updateProductById);
//deleteProductById
router.delete('/product/delete/:id', product.deleteProductById);
//getFullProducts
router.get('/product/rating', product.getProductsWithRating);
// Tối ưu lại getProductsWithRating nhưng chưa test
router.get('/product/rating_H', product.getProductsWithRating_Hung);
module.exports = router;