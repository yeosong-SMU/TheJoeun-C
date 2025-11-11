const Reservation = require('../model/Reservation');

exports.list = async (req, res) => {
    const reservations = await Reservation.find();
    res.render('list', {title: '예약 목록', reservations});
};

exports.newForm = (req, res) => {
    res.render('form', {title: '새 예약', reservation: {} });
};

exports.create = async (req, res) => {
    const reservation = new Reservation(req.body);
    await reservation.save();
    res.redirect('/');
};

exports.editForm = async (req, res) => {
    const reservation = await Reservation.findById(req.params.id);
    res.render('form', { title: '예약 수정', reservation });
};

exports.update = async (req, res) => {
    await Reservation.findByIdAndUpdate(req.params.id, req.body);
    res.redirect('/');
};

exports.delete = async (req, res) => {
    await Reservation.findByIdAndDelete(req.params.id);
    res.redirect('/');
};