export function resultOk(moreData: object = {}) {
  return { ...moreData, result: "ok" };
}
export function resultError(message: string, moreData: object = {}) {
  return { ...moreData, result: "error", message: message };
}
