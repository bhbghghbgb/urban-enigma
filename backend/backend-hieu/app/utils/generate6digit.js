import { randomInt } from "crypto";

exports.generate6digit = () =>
    randomInt(0, 999_999).toString().padStart(6, "0");
