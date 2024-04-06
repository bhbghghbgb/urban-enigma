"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// src/index.js
const express_1 = __importDefault(require("express"));
const dotenv_1 = require("dotenv");
const { error } = (0, dotenv_1.config)({ path: "./.env.shared" });
if (error) {
    console.error(error);
}
const app = (0, express_1.default)();
const port = process.env.PORT || 3000;
app
    .route("/")
    .get((req, res) => {
    res.send("Express + TypeScript Server GET");
})
    .post((req, res) => {
    res.send("Express + TypeScript Server POST");
});
app.listen(port, () => {
    console.log(`[server]: Server is running at http://localhost:${port}`);
});
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaW5kZXguanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi9zcmMvaW5kZXgudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7QUFBQSxlQUFlO0FBQ2Ysc0RBQThEO0FBQzlELG1DQUFnQztBQUVoQyxNQUFNLEVBQUUsS0FBSyxFQUFFLEdBQUcsSUFBQSxlQUFNLEVBQUMsRUFBRSxJQUFJLEVBQUUsZUFBZSxFQUFFLENBQUMsQ0FBQztBQUNwRCxJQUFJLEtBQUssRUFBRSxDQUFDO0lBQ1YsT0FBTyxDQUFDLEtBQUssQ0FBQyxLQUFLLENBQUMsQ0FBQztBQUN2QixDQUFDO0FBRUQsTUFBTSxHQUFHLEdBQVksSUFBQSxpQkFBTyxHQUFFLENBQUM7QUFDL0IsTUFBTSxJQUFJLEdBQUcsT0FBTyxDQUFDLEdBQUcsQ0FBQyxJQUFJLElBQUksSUFBSSxDQUFDO0FBRXRDLEdBQUc7S0FDQSxLQUFLLENBQUMsR0FBRyxDQUFDO0tBQ1YsR0FBRyxDQUFDLENBQUMsR0FBWSxFQUFFLEdBQWEsRUFBRSxFQUFFO0lBQ25DLEdBQUcsQ0FBQyxJQUFJLENBQUMsaUNBQWlDLENBQUMsQ0FBQztBQUM5QyxDQUFDLENBQUM7S0FDRCxJQUFJLENBQUMsQ0FBQyxHQUFZLEVBQUUsR0FBYSxFQUFFLEVBQUU7SUFDcEMsR0FBRyxDQUFDLElBQUksQ0FBQyxrQ0FBa0MsQ0FBQyxDQUFDO0FBQy9DLENBQUMsQ0FBQyxDQUFDO0FBRUwsR0FBRyxDQUFDLE1BQU0sQ0FBQyxJQUFJLEVBQUUsR0FBRyxFQUFFO0lBQ3BCLE9BQU8sQ0FBQyxHQUFHLENBQUMsbURBQW1ELElBQUksRUFBRSxDQUFDLENBQUM7QUFDekUsQ0FBQyxDQUFDLENBQUMifQ==