'use strict'
import {Router} from 'express';

const router = Router();
import product from '../../controllers/product.controller'
// getAll
router.get('/product', product.getAll);
//getAllProductsPopular
router.get('/products/popular', product.getAllProductsPopular);
//getAllProductsOfCategory
router.get('/products/category/:id', product.getAllProductsOfCategory);
//findProductById
router.get('/products/:id', product.findProductById);
//findProductByName
router.get('/products/name/:name', product.findProductByName);
//createProduct
router.post('/products', product.createProduct);
//updateProductById
router.put('/products/update/:id', product.updateProductById);
//deleteProductById
router.delete('/product/delete/:id', product.deleteProductById);
//getFullProducts
router.get('/product/rating', product.getProductsWithRating);
// Tối ưu lại getProductsWithRating nhưng chưa test
router.get('/product/rating_H', product.getProductsWithRating_Hung);
module.exports = router;