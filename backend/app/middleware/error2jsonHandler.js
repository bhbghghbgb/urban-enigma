exports.error2jsonHandler = (err, req, res, next) => {
    res.status(err.status).json({
        statusCode: err.status,
        message: err.message,
    });
    return next();
};
