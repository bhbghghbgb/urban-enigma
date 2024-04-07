'use strict'
import {Router} from 'express';

const router = Router();
import product from '../../controllers/product.controller'
// getAll
router.get('/product/getAll', product.getAll);
//getAllProductsPopular
router.get('/product/getAllProductsPopular', product.getAllProductsPopular);
//getAllProductsOfCategory
router.get('/product/getAllProductsOfCategory', product.getAllProductsOfCategory);
//findProductById
router.get('/product/findProductById', product.findProductById);
//findProductByName
router.get('/product/findProductByName', product.findProductByName);
//createProduct
router.post('/product/createProduct', product.createProduct);
//updateProductById
router.put('/product/updateProductById', product.updateProductById);
//deleteProductById
router.delete('/product/deleteProductById', product.deleteProductById);
//getFullProducts
router.get('/product/getFullProducts', product.getFullProducts);
module.exports = router;