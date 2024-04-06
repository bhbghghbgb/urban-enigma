"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// src/index.js
const express_1 = __importDefault(require("express"));
const dotenv_1 = require("dotenv");
require("passport");
const passport_1 = __importDefault(require("passport"));
const passport_local_1 = require("passport-local");
const jsonwebtoken_1 = require("jsonwebtoken");
const jsonresponse_util_1 = require("./utils/jsonresponse.util");
const mongoose_1 = require("mongoose");
const password_util_1 = require("./utils/password.util");
const { error } = (0, dotenv_1.config)({ path: "./.env.shared" });
if (error) {
    console.error(error);
}
const app = (0, express_1.default)();
const port = process.env.PORT || 3000;
app.use(passport_1.default.initialize());
app
    .route("/")
    .get((req, res) => {
    res.send("Express + TypeScript Server GET");
})
    .post((req, res) => {
    res.send("Express + TypeScript Server POST");
});
const accountSchema = new mongoose_1.Schema({
    username: { type: String, required: true },
    password: { type: String, required: true },
});
const Account = (0, mongoose_1.model)("Account", accountSchema);
passport_1.default.use(new passport_local_1.Strategy({
    usernameField: "username",
    passwordField: "password",
    passReqToCallback: false,
    session: false,
}, function (username, password, done) {
    try {
        Account.findOne({ username: username }, function (err, account) {
            if (err) {
                return done(err);
            }
            if (!account) {
                return done(null, false);
            }
            if (!(0, password_util_1.comparePassword)(password, account.password)) {
                return done(null, false);
            }
            return done(null, account);
        });
    }
    catch (err) {
        return done(err);
    }
}));
app.post("/login", function (req, res, next) {
    passport_1.default.authenticate("local", function (err, user, info, status) {
        if (err) {
            return next(err);
        }
        if (!user) {
            return res.status(401).json((0, jsonresponse_util_1.resultError)("User not found?"));
        }
        (0, jsonresponse_util_1.resultOk)({
            bearer: (0, jsonwebtoken_1.sign)({ username: user.username }, process.env.JWT_SECRET, { expiresIn: "7d" }),
        });
    });
});
app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW5kZXguanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi9zcmMvaW5kZXgudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7QUFBQSxlQUFlO0FBQ2Ysc0RBQThEO0FBQzlELG1DQUFnQztBQUNoQyxvQkFBa0I7QUFDbEIsd0RBQWdDO0FBQ2hDLG1EQUEyRDtBQUMzRCwrQ0FBb0M7QUFDcEMsaUVBQWtFO0FBQ2xFLHVDQUF5QztBQUN6Qyx5REFBd0Q7QUFFeEQsTUFBTSxFQUFFLEtBQUssRUFBRSxHQUFHLElBQUEsZUFBTSxFQUFDLEVBQUUsSUFBSSxFQUFFLGVBQWUsRUFBRSxDQUFDLENBQUM7QUFDcEQsSUFBSSxLQUFLLEVBQUUsQ0FBQztJQUNWLE9BQU8sQ0FBQyxLQUFLLENBQUMsS0FBSyxDQUFDLENBQUM7QUFDdkIsQ0FBQztBQUVELE1BQU0sR0FBRyxHQUFZLElBQUEsaUJBQU8sR0FBRSxDQUFDO0FBQy9CLE1BQU0sSUFBSSxHQUFHLE9BQU8sQ0FBQyxHQUFHLENBQUMsSUFBSSxJQUFJLElBQUksQ0FBQztBQUV0QyxHQUFHLENBQUMsR0FBRyxDQUFDLGtCQUFRLENBQUMsVUFBVSxFQUFFLENBQUMsQ0FBQztBQUMvQixHQUFHO0tBQ0EsS0FBSyxDQUFDLEdBQUcsQ0FBQztLQUNWLEdBQUcsQ0FBQyxDQUFDLEdBQVksRUFBRSxHQUFhLEVBQUUsRUFBRTtJQUNuQyxHQUFHLENBQUMsSUFBSSxDQUFDLGlDQUFpQyxDQUFDLENBQUM7QUFDOUMsQ0FBQyxDQUFDO0tBQ0QsSUFBSSxDQUFDLENBQUMsR0FBWSxFQUFFLEdBQWEsRUFBRSxFQUFFO0lBQ3BDLEdBQUcsQ0FBQyxJQUFJLENBQUMsa0NBQWtDLENBQUMsQ0FBQztBQUMvQyxDQUFDLENBQUMsQ0FBQztBQU1MLE1BQU0sYUFBYSxHQUFHLElBQUksaUJBQU0sQ0FBVztJQUN6QyxRQUFRLEVBQUUsRUFBRSxJQUFJLEVBQUUsTUFBTSxFQUFFLFFBQVEsRUFBRSxJQUFJLEVBQUU7SUFDMUMs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