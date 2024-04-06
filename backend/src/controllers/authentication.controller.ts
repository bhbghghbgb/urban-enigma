import { Request, Response } from "express";
import { authenticate } from "passport";
export async function localLoginCustomer(req:Request, res: Response) {
    try {
        authenticate('local')
    } catch (err) {
        return res.status(404).send(err)
    }
}