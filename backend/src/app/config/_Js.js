const jwt = require('jsonwebtoken');

const ACCESS_TOKEN = process.env.ACCESS_TOKEN
const TOKEN_TIME_LIFE = process.env.TOKEN_TIME_LIFE

// make => tạo mã token
let make = function (user) {
    return new Promise(function (resolve, reject) {
        jwt.sign({ data: user }, ACCESS_TOKEN, {
            algorithm: "HS256",
            expiresIn: TOKEN_TIME_LIFE,
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