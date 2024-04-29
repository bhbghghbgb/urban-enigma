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
exports.localLoginCustomer = void 0;
const passport_1 = require("passport");
function localLoginCustomer(req, res) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            (0, passport_1.authenticate)('local');
        }
        catch (err) {
            return res.status(404).send(err);
        }
    });
}
exports.localLoginCustomer = localLoginCustomer;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYXV0aGVudGljYXRpb24uY29udHJvbGxlci5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uL3NyYy9jb250cm9sbGVycy9hdXRoZW50aWNhdGlvbi5jb250cm9sbGVyLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7OztBQUNBLHVDQUF3QztBQUN4QyxTQUFzQixrQkFBa0IsQ0FBQyxHQUFXLEVBQUUsR0FBYTs7UUFDL0QsSUFBSSxDQUFDO1lBQ0QsSUFBQSx1QkFBWSxFQUFDLE9BQU8sQ0FBQyxDQUFBO1FBQ3pCLENBQUM7UUFBQyxPQUFPLEdBQUcsRUFBRSxDQUFDO1lBQ1gsT0FBTyxHQUFHLENBQUMsTUFBTSxDQUFDLEdBQUcsQ0FBQyxDQUFDLElBQUksQ0FBQyxHQUFHLENBQUMsQ0FBQTtRQUNwQyxDQUFDO0lBQ0wsQ0FBQztDQUFBO0FBTkQsZ0RBTUMifQ==