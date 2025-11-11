const mongoose = require('mongoose');

const reservationSchema = new mongoose.Schema({
    name: String,
    date: Date,
    time: String,
    service: String,
});

module.exports = mongoose.model('Reservation', reservationSchema);