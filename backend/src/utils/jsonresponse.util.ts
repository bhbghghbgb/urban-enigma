export function resultOk(moreData: object = {}, message?: string, status?: number) {
  return {...moreData, result: "ok", message2: message, status}
}
export function resultError(message: string, moreData: object = {}, status?: number) {
  return { ...moreData, result: "error", message2: message, status };
}
