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
Object.defineProperty(exports, "__esModule", { value: true });
exports.hashPassword = exports.comparePassword = void 0;
const bcrypt_1 = require("bcrypt");
function comparePassword(literal, hashed) {
    return __awaiter(this, void 0, void 0, function* () {
        return (0, bcrypt_1.compare)(literal, hashed);
    });
}
exports.comparePassword = comparePassword;
function hashPassword(literal) {
    return __awaiter(this, void 0, void 0, function* () {
        return (0, bcrypt_1.hash)(literal, 10);
    });
}
exports.hashPassword = hashPassword;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicGFzc3dvcmQudXRpbC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uL3NyYy91dGlscy9wYXNzd29yZC51dGlsLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7OztBQUFBLG1DQUF1QztBQUN2QyxTQUFzQixlQUFlLENBQUMsT0FBZSxFQUFFLE1BQWM7O1FBQ25FLE9BQU8sSUFBQSxnQkFBTyxFQUFDLE9BQU8sRUFBRSxNQUFNLENBQUMsQ0FBQztJQUNsQyxDQUFDO0NBQUE7QUFGRCwwQ0FFQztBQUNELFNBQXNCLFlBQVksQ0FBQyxPQUFlOztRQUNoRCxPQUFPLElBQUEsYUFBSSxFQUFDLE9BQU8sRUFBRSxFQUFFLENBQUMsQ0FBQTtJQUMxQixDQUFDO0NBQUE7QUFGRCxvQ0FFQyJ9