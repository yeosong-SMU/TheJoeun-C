import { pool } from "./db.js";

export const list = async (req, res) => {
    const [rows] = await pool.query("select idx, title , description, priority, date_format(reg_date, '%Y-%m-%d %H:%i:%s') reg_date, date_format(target_date, '%Y-%m-%d') target_date, done from todo order by priority, reg_date desc");
    res.render("index", { items: rows });
};

export const add = async (req, res) => {
    const { title, description, priority, target_date } = req.body;
    await pool.query('insert into todo (title, description, priority, target_date) values (?,?,?,?)', [title, description, priority, target_date]);
    res.redirect('/');
};

export const done = async (req, res) => {
    await pool.query("update todo set done = 1 where idx = ?", [req.params.idx]);
    res.redirect("/");
};

export const detail = async (req, res) => {
    const idx = req.params.idx;
    const [results] = await pool.query("select idx, title, description, priority, date_format(target_date, '%Y-%m-%d') target_date, done from todo where idx = ?", [idx]);
    
    if(results.length > 0){
        res.render('detail', { todo: results[0] });
    } else {
        res.send('할 일을 찾을 수 없습니다.');
    }
};

export const update = async (req, res) => {
    const idx = req.params.idx;
    const {title, description, priority, target_date, done} = req.body;
    await pool.query("update todo set title=?, description=?, priority=?, target_date=?, done=? where idx = ?", [title, description, priority, target_date, done, idx]);
    res.redirect("/");
};

export const delete_one = async (req, res) => {
    await pool.query('delete from todo where idx = ?', [req.params.idx]);
    res.redirect("/");
};