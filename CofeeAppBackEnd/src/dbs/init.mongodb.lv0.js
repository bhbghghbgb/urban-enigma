'use strict';
/**
 * Khi gọi require thì đã kết nối rồi và không gọi lại nữa
 * Nhưng trong các công nghệ khác như java hay php lại gọi tới 1 kết nối mới
 * Dẫn đến quá nhiều kết nối tới database
 * */
const mongoose = require('mongoose')
const config = 'mongodb://localhost:27017';
mongoose.connect(config).then(e => {
    console.log('Connected to MongoDB');
}).catch(err => console.log("Couldn't connect to MongoDB'"))
//dev
/**
 * vì điều kiện 1 === 0 là sai, nên các dòng lệnh trong khối if này sẽ không bao giờ được thực thi trong quá trình chạy của chương trình.
 * Điều này đảm bảo rằng cờ debug của Mongoose sẽ không bao giờ được đặt trong môi trường sản xuất, nơi bạn không muốn xuất ra các thông
 * báo gỡ lỗi lên console để bảo vệ dữ liệu và tăng hiệu suất của ứng dụng.
 * */
if (1===0) {
    mongoose.set('debug', true);//Xuất hiện các thông báo gỡ lỗi lên console khi có các hoạt động liên quan đến cơ sở dự liệu
    mongoose.set('debug', {color: true});
}


module.exports = mongoose