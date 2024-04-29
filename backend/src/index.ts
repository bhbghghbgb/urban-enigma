// src/index.js
import express, { Express, Request, Response } from "express";
import { Schema, model, connect } from "mongoose";
import { config } from "dotenv";

import passport from "passport";
import { Strategy as LocalStrategy } from "passport-local";
import { Strategy as JwtStrategy, ExtractJwt } from "passport-jwt";
import { sign } from "jsonwebtoken";

import { resultError, resultOk } from "./utils/jsonresponse.util";
import { comparePassword, hashPassword } from "./utils/password.util";

const { error } = config({ path: "./.env.shared" });
if (error) {
  console.error(error);
}

const app: Express = express();
const port = process.env.PORT || 3000;

app.use(passport.initialize());
app.use(express.json());

app
  .route("/")
  .get((req: Request, res: Response) => {
    res.send("Express + TypeScript Server GET");
  })
  .post((req: Request, res: Response) => {
    res.send("Express + TypeScript Server POST");
  });
// bearer token jwt payload containing only account unique and indentifiable
interface IAccountJwtPayload {
  username: string;
}
interface IAccount {
  username: string;
  password: string;
  userType: string;
}
const accountSchema = new Schema<IAccount>({
  username: { type: String, required: true },
  password: { type: String, required: true },
  userType: { type: String, required: true },
});
const Account = model<IAccount>("Account", accountSchema);
// passport strategy for logging in with username/password pair when not logged in yet
// subsequent authorized actions should use bearer token
passport.use(
  new LocalStrategy(
    {
      usernameField: "username",
      passwordField: "password",
      passReqToCallback: true,
      session: false,
    },
    async function (req: Request, username, password, done) {
      console.log(req);
      try {
        const account = await Account.findOne({ username });
        if (!account) {
          return done(null, false, { message: "Username not found." });
        }
        const passwordMatch = await comparePassword(password, account.password);
        if (!passwordMatch) {
          return done(null, false, { message: "Password mismatch." });
        }
        return done(null, account, {
          message: `Logging in as ${username}, role ${account.userType}`,
        });
      } catch (err) {
        return done(err, { message: (err as Error).message });
      }
    }
  )
);
// passport strategy for authorized actions with a bearer token
passport.use(
  new JwtStrategy(
    {
      secretOrKey: process.env.JWT_SECRET as string,
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      passReqToCallback: true,
    },
    async function (req: Request, jwtPayload: IAccountJwtPayload, done) {
      console.log(req);
      try {
        const username = jwtPayload.username;
        const account = await Account.findOne({ username: username });
        if (!account) {
          return done(null, false, { message: "Malformed bearer token." });
        }
        return done(null, account, {
          message: `Bearer authorized ${username}. Role ${account.userType}`,
        });
      } catch (err) {
        return done(err, { message: (err as Error).message });
      }
    }
  )
);
app.post("/login", function (req: Request, res: Response, next) {
  return passport.authenticate(
    "local",
    function (err: any, account: IAccount, info: object, status: number) {
      if (err) {
        return next(err);
      }
      if (!account) {
        return res
          .status(401)
          .json(resultError("User not found.", info, status));
      }
      return res.json(
        resultOk({
          bearer: sign(
            { username: account.username } as IAccountJwtPayload,
            process.env.JWT_SECRET as string,
            { expiresIn: "7d" }
          ),
          info,
          status,
        })
      );
    }
  )(req, res, next);
});
app.get("/test/authorized/admin", function (req: Request, res: Response, next) {
  return passport.authenticate(
    "jwt",
    function (err: any, account: IAccount, info: object, status: number) {
      if (err) {
        return next(err);
      }
      const role = account.userType;
      if (!account || role !== "admin") {
        return res
          .status(401)
          .json(
            resultError(
              `No admin access. Role requesting is ${role}`,
              info,
              status
            )
          );
      }
      return res.json(resultOk(info, "Admin authorized.", status));
    }
  )(req, res, next);
});
app.post("/signup", async function (req: Request, res: Response) {
  try {
    const username = req.body.username;
    const hashedPassword = await hashPassword(req.body.password);
    if ((await Account.countDocuments({ username })) > 0) {
      return res
        .status(409)
        .json(resultError(`Username exists. (${username})`));
    }
    const userType = req.body.userType;
    await Account.create({
      username,
      password: hashedPassword,
      userType: userType,
    });
    return res.json(
      resultOk(
        undefined,
        `Account created. Username ${username}. Role ${userType}`
      )
    );
  } catch (err) {
    return res.status(500).json(resultError((err as Error).message));
  }
});

connect(process.env.DB_CONNECT_STRING as string).then(() =>
  app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
  })
);
