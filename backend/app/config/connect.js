const mongoose = require("mongoose");
const { DATABASE_CONNECTION_URL } = require("./_APP");
mongoose.connect(DATABASE_CONNECTION_URL, {
    dbName: "CoffeeApp",
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

const db = mongoose.connection;

db.on("error", console.error.bind(console, "connection error:"));
db.once("open", function () {
    console.log("Connected to MongoDB");
});

module.exports = db;
