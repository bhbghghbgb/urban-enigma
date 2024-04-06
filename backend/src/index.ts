// src/index.js
import express, { Express, Request, Response } from "express";
import { config } from "dotenv";
import "passport";
import passport from "passport";
import { Strategy as LocalStrategy } from "passport-local";
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
    function (username, password, done) {
      try {
        Account.findOne(
          { username: username },
          function (err: any, account: IAccount) {
            if (err) {
              return done(err);
            }
            if (!account) {
              return done(null, false);
            }
            if (!comparePassword(password, account.password)) {
              return done(null, false);
            }
            return done(null, account);
          }
        );
      } catch (err) {
        return done(err)
      }
    }
  )
);
app.post("/login", function (req: Request, res: Response, next) {
  passport.authenticate(
    "local",
    function (err: any, user: any, info: object, status: number) {
      if (err) {
        return next(err);
      }
      if (!user) {
        return res.status(401).json(resultError("User not found?"));
      }
      resultOk({
        bearer: sign(
          { username: user.username },
          process.env.JWT_SECRET as string,
          { expiresIn: "7d" }
        ),
      });
    }
  );
});

app.listen(port, () => {
  console.log(`[server]: Server is running at http://localhost:${port}`);
});
