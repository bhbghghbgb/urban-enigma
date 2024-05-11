const ConnectRoles = require("connect-roles");
const { StatusCodes } = require("http-status-codes");

const userRole = new ConnectRoles({
    failureHandler: function (req, res, action) {
        // optional function to customise code that runs when
        // user fails authorisation
        res.status(StatusCodes.FORBIDDEN).json({
            message: `Access denined. Your role ${req.user.role} doesn't have permission to ${action}`,
        });
    },
});

userRole.use("customer", function (req) {
    return req.user.role === "customer";
});
userRole.use("staff", function (req) {
    return req.user.role === "staff";
});
userRole.use("admin", function (req) {
    return req.user.role === "admin";
});

// TEST - use Product as User, test role using popular boolean:true/false
const productRole = new ConnectRoles({
    failureHandler: function (req, res, action) {
        // optional function to customise code that runs when
        // user fails authorisation
        res.status(StatusCodes.FORBIDDEN).json({
            message: `Access denined. Product role popular:${req.user.popular} mismatch ${action}`,
        });
    },
});
productRole.use("ispopular", function (req) {
    return req.user.popular;
});
productRole.use("nopopular", function (req) {
    return !req.user.popular;
});
module.exports = {
    userRole,
    productRole,
};
