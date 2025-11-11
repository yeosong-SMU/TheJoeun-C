import express from "express";
import session from 'express-session';
import bodyParser from 'body-parser';
import path from "path";

import memberRouter from "./routes/index.js";

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(
  session({
    secret: 'secret-key',
    resave: false,
    saveUninitialized: true,
  })
);

import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));  //views 디렉토리

app.use('/member', memberRouter);

app.get('/', (req, res) => {
  res.redirect('/member/login');
});

export default app;