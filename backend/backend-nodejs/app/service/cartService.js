const { Customer } = require("../models/userModel");
const Cart = require("../models/cartModel");
const Product = require("../models/productModel");
const s_updateCart = async (customerId, total, products) => {
    // Tìm kiếm customer
    const customer = await Customer.findById(customerId);
    if (!customer) {
        const error = new Error("Customer not found");
        error.statusCode = 404;
        throw error;
    }

    // Tìm kiếm giỏ hàng hiện tại của khách hàng
    let currentCart = await Cart.findOne({ customer: customerId }).populate(
        "products.product"
    );

    // Nếu không tìm thấy giỏ hàng hiện tại, tạo mới giỏ hàng
    if (!currentCart) {
        currentCart = new Cart({
            customer: customerId,
            total: 0,
            products: [],
        });
    }

    // Cập nhật total của giỏ hàng
    currentCart.total = total;

    // Cập nhật sản phẩm trong giỏ hàng
    const updatedProducts = [];
    for (const productDetail of products) {
        // console.log("current")
        // currentCart.products.forEach(e => console.log(e.product))
        // console.log("Detail")
        // console.log(productDetail.product)
        const productIndex = currentCart.products.findIndex((p) =>
            p.product._id.equals(productDetail.product._id)
        );
        if (productIndex !== -1) {
            // Cập nhật số lượng sản phẩm hiện có
            currentCart.products[productIndex].amount = productDetail.amount;
            updatedProducts.push(currentCart.products[productIndex]);
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
            const product = await Product.findById(productDetail.productId);
            if (!product) {
                const error = new Error("Product not found");
                error.statusCode = 404;
                throw error;
            }
            updatedProducts.push({
                product: product._id,
                amount: productDetail.amount,
                price: product.price,
            });
        }
    }

    // Gán danh sách sản phẩm đã cập nhật vào giỏ hàng
    currentCart.products = updatedProducts;

    // Lưu giỏ hàng đã cập nhật
    return await currentCart.save();
};
module.exports = {
    s_updateCart,
};
