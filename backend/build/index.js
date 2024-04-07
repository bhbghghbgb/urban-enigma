"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// src/index.js
const express_1 = __importDefault(require("express"));
const mongoose_1 = require("mongoose");
const dotenv_1 = require("dotenv");
const passport_1 = __importDefault(require("passport"));
const passport_local_1 = require("passport-local");
const passport_jwt_1 = require("passport-jwt");
const jsonwebtoken_1 = require("jsonwebtoken");
const jsonresponse_util_1 = require("./utils/jsonresponse.util");
const password_util_1 = require("./utils/password.util");
const { error } = (0, dotenv_1.config)({ path: "./.env.shared" });
if (error) {
    console.error(error);
}
const app = (0, express_1.default)();
const port = process.env.PORT || 3000;
app.use(passport_1.default.initialize());
app.use(express_1.default.json());
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
    userType: { type: String, required: true },
});
const Account = (0, mongoose_1.model)("Account", accountSchema);
// passport strategy for logging in with username/password pair when not logged in yet
// subsequent authorized actions should use bearer token
passport_1.default.use(new passport_local_1.Strategy({
    usernameField: "username",
    passwordField: "password",
    passReqToCallback: false,
    session: false,
}, function (username, password, done) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const account = yield Account.findOne({ username });
            if (!account) {
                return done(null, false, { message: "Username not found." });
            }
            const passwordMatch = yield (0, password_util_1.comparePassword)(password, account.password);
            if (!passwordMatch) {
                return done(null, false, { message: "Password mismatch." });
            }
            return done(null, account, {
                message: `Logging in as ${username}, role ${account.userType}`,
            });
        }
        catch (err) {
            return done(err, { message: err.message });
        }
    });
}));
// passport strategy for authorized actions with a bearer token
passport_1.default.use(new passport_jwt_1.Strategy({
    secretOrKey: process.env.JWT_SECRET,
    jwtFromRequest: passport_jwt_1.ExtractJwt.fromAuthHeaderAsBearerToken(),
    passReqToCallback: true,
}, function (req, jwtPayload, done) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const username = jwtPayload.username;
            const account = yield Account.findOne({ username: username });
            if (!account) {
                return done(null, false, { message: "Malformed bearer token." });
            }
            return done(null, account, {
                message: `Bearer authorized ${username}. Role ${account.userType}`,
            });
        }
        catch (err) {
            return done(err, { message: err.message });
        }
    });
}));
app.post("/login", function (req, res, next) {
    return passport_1.default.authenticate("local", function (err, account, info, status) {
        if (err) {
            return next(err);
        }
        if (!account) {
            return res
                .status(401)
                .json((0, jsonresponse_util_1.resultError)("User not found?", info, status));
        }
        return res.json((0, jsonresponse_util_1.resultOk)({
            bearer: (0, jsonwebtoken_1.sign)({ username: account.username }, process.env.JWT_SECRET, { expiresIn: "7d" }),
            info,
            status,
        }));
    });
});
app.get("/test/authorized/admin", function (req, res, next) {
    passport_1.default.authenticate("jwt", function (err, account, info, status) {
        if (err) {
            return next(err);
        }
        if (!account ||
            !account.roles ||
            !account.roles.includes("admin")) {
            return res
                .status(401)
                .json((0, jsonresponse_util_1.resultError)("No admin access.", info, status));
        }
        return res.json((0, jsonresponse_util_1.resultOk)(info, "Admin authorized.", status));
    });
});
app.post("/signup", function (req, res) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const username = req.body.username;
            const hashedPassword = yield (0, password_util_1.hashPassword)(req.body.password);
            if ((yield Account.countDocuments({ username })) > 0) {
                return res
                    .status(409)
                    .json((0, jsonresponse_util_1.resultError)(`Username exists. (${username})`));
            }
            const userType = req.body.userType;
            yield Account.create({
                username,
                password: hashedPassword,
                userType: userType,
            });
            return res.json((0, jsonresponse_util_1.resultOk)(undefined, `Account created. Username ${username}. Role ${userType}`));
        }
        catch (err) {
            return res.status(500).json((0, jsonresponse_util_1.resultError)(err.message));
        }
    });
});
app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW5kZXguanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi9zcmMvaW5kZXgudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7QUFBQSxlQUFlO0FBQ2Ysc0RBQThEO0FBQzlELHVDQUF5QztBQUN6QyxtQ0FBZ0M7QUFFaEMsd0RBQWdDO0FBQ2hDLG1EQUEyRDtBQUMzRCwrQ0FBbUU7QUFDbkUsK0NBQW9DO0FBRXBDLGlFQUFrRTtBQUNsRSx5REFBc0U7QUFFdEUsTUFBTSxFQUFFLEtBQUssRUFBRSxHQUFHLElBQUEsZUFBTSxFQUFDLEVBQUUsSUFBSSxFQUFFLGVBQWUsRUFBRSxDQUFDLENBQUM7QUFDcEQsSUFBSSxLQUFLLEVBQUUsQ0FBQztJQUNWLE9BQU8sQ0FBQyxLQUFLLENBQUMsS0FBSyxDQUFDLENBQUM7QUFDdkIsQ0FBQztBQUVELE1BQU0sR0FBRyxHQUFZLElBQUEsaUJBQU8sR0FBRSxDQUFDO0FBQy9CLE1BQU0sSUFBSSxHQUFHLE9BQU8sQ0FBQyxHQUFHLENBQUMsSUFBSSxJQUFJLElBQUksQ0FBQztBQUV0QyxHQUFHLENBQUMsR0FBRyxDQUFDLGtCQUFRLENBQUMsVUFBVSxFQUFFLENBQUMsQ0FBQztBQUMvQixHQUFHLENBQUMsR0FBRyxDQUFDLGlCQUFPLENBQUMsSUFBSSxFQUFFLENBQUMsQ0FBQztBQUV4QixHQUFHO0tBQ0EsS0FBSyxDQUFDLEdBQUcsQ0FBQztLQUNWLEdBQUcsQ0FBQyxDQUFDLEdBQVksRUFBRSxHQUFhLEVBQUUsRUFBRTtJQUNuQyxHQUFHLENBQUMsSUFBSSxDQUFDLGlDQUFpQyxDQUFDLENBQUM7QUFDOUMsQ0FBQyxDQUFDO0tBQ0QsSUFBSSxDQUFDLENBQUMsR0FBWSxFQUFFLEdBQWEsRUFBRSxFQUFFO0lBQ3BDLEdBQUcsQ0FBQyxJQUFJLENBQUMsa0NBQWtDLENBQUMsQ0FBQztBQUMvQyxDQUFDLENBQUMsQ0FBQztBQVVMLE1BQU0sYUFBYSxHQUFHLElBQUksaUJBQU0sQ0FBVztJ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