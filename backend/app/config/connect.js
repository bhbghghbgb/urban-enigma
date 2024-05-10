const mongoose = require("mongoose");
const { DATABASE_CONNECTION_URL, DATABASE_NAME } = require("./_APP");
mongoose.set("debug", true)
mongoose.connect(DATABASE_CONNECTION_URL, {
    dbName: DATABASE_NAME,
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

const db = mongoose.connection;

db.on("error", console.error.bind(console, "connection error:"));
db.once("open", function () {
    console.log("Connected to MongoDB");
});

module.exports = db;
