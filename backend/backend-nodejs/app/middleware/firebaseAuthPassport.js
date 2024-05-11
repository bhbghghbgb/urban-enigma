const passport = require("passport");
const { Strategy: BearerStrategy } = require("passport-http-bearer");
const {
    getOrCreateAccountFromIdToken,
} = require("../service/firebaseAuthService");

// header bearer auth through api for android app
passport.use(
    new BearerStrategy(function (firebaseIdToken, done) {
        getOrCreateAccountFromIdToken(firebaseIdToken)
            .then((firebaseUser) => {
                if (firebaseUser) {
                    done(null, firebaseUser, { scope: firebaseUser.role });
                } else {
                    done(null, false);
                }
            })
            .catch((err) => done(err));
    })
);

module.exports = {
    firebaseAuthBearer: passport.authenticate("bearer", {
        session: false,
    }),
};
