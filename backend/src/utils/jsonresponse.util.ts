export function resultOk(moreData: object = {}, message?: string, status?: number) {
  const result: { result: string; message?: string } = {
    ...moreData,
    result: "ok",
  };
  if (message) {
    result.message = message;
  }
  return result;
}
export function resultError(message: string, moreData: object = {}, status?: number) {
  return { ...moreData, result: "error", message: message };
}
