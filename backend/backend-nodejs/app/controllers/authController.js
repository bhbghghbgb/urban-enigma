const { getFirebaseUserFromUid } = require("../service/firebaseUserService");
async function getUser(req, res) {
    const user = req.user;
    console.info("Getting debug user info for ", user.username);
    return res.json({
        user,
        firebaseUser: await getFirebaseUserFromUid(user.username),
    });
}
module.exports = { getUser };
