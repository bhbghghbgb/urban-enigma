const Product = require("../models/product.model")

exports.list = function(req, res) {
    res.sendFile(__dirname.replace('app\\controllers','') + '/index.html')
}

exports.detail = function(req, res) {
    res.send("detail")
}

exports.search = function(req, res) {
    Product.findById(req.params.id, function(response) {
        res.send({result: response});
    })
}