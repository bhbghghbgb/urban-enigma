import { use } from "passport";
import { Strategy as LocalStrategy } from "passport-local";
use(new LocalStrategy(function (username, password, done) {
    
 }))