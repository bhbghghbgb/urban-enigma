const passport = require("passport");
const BearerStrategy = require("passport-http-bearer");
const { getFirebaseUidFromIdToken } = require("../service/firebaseAuthService");
passport.use(
    new BearerStrategy(function (firebaseIdToken, done) {
        // firebaseUid is req.user
        getFirebaseUidFromIdToken(firebaseIdToken)
            .then((firebaseUid) => {
                done(null, firebaseUid);
            })
            .catch((err) => done(err));
    })
);

exports.firebaseAuthBearer = passport.authenticate("bearer", {
    session: false,
});
