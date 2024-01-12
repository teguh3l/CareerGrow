import Users from '../models/userModel.js';
import argon2 from "argon2";
import jwt from "jsonwebtoken";

const token = jwt.sign({
    data: 'foobar'
  }, 'secret');

export const userLogin = async (req, res) => {
    console.log(token);
    const user = await Users.findOne({
        where:{
            email: req.body.email
        }
    });
    if(!user) return res.status(404).json({error:"true", message: "Email/Password Salah"});
    const match = await argon2.verify(user.password, req.body.password);
    if(!match){
        return res.status(404).json({error:"true", message: "Email/Password Salah"});
    }
    const nama = user.nama;
    const email = user.email;
    res.status(200).json({error:"false",message: "Login Success", data:{email: email, nama: nama, token: token}});
}