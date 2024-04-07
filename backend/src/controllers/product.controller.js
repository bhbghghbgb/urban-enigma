'use strict'
import {
    getProducts,
    getAllProductsPopular as _getAllProductsPopular,
    getAllProductsOfCategory as _getAllProductsOfCategory,
    findProductById as _findProductById,
    findProductByName as _findProductByName,
    createProduct as _createProduct,
    updateProductById as _updateProductById,
    deleteProductById as _deleteProductById,
    getFullProducts as _getFullProduct
} from '../services/product.service';

class productController {

    getAll = async (req, res) => {
        // handle error
        try {
            console.log('[P]::Get All Product::')
            /*
            * 200 OK
            * 201 Created
            * */
            const products = await getProducts();
            res.status(200).json(products);
        } catch (error) {
            res.status(500).json({message: error.message});
        }
    }

    getAllProductsPopular = async (req, res) => {
        try {
            const products = await _getAllProductsPopular();
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }
    getAllProductsOfCategory = async (req, res) => {
        try {
            const products = await _getAllProductsOfCategory(req.params.id);
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }
    findProductById = async (req, res) => {
        try {
            const product = await _findProductById(req.params.id);
            if (product) {
                res.status(200).json(product);
            } else {
                res.status(404).json({message: 'Product is not fount'});
            }
            return;
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

    findProductByName = async (req, res) => {
        try {
            const product = await _findProductByName(req.params.name);
            if (product) {
                res.status(200).json(product);
            } else {
                res.status(404).json({message: 'Product is not fount'});
            }
            return;
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

    createProduct = async (req, res) => {
        try {
            const product = await _createProduct(req.body);
            res.status(201).json({message: 'Product created successfully'});
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

    updateProductById = async (req, res) => {
        try {
            const product = await _updateProductById(req.params.id, req.body);
            res.status(200).json({message: 'Product updated successfully'});
        } catch (err) {
              res.status(err.statusCode).json({message: err.message});
        }
    }

    deleteProductById = async (req, res) => {
        try {
            const product = await _deleteProductById(req.params.id);
            res.status(200).json({message: 'Product deleted successfully'});
        } catch (err) {
            if (err.statusCode === 404) {
                res.status(404).json({message: err.message});
            } else res.status(500).json({message: err.message});
        }
    }

    getFullProducts = async (req, res) => {
        try {
            const products = await _getFullProduct();
            res.status(200).json(products);
        } catch (err) {
            res.status(500).json({message: err.message});
        }
    }

}

const Product = new productController()
module.exports = Product;