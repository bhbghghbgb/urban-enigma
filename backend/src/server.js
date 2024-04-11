import app from "/app.js";

const {app:{port}} = require('/configs/config.mongodb.js');

const server = app.listen(port, (err, res) => {
    console.log(`server listening on ${port}`)
});

process.on("SIGINT", () => {
    server.close(() => {
        console.log("server closed");
        process.exit(0);
    })
})

module.exports = server;
