const { getConnection } = require('../config/db');

async function getAllBooks() {
    const conn = await getConnection();
    const result = await conn.execute('select * from books order by id');
    await conn.close();
    return result.rows;
}

async function getBookById(id) {
    const conn = await getConnection();
    const result = await conn.execute('select * from books where id = :id', [id]);
    await conn.close();
    return result.rows[0];
}

async function createBook(book) {
    const conn = await getConnection();
    await conn.execute('insert into books (title, author, price, published_date) values (:1, :2, :3, :4)', [book.title, book.author, book.price, book.published_date]);
    await conn.commit();
    await conn.close();
}

async function updateBook(id, book) {
    const conn = await getConnection();
    await conn.execute('update books set title=:1, author=:2, price=:3, published_date=:4 where id=:5', [book.title, book.author, book.price, book.published_date, id]);
    await conn.commit();
    await conn.close();
}

async function deleteBook(id) {
    const conn = await getConnection();
    await conn.execute('delete from books where id = :id', [id]);
    await conn.commit();
    await conn.close();
}

module.exports = {
    getAllBooks,
    getBookById,
    createBook,
    updateBook,
    deleteBook,
};