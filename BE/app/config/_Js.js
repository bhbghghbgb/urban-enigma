const jwt = require('jsonwebtoken');
const _APP = require('./_APP');
// make => tạo mã token
let make = function (user) {
    return new Promise(function (resolve, reject) {
        jwt.sign({ data: user }, _APP.ACCESS_TOKEN, {
            algorithm: "HS256",
            expiresIn: _APP.TOKEN_TIME_LIFE,
        },
            function (err, _token) {
                if (err) return reject(err);
                return resolve(_token);
            });
    })
}
// check => xác thực mã đúng, sai, hết hạn

module.exports = {
    make: make,
}