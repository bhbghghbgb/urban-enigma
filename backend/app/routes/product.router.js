module.exports = function (router) {
    var productController = require('../controllers/product.controller');
    const _Js = require('../config/_Js');
    router.get('/', productController.list);
    router.get('/detail', productController.detail);
    router.get('/search/:id', productController.search);


    router.get('/to', async function (req, res) {
        var user = {
            name: "Admin",
            pass: "admin",
        };
        const _to = await _Js.make(user);
        res.send({to: _to})
    })
}