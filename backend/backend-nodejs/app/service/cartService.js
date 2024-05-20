const { Customer } = require("../models/userModel");
const Cart = require("../models/cartModel");
const Product = require("../models/productModel");
const s_updateCart = async (customerId, products) => {
    // Find or create the cart
    const currentCart = await Cart.findOneAndUpdate(
        { customer: customerId },
        { $set: { customer: customerId, total: 0, products: [] } },
        { upsert: true, new: true }
    );

    // Update products in the cart
    const updatedProducts = [];
    for (const productDetail of products) {
        const productIndex = currentCart.products.findIndex((p) => p.product.equals(productDetail.product._id));
        if (productIndex !== -1) {
            // Update product amount
            currentCart.products[productIndex].amount = productDetail.amount;
            updatedProducts.push(currentCart.products[productIndex]);
        } else {
            // Get product by id
            const product = await ProductService.findProductById(productDetail.product);
            // Add new product to cart
            updatedProducts.push({
                product: productDetail.product,
                amount: productDetail.amount,
                price: product.price,
            });
        }
    }

    // Update cart products
    currentCart.products = updatedProducts;

    // Save the cart
    return await currentCart.save();
};
module.exports = {
    s_updateCart,
};
