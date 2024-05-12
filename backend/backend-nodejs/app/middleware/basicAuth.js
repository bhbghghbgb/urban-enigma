const passport = require("passport");
const { BasicStrategy } = require("passport-http");
const Product = require("../models/productModel");
// basic auth through browser for testing passport
// use a Product's *_id* field as *username* and *image* field as *password*
passport.use(
    new BasicStrategy(async function (username, password, done) {
        console.info(`Passport basic auth login test ${username} ${password}`);
        try {
            const product = await Product.findById(username);
            if (!product) {
                console.info("Auth fail: no such product _id");
                return done(null, false);
            }
            if (product.image !== password) {
                console.info("Auth fail: password mismatch");
                return done(null, false);
            }
            console.info("Auth success");
            return done(null, product);
        } catch (err) {
            console.info("Auth fail: error ", err);
            return done(err);
        }
    }),
);

module.exports = {
    basicAuth: passport.authenticate("basic", {
        session: false,
    }),
};
