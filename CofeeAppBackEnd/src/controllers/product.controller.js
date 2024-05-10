'use strict'
// import {
//     getProducts,
//     getAllProductsPopular as _getAllProductsPopular,
//     getAllProductsOfCategory as _getAllProductsOfCategory,
//     findProductById as _findProductById,
//     findProductByName as _findProductByName,
//     createProduct as _createProduct,
//     updateProductById as _updateProductById,
//     deleteProductById as _deleteProductById,
//     getProductsWithRating as _getProductsWithRating,
//     getProductsWithRating_Hung as _getProductsWithRating_Hung
// } from '../../../../WebstormProjects/CofeeBackEnd/src/services/product.service';
const productService = require('../services/product.service')

class productController {

    getAll = async (req, res) => {
        // handle error
        try {
            console.log('[P]::Get All Product::')
            /*
            * 200 OK
            * 201 Created
            * */
            const products = await productService.getProducts();
            res.status(200).json(products);
        } catch (error) {
            res.status(500).json({message: error.message});
        }
    }

    getAllProductsPopular = async (req, res) => {
        try {
            const products = await productService.getAllProductsPopular();
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }
    getAllProductsOfCategory = async (req, res) => {
        try {
            const products = await productService.getAllProductsOfCategory(req.params.id);
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }
    findProductById = async (req, res) => {
        try {
            const product = await productService.findProductById(req.params.id);
            if (product) {
                res.status(200).json(product);
            }
        } catch (err) {
            res.status(err.status? err.status: 500).json({message: err.message});
        }
    }

    findProductByName = async (req, res) => {
        try {
            const product = await productService.findProductByName(req.params.name);
            if (product) {
                res.status(200).json(product);
            }
        } catch (err) {
            res.status(err.status? err.status: 500).json({message: err.message});
        }
    }

    createProduct = async (req, res) => {
        try {
            const product = await productService.createProduct(req.body);
            res.status(201).json({message: 'Product created successfully', metadata: product});
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

    updateProductById = async (req, res) => {
        try {
            const product = await productService.updateProductById(req.params.id, req.body);
            res.status(200).json({message: 'Product updated successfully', metadata: product});
        } catch (err) {
              res.status(err.status? err.status: 500).json({message: err.message});
        }
    }

    deleteProductById = async (req, res) => {
        try {
            const product = await productService.deleteProductById(req.params.id);
            res.status(200).json({message: 'Product deleted successfully', metadata: product});
        } catch (err) {
            res.status(err.status? err.status: 500).json({message: err.message});
        }
    }

    getProductsWithRating = async (req, res) => {
        try {
            const products = await productService.getProductsWithRating();
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

    getProductsWithRating_Hung = async (req, res) => {
        try {
            const products = await productService.getProductsWithRating_Hung();
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }
}

const Product = new productController()
module.exports = Product;