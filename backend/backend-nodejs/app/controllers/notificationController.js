exports.bindDeviceToAccount = async function(req, res) {
    const account = req.user
    account.notificationDeviceToken = req.query["device-token"]
    await account.save()
}

exports.unbindDeviceFromAccount = async function(req, res) {
    const account = req.user
    account.notificationDeviceToken = null
    await account.save()
}