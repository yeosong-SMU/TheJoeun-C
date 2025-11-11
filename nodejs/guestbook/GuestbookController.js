import { pool } from "./db.js";

export const list = async (req, res) => {
    const [rows] = await pool.query("select idx, name, email, passwd, contents, date_format(post_date, '%Y-%m-%d %H:%i:%s') post_date from guestbook order by idx desc");
    const message = req.query.message;
    res.render("list", { items: rows, message: message });
};

export const form = async (req, res) => {
    res.render("form");
};

export const insert = async (req, res) => {
    const row = req.body;
    await pool.query("insert into guestbook (name, email, passwd, contents, post_date) values (?,?,?,?,now())", [row.name, row.email, row.passwd, row.contents]);
    res.redirect("/");
};

export const passwd_check = async (req, res) => {
    const { idx, passwd } = req.body;
    const [result] = await pool.query ("select * from guestbook where idx = ? and passwd=?", [idx, passwd]);

    if(result.length > 0) {
        res.render("edit", { item: result[0] });
    } else {
        res.redirect("/?message=error");
    }
};

export const detail = async (req, res) => {
    const { idx } = req.params;
    const [result] = await pool.query("select * from guestbook where idx = ?", [idx]);
    res.render("edit", {item: result[0]});
};

export const update = async (req, res) => {
    const { idx } = req.params;
    const row = req.body;
    await pool.query("update guestbook set ? where idx = ?", [row, idx]);
    res.redirect("/");
};

export const delete_one = async (req, res) => {
    const { idx } = req.params;
    const result = await pool.query("delete from guestbook where idx = ?", [idx]);
    if(result.affectedRows === 1){
        res.json({ message: "deleted" });
    }
    res.redirect("/");
};