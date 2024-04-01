const db = require('../config/connect');

const Product = function (product) {
    this.id = book.id;
    this.name = book.name;
}

Product.get_All = function (result) {
    db.query('SELECT * FROM PRODUCT', function (err, result) {
        if (err) {
            result(null);
        } else {
            result(result);
        }
    })
}

Product.findById = function(id, result) {
    db.query(`SELECT * FROM PRODUCT WHERE PRODUCTID = ${id}`, function(err, result) {
        if(err) {
            result(null);
        } else {
            result(result);
        }
    })
}

module.exports = Product;