const jwt = require('jsonwebtoken');

function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization']
    if (authHeader && authHeader.split(' ')[0] !== "Bearer") return res.status(401).send({error: "true", message:"Bearer token salah"})
    const token = authHeader && authHeader.split(' ')[1];
    if (token == null) return res.status(401).send({error: "true", message:"Bearer token salah/tidak ditemukan"});

    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if (err) return res.status(403).send({error: "true", message:"Bearer token expired"});
        req.user = user;
        next();
    })
} 

module.exports = { authenticateToken };