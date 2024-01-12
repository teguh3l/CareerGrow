import express from "express";
import {addUser} from "../controllers/Users.js";
import {userLogin} from "../controllers/Auth.js";

const router = express.Router();

router.post('/users', addUser);
router.get('/login', userLogin);

export default router;