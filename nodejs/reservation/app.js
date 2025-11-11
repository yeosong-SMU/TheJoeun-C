const express = require('express');
const path = require('path');
const mongoose = require('mongoose');
const reservationRouter = require('./routes/index');

const app = express();

mongoose
.connect('mongodb://user:1234@localhost:27017/reservation?authSource=reservation')
.then(() => console.log('MongoDB connected'))
.catch((err) => console.log('MongoDB connection error:', err));

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use('/', reservationRouter);

module.exports = app;