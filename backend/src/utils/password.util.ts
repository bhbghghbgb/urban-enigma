import { compare, hash } from "bcrypt";
export async function comparePassword(literal: string, hashed: string) {
  return compare(literal, hashed);
}
export async function hashPassword(literal: string) {
  return hash(literal, 10)
}