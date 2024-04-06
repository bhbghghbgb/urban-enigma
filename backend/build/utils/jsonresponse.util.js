"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.resultError = exports.resultOk = void 0;
function resultOk(moreData = {}) {
    return Object.assign(Object.assign({}, moreData), { result: "ok" });
}
exports.resultOk = resultOk;
function resultError(message, moreData = {}) {
    return Object.assign(Object.assign({}, moreData), { result: "error", message: message });
}
exports.resultError = resultError;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoianNvbnJlc3BvbnNlLnV0aWwuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi9zcmMvdXRpbHMvanNvbnJlc3BvbnNlLnV0aWwudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7O0FBQUEsU0FBZ0IsUUFBUSxDQUFDLFdBQW1CLEVBQUU7SUFDNUMsdUNBQVksUUFBUSxLQUFFLE1BQU0sRUFBRSxJQUFJLElBQUc7QUFDdkMsQ0FBQztBQUZELDRCQUVDO0FBQ0QsU0FBZ0IsV0FBVyxDQUFDLE9BQWUsRUFBRSxXQUFtQixFQUFFO0lBQ2hFLHVDQUFZLFFBQVEsS0FBRSxNQUFNLEVBQUUsT0FBTyxFQUFFLE9BQU8sRUFBRSxPQUFPLElBQUc7QUFDNUQsQ0FBQztBQUZELGtDQUVDIn0=