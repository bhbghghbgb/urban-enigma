const ACCESS_TOKEN = "ABCDEFBHDBVNSKCMKMAIWUHFHBHDBVDVS";
const TOKEN_TIME_LIFE = "86400";

// Supply the real Atlas SRV string through the DATABASE_CONNECTION_URL
// environment variable; the literal below is a placeholder and will not connect.
const DATABASE_CONNECTION_URL =
    process.env.DATABASE_CONNECTION_URL ||
    "mongodb+srv://<user>:<password>@<cluster-host>/?retryWrites=true&w=majority&appName=<app>";

module.exports = {
    DATABASE_CONNECTION_URL: DATABASE_CONNECTION_URL,
    DATABASE_NAME: "do-an-android",
    FIREBASE_AUTH_USER_TEST_EMAIL: "firebase-test-user@example.com",
    ACCESS_TOKEN: ACCESS_TOKEN,
    TOKEN_TIME_LIFE: TOKEN_TIME_LIFE,
    WHITELIST_IPS: ["127.0.0.1", "localhost", "::1", "10.0.2.2", "10.0.3.2"],
};
