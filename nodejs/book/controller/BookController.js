const model = require('../model/BookDAO');

exports.list = async (req, res) => {
    const books = await model.getAllBooks();
    res.render('list', { books });
};

exports.form = (req, res) => {
    res.render('form', { book: null });
};

exports.create = async(req, res) => {
    await model.createBook(req.body);
    res.redirect('/');
};

exports.edit = async(req, res) => {
    const book = await model.getBookById(req.params.id);
    res.render('form', { book });
};

exports.update = async (req, res) => {
    await model.updateBook(req.params.id, req.body);
    res.redirect('/');
};

exports.delete = async (req, res) => {
    await model.deleteBook(req.params.id);
    res.redirect('/');
};