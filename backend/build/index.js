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
    passReqToCallback: true,
    session: false,
}, function (req, username, password, done) {
    return __awaiter(this, void 0, void 0, function* () {
        console.log(req);
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
        console.log(req);
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
                .json((0, jsonresponse_util_1.resultError)("User not found.", info, status));
        }
        return res.json((0, jsonresponse_util_1.resultOk)({
            bearer: (0, jsonwebtoken_1.sign)({ username: account.username }, process.env.JWT_SECRET, { expiresIn: "7d" }),
            info,
            status,
        }));
    })(req, res, next);
});
app.get("/test/authorized/admin", function (req, res, next) {
    return passport_1.default.authenticate("jwt", function (err, account, info, status) {
        if (err) {
            return next(err);
        }
        const role = account.userType;
        if (!account || role !== "admin") {
            return res
                .status(401)
                .json((0, jsonresponse_util_1.resultError)(`No admin access. Role requesting is ${role}`, info, status));
        }
        return res.json((0, jsonresponse_util_1.resultOk)(info, "Admin authorized.", status));
    })(req, res, next);
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
(0, mongoose_1.connect)(process.env.DB_CONNECT_STRING).then(() => app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
}));
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW5kZXguanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi9zcmMvaW5kZXgudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7QUFBQSxlQUFlO0FBQ2Ysc0RBQThEO0FBQzlELHVDQUFrRDtBQUNsRCxtQ0FBZ0M7QUFFaEMsd0RBQWdDO0FBQ2hDLG1EQUEyRDtBQUMzRCwrQ0FBbUU7QUFDbkUsK0NBQW9DO0FBRXBDLGlFQUFrRTtBQUNsRSx5REFBc0U7QUFFdEUsTUFBTSxFQUFFLEtBQUssRUFBRSxHQUFHLElBQUEsZUFBTSxFQUFDLEVBQUUsSUFBSSxFQUFFLGVBQWUsRUFBRSxDQUFDLENBQUM7QUFDcEQsSUFBSSxLQUFLLEVBQUUsQ0FBQztJQUNWLE9BQU8sQ0FBQyxLQUFLLENBQUMsS0FBSyxDQUFDLENBQUM7QUFDdkIsQ0FBQztBQUVELE1BQU0sR0FBRyxHQUFZLElBQUEsaUJBQU8sR0FBRSxDQUFDO0FBQy9CLE1BQU0sSUFBSSxHQUFHLE9BQU8sQ0FBQyxHQUFHLENBQUMsSUFBSSxJQUFJLElBQUksQ0FBQztBQUV0QyxHQUFHLENBQUMsR0FBRyxDQUFDLGtCQUFRLENBQUMsVUFBVSxFQUFFLENBQUMsQ0FBQztBQUMvQixHQUFHLENBQUMsR0FBRyxDQUFDLGlCQUFPLENBQUMsSUFBSSxFQUFFLENBQUMsQ0FBQztBQUV4QixHQUFHO0tBQ0EsS0FBSyxDQUFDLEdBQUcsQ0FBQztLQUNWLEdBQUcsQ0FBQyxDQUFDLEdBQVksRUFBRSxHQUFhLEVBQUUsRUFBRTtJQUNuQyxHQUFHLENBQUMsSUFBSSxDQUFDLGlDQUFpQyxDQUFDLENBQUM7QUFDOUMsQ0FBQyxDQUFDO0tBQ0QsSUFBSSxDQUFDLENBQUMsR0FBWSxFQUFFLEdBQWEsRUFBRSxFQUFFO0lBQ3BDLEdBQUcsQ0FBQyxJQUFJLENBQUMsa0NBQWtDLENBQUMsQ0FBQztBQUMvQyxDQUFDLENBQUMsQ0FBQztBQVVMLE1BQU0sYUFBYSxHQUFHLElBQUksaUJBQU0sQ0FBVztJ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