import Order from '../models/oder.model';

const getAllOrders = async () => {
    try {
        return await Order.find().populate('detailOrders.product');
    } catch (err) {
        throw err;
    }
}

const findOrderById = async (id) => {
    const order = await Order.findById(id).populate('detailOrders.product');
    if (order) {
        return order;
    } else {
        const error = new Error('Order not found');
        error.statusCode = 404;
        throw error
    }
}

const createOrder = async (data) => {
    try {
        const {customer, orderDateTime, status, deliveryLocation, note, discount, detailOrders} = data;
        const parsedDetailOrders = JSON.parse(detailOrders);
        await Order.create({
            customer,
            orderDateTime,
            status,
            deliveryLocation,
            note,
            discount,
            detailOrders: parsedDetailOrders,
        })
    } catch (err) {
        throw err;
    }
}

const updateStatus = async (orderId, statusData) => {
    try {
        const order = await Order.findById(orderId);
        if (!order) {
            const error = new Error('Order not found');
            error.statusCode = 404;
            throw error;
        }
        const { status } = statusData;
        await Order.updateOne({ _id: orderId }, { $set: { status } });
    } catch (err) {
        throw err;
    }
}


const getOrdersOfStatus = async (status) => {
    try {
        return await Order.find({status: status}).populate('detailOrders.product');
    } catch (err) {
        throw err;
    }
}

module.exports = {
    getAllOrders,
    findOrderById,
    createOrder,
    updateStatus,
    getOrdersOfStatus,
}

