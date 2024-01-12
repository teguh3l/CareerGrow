import express from "express";
import cors from "cors";
import dotenv from "dotenv";
// import session from "express-session";
// import db from './config/Database.js';
// import SequelizeStore from "connect-session-sequelize"
import route from './routes/route.js';
dotenv.config();

const app = express();

// const sessionStore = SequelizeStore(session.Store);

// const store = new sessionStore({
//     db: db
// })

// app.use(session({
//     secret: process.env.SESS_SECRET,
//     resave: false,
//     saveUninitialized: true,
//     store: store,
//     cookie: {
//         secure: 'auto'
//     }
// }));

// (async()=>{
//     await db.sync();
// })();  

app.use(cors());
app.use(express.json())
app.use(route);

// store.sync();

app.get("/", (req, res) => {
    console.log("Response success")
    res.send("Response Success!")
})

const PORT = process.env.PORT;
app.listen(PORT, () => {
    console.log("Server is up and listening on " + PORT)
})