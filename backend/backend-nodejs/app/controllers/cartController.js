const Cart = require("../models/cartModel");
const Product = require("../models/productModel");
const { Customer } = require("../models/userModel");
const { account2customer } = require("../service/account2shits");
const { s_updateCart } = require("../service/cartService");
const productService = require("../service/product.service")
exports.getCartOfUser = async (req, res) => {
    try {
        const customer = await account2customer(req.user);
        if (!customer) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        const carts = await Cart.findOne({ user: customer._id }).populate({
            path: "products.product",
            populate: {
                path: "category",
            },
        });
        res.status(200).json(carts);
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.addToCart = async (req, res) => {
    try {
        const customer = await account2customer(req.user);
        if (!customer) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        let cart = await Cart.findOne({ user: customer._id }).populate({
            path: "products.product",
            populate: {
                path: "category",
            },
        });
        var product = await Product.findOne({ _id: req.body.productId });
        if (!cart) {
            cart = new Cart({
                user: customer,
                total: product.price,
                products: [
                    {
                        product: product,
                        amount: 1,
                        price: product.price,
                    },
                ],
            });
            await cart.save();
            res.status(200).json({ message: "Add to cart successfully!" });
            return;
        } else {
            const existingProduct = cart.products.find(
                (p) => p.product._id == req.body.productId
            );
            if (existingProduct) {
                existingProduct.amount += 1;
                existingProduct.price =
                    existingProduct.product.price * existingProduct.amount;
                cart.total += existingProduct.product.price;
            } else {
                cart.products.push({
                    product: product,
                    amount: 1,
                    price: product.price,
                });
                cart.total += product.price;
            }
            await Cart.updateOne({ user: customer._id }, { $set: cart });
            res.status(200).json({ message: "Add to cart successfully" });
            return;
        }
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.deleteProductOfCart = async (req, res) => {
    try {
        const customer = await account2customer(req.user);
        if (!customer) {
            res.status(404).json({ message: "User is not found" });
            return;
        }
        var product = await Product.findOne({ _id: req.body.productId });
        if (!product) {
            res.status(404).json({ message: "Product is not found" });
            return;
        }
        const cart = await Cart.findOne({ user: customer._id }).populate({
            path: "products.product",
            populate: {
                path: "category",
            },
        });
        var isProductInCart = cart.products.find(
            (product) => product.product._id.toString() == req.body.productId
        );
        if (!isProductInCart) {
            res.status(404).json({ message: "Product is not in cart" });
            return;
        } else {
            cart.total -= isProductInCart.price;
            cart.products.remove(isProductInCart);
            cart.save();
            res.status(201).json({
                message: "Product has been removed from the cart",
            });
            return;
        }
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.increaseProductOfCart = async (req, res) => {
    try {
        const customer = await account2customer(req.user);
        if (!customer) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        const user = customer._id;
        let cart = await Cart.findOne({ user: user }).populate({
            path: "products.product",
            populate: {
                path: "category",
            },
        });
        const product = cart.products.find(
            (p) => p.product._id.toString() === req.params.productId
        );
        if (!product) {
            res.status(404).json({ message: "Product not found in cart" });
            return;
        }
        product.amount += 1;
        product.price = product.product.price * product.amount;
        cart.total += product.price.price;
        await Cart.updateOne({ user: user }, { $set: cart });
        res.status(200).json({ message: "Increase successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.decreaseProductOfCart = async (req, res) => {
    try {
        const customer = await Customer.findOne({ user: req.params.user });
        if (!customer) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        const user = req.params.user;
        let cart = await Cart.findOne({ user: user }).populate(
            "products.product"
        );
        const productIndex = cart.products.findIndex(
            (p) => p.product._id.toString() === req.body.product
        );
        cart.total -= cart.products[productIndex].price;
        cart.products[productIndex].amount -= 1;
        cart.products[productIndex].price =
            cart.products[productIndex].amount *
            cart.products[productIndex].product.price;
        if (cart.products[productIndex].amount == 0) {
            cart.products.splice(productIndex, 1);
        }
        await Cart.updateOne({ user: user }, { $set: cart });
        res.status(200).json({ message: "Decrease successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.resetCart = async (req, res) => {
    try {
        const customer = await account2customer(req.user);
        if (!customer) {
            res.status(404).json({ message: "User not found" });
            return;
        }
        await Cart.updateOne(
            { user: customer },
            { $set: { total: 0, products: [] } }
        );
        res.status(201).json({ message: "Reset successfully" });
        return;
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

exports.updateCart = async function (req, res) {
    try {
        const customer = await account2customer(req.user);
        console.log("[P]:::Update Cart:::")
        // Nhận vào cả một cart, nhưng chỉ cần lấy products
        const products = req.body.products;

        // Check if any property in products is null
        if (products?.length <= 0) {
            return res.status(400).json({message: 'Products cannot be null'});
        }

        for (const item of products) {
            console.log("[P]:::Product:::", item)
            if (item?.amount == null) {
                return res.status(400).json({message: 'Product or amount cannot be null'});
            }
        }

        // Map all products in the cart
        const updatedProducts = await Promise.all(products.map(async item => {
            const product = await productService.findProductById(item.product);
            return {
                product: item.product,
                amount: item.amount,
                price: product.price
            };
        }));

        const cart = await s_updateCart(customer._id, updatedProducts);
        res.status(200).json({message: 'Update cart successfully!', metadata: cart});
    } catch (error) {
        res.status(error.statusCode || 500).json({message: error.message});
    }
};
