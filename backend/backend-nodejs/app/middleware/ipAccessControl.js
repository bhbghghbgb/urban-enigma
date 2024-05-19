const { WHITELIST_IPS } = require("../config/_APP");
const { getClientIp } = require("request-ip");
const { ipMatch } = require("express-ip-access-control");
const { StatusCodes } = require("http-status-codes");
async function ipAccessControl(req, res, next) {
    const realIp = getClientIp(req) || req.connection.remoteAddress;
    const allowed = ipMatch(realIp, WHITELIST_IPS);
    const logStr = `${req.connection.remoteAddress} (${
        realIp === req.connection.remoteAddress
            ? "no forwarding"
            : `forwarded for ${realIp}`
    }) ${allowed ? "accessed" : "denied"}.`;
    console.log(logStr);
    if (!allowed) {
        return res
            .status(StatusCodes.FORBIDDEN)
            .send(`Forbidden, this client IP ${logStr} is not whitelisted.`);
    }
    next();
}

module.exports = { ipAccessControl };
