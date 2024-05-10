const mongoose = require("mongoose");
const admin = require("firebase-admin");
const { DATABASE_CONNECTION_URL, DATABASE_NAME } = require("./_APP");
const { testFirebaseAuth } = require("../utils/firebase");

// LUU Y phai tu lay service account creds cua project ca nhan tren firebase
// cai nay ko push len github dc vi se dc auto scan sau do bi disable
// const serviceAccount = require("./doanandroid-banhang-firebase-adminsdk-p80ax-e3465e55dd.json");

mongoose.set("debug", true);

var mongodbConnection;
var firebaseApp;

function initConnection(callback) {
    initMongodb(() => initFirebase(callback));
}
function initMongodb(callback) {
    mongoose.connect(DATABASE_CONNECTION_URL, {
        dbName: DATABASE_NAME,
        useNewUrlParser: true,
        useUnifiedTopology: true,
    });
    mongodbConnection = mongoose.connection;
    mongodbConnection.on("error", function (err) {
        console.error("Failed to connect to mongodb", err);
        process.exit(1);
    });

    mongodbConnection.once("open", function () {
        console.info("Connected to MongoDB");
        callback();
    });
}
function initFirebase(callback) {
    try {
        firebaseApp = admin.initializeApp({
            credential: admin.credential.cert(serviceAccount),
        });
        console.info("Connect Firebase without errors");
        // test get an user
        testFirebaseAuth()
            .then((success) => {
                if (success) {
                    console.info("Firebase connection test success");
                    return;
                }
                console.error(
                    "Firebase connection test failed. Auth will not work, server can continue."
                );
            })
            .finally(() => callback());
    } catch (err) {
        if (!(err instanceof ReferenceError)) {
            console.error(err);
        } else {
            console.error("Firebase credentials not given.");
        }
        console.info(
            "Failed to connect to Firebase. Auth will not work, server can continue"
        );
        callback();
    }
}
module.exports = { mongodbConnection, firebaseApp, initConnection };
