const { StatusCodes } = require("http-status-codes");
const { getFirebaseUserFromUid } = require("../service/firebaseUserService");
async function getUser(req, res) {
    const user = req.user;
    console.info("Getting debug user info for ", user.username);
    return res.json({
        user,
        firebaseUser: await getFirebaseUserFromUid(user.username),
    });
}
async function testAuthorization(req, res) {
    return res.sendStatus(
        req.user ? StatusCodes.NO_CONTENT : StatusCodes.UNAUTHORIZED,
    );
}
module.exports = { getUser, testAuthorization };
