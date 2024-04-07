"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resultError = exports.resultOk = void 0;
function resultOk(moreData = {}, message, status) {
    return Object.assign(Object.assign({}, moreData), { result: "ok", message, status });
}
exports.resultOk = resultOk;
function resultError(message, moreData = {}, status) {
    return Object.assign(Object.assign({}, moreData), { result: "error", message, status });
}
exports.resultError = resultError;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoianNvbnJlc3BvbnNlLnV0aWwuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi9zcmMvdXRpbHMvanNvbnJlc3BvbnNlLnV0aWwudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7O0FBQUEsU0FBZ0IsUUFBUSxDQUFDLFdBQW1CLEVBQUUsRUFBRSxPQUFnQixFQUFFLE1BQWU7SUFDL0UsdUNBQVcsUUFBUSxLQUFFLE1BQU0sRUFBRSxJQUFJLEVBQUUsT0FBTyxFQUFFLE1BQU0sSUFBQztBQUNyRCxDQUFDO0FBRkQsNEJBRUM7QUFDRCxTQUFnQixXQUFXLENBQUMsT0FBZSxFQUFFLFdBQW1CLEVBQUUsRUFBRSxNQUFlO0lBQ2pGLHVDQUFZLFFBQVEsS0FBRSxNQUFNLEVBQUUsT0FBTyxFQUFFLE9BQU8sRUFBRSxNQUFNLElBQUc7QUFDM0QsQ0FBQztBQUZELGtDQUVDIn0=