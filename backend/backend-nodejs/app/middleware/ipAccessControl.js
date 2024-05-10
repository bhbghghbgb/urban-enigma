const { WHITELIST_IPS } = require("../config/_APP");
exports.ipAccessControl = require("express-ip-access-control")({
    mode: "allow",
    allows: WHITELIST_IPS,
    statusCode: 403,
    message: "Forbidden, this client IP is not whitelisted.",
});
