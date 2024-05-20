// import Order from '../models/oder.model';

const Order = require('../models/orderModel');
const ProductService = require('../service/product.service');
// const {CustomError} = require("../middleware/CustomError.middleware");

class OderService {

    findOrderById = async (id) => {
        const order = await Order.findById(id).populate('detailOrders.product').exec();
        if (order) {
            return order;
        } else {
            const error = new Error('Order not found');
            error.statusCode = 404;
            throw error
        }
    }

    createOrder = async (products, discount, deliveryLocation, note, payment, customer) => {
        var total = 0;
        await Promise.all(products.map(async item => {
            const product = await ProductService.findProductById(item.product);
            total += (product.price * item.amount) - (discount * 10);
        }));

        return await Order.create({
            user: customer,
            orderDateTime: new Date(),
            deliveryLocation: deliveryLocation,
            note: note,
            discount: discount,
            detailOrders: products,
            totalPrice: total,
            paymentMethod: payment
        });
    }
    updateStatus = async (orderId, statusData) => {
        const order = await Order.findById(orderId).exec();
        if (!order) {
            const error = new Error('Order not found');
            error.statusCode = 404;
            throw error;
        }
        return await Order.updateOne({_id: orderId}, {$set: {status: statusData}}).exec();
    }

}

module.exports = new OderService();

