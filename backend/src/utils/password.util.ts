import { compare } from "bcrypt";
export async function comparePassword(literal: string, hashed: string) {
  return compare(literal, hashed);
}
