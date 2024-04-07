"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resultError = exports.resultOk = void 0;
function resultOk(moreData = {}, message, status) {
    return Object.assign(Object.assign({}, moreData), { result: "ok", message2: message, status });
}
exports.resultOk = resultOk;
function resultError(message, moreData = {}, status) {
    return Object.assign(Object.assign({}, moreData), { result: "error", message2: message, status });
}
exports.resultError = resultError;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoianNvbnJlc3BvbnNlLnV0aWwuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi9zcmMvdXRpbHMvanNvbnJlc3BvbnNlLnV0aWwudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7O0FBQUEsU0FBZ0IsUUFBUSxDQUFDLFdBQW1CLEVBQUUsRUFBRSxPQUFnQixFQUFFLE1BQWU7SUFDL0UsdUNBQVcsUUFBUSxLQUFFLE1BQU0sRUFBRSxJQUFJLEVBQUUsUUFBUSxFQUFFLE9BQU8sRUFBRSxNQUFNLElBQUM7QUFDL0QsQ0FBQztBQUZELDRCQUVDO0FBQ0QsU0FBZ0IsV0FBVyxDQUFDLE9BQWUsRUFBRSxXQUFtQixFQUFFLEVBQUUsTUFBZTtJQUNqRix1Q0FBWSxRQUFRLEtBQUUsTUFBTSxFQUFFLE9BQU8sRUFBRSxRQUFRLEVBQUUsT0FBTyxFQUFFLE1BQU0sSUFBRztBQUNyRSxDQUFDO0FBRkQsa0NBRUMifQ==