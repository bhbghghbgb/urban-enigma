// src/index.js
import express, { Express, Request, Response } from "express";
import { config } from "dotenv";
import "passport";
import passport from "passport";
import { Strategy as LocalStrategy } from "passport-local";
import { Strategy as JwtStrategy, ExtractJwt } from "passport-jwt";
import { sign } from "jsonwebtoken";
import { resultError, resultOk } from "./utils/jsonresponse.util";
import { Schema, model } from "mongoose";
import { comparePassword } from "./utils/password.util";

const { error } = config({ path: "./.env.shared" });
if (error) {
  console.error(error);
}

const app: Express = express();
const port = process.env.PORT || 3000;

app.use(passport.initialize());
app
  .route("/")
  .get((req: Request, res: Response) => {
    res.send("Express + TypeScript Server GET");
  })
  .post((req: Request, res: Response) => {
    res.send("Express + TypeScript Server POST");
  });
interface IAccountJwtPayload {
  username: string;
}
interface IAccount {
  username: string;
  password: string;
}
const accountSchema = new Schema<IAccount>({
  username: { type: String, required: true },
  password: { type: String, required: true },
});
const Account = model<IAccount>("Account", accountSchema);
passport.use(
  new LocalStrategy(
    {
      usernameField: "username",
      passwordField: "password",
      passReqToCallback: false,
      session: false,
    },
    async function (username, password, done) {
      try {
        const account = await Account.findOne({ username });
        if (!account) {
          return done(null, false, { message: "Username not found." });
        }
        const passwordMatch = await comparePassword(password, account.password);
        if (!passwordMatch) {
          return done(null, false, { message: "Password mismatch." });
        }
        return done(null, account, { message: `Logging in as ${username}.` });
      } catch (err) {
        return done(err, { message: "Server exception." });
      }
    }
  )
);
passport.use(
  new JwtStrategy(
    {
      secretOrKey: process.env.JWT_SECRET as string,
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
    },
    async function (jwtPayload: IAccountJwtPayload, done) {
      try {
        const username = jwtPayload.username;
        const account = await Account.findOne({ username: username });
        if (!account) {
          return done(null, false, { message: "Malformed bearer token." });
        }
        return done(null, account, {
          message: `Bearer authorized ${username}.`,
        });
      } catch (err) {
        return done(err, { message: "Server exception." });
      }
    }
  )
);
app.post("/login", function (req: Request, res: Response, next) {
  passport.authenticate(
    "local",
    function (err: any, account: any, info: object, status: number) {
      if (err) {
        return next(err);
      }
      if (!account) {
        return res
          .status(401)
          .json(resultError("User not found?", info, status));
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
  );
});

app.listen(port, () => {
  console.log(`[server]: Server is running at http://localhost:${port}`);
});
