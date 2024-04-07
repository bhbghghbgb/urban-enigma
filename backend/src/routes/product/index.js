'use strict'
import {Router} from 'express';

const router = Router();
const product = require('../../controllers/product.controller');
// getAll
router.post('/product/getAll', product.getAll);
//getAllProductsPopular
router.post('/product/getAllProductsPopular', product.getAllProductsPopular);
//getAllProductsOfCategory
router.post('/product/getAllProductsOfCategory', product.getAllProductsOfCategory);
//findProductById
router.post('/product/findProductById', product.findProductById);
//findProductByName
router.post('/product/findProductByName', product.findProductByName);
//createProduct
router.post('/product/createProduct', product.createProduct);
//updateProductById
router.post('/product/updateProductById', product.updateProductById);
//deleteProductById
router.post('/product/deleteProductById', product.deleteProductById);
//getFullProducts
router.post('/product/getFullProducts', product.getFullProducts);
module.exports = router;