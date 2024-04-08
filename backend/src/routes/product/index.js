'use strict'
import {Router} from 'express';

const router = Router();
import product from '../../controllers/product.controller'
// getAll
router.get('/product/getAll', product.getAll);
//getAllProductsPopular
router.get('/product/getAllProductsPopular', product.getAllProductsPopular);
//getAllProductsOfCategory
router.get('/product/getAllProductsOfCategory/:id', product.getAllProductsOfCategory);
//findProductById
router.get('/product/findProductById/:id', product.findProductById);
//findProductByName
router.get('/product/findProductByName/:name', product.findProductByName);
//createProduct
router.post('/product/createProduct', product.createProduct);
//updateProductById
router.put('/product/updateProductById/:id', product.updateProductById);
//deleteProductById
router.delete('/product/deleteProductById/:id', product.deleteProductById);
//getFullProducts
router.get('/product/getFullProducts', product.getProductsWithRating);
// Tối ưu lại getProductsWithRating nhưng chưa test
router.get('/product/getProductsWithRating_Hung/:id', product.getProductsWithRating_Hung);
module.exports = router;