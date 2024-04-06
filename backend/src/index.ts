// src/index.js
import express, { Express, Request, Response } from "express";
import { config } from "dotenv";

const { error } = config({ path: "./.env.shared" });
if (error) {
  console.error(error);
}

const app: Express = express();
const port = process.env.PORT || 3000;

app
  .route("/")
  .get((req: Request, res: Response) => {
    res.send("Express + TypeScript Server GET");
  })
  .post((req: Request, res: Response) => {
    res.send("Express + TypeScript Server POST");
  });

app.listen(port, () => {
  console.log(`[server]: Server is running at http://localhost:${port}`);
});
