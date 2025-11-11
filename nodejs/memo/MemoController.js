import { pool } from "./db.js";

export const list = async (req, res) => {
    const [rows] = await pool.query("select idx, writer, memo, date_format(post_date, '%Y-%m-%d %H:%i:%s') post_date from memo");
    res.render("list", { items: rows });
};

export const insert = async (req, res) => {
    const row = req.body;
    await pool.query("insert into memo (writer, memo, post_date) values (?,?,now())", [row.writer, row.memo]);
    res.redirect("/");
};

export const detail = async (req, res) => {
    const { idx } = req.params;
    const [result] = await pool.query("select * from memo where idx = ?", [idx,]);
    res.render("edit", { item: result[0] });
};

export const update = async (req, res) => {
    const { idx } = req.params;
    const row = req.body;
    await pool.query("update memo set ? where idx = ?", [row, idx]);
    res.redirect("/");
};

export const delete_one = async (req, res) => {
    const { idx } = req.params;
    const result = await pool.query("delete from memo where idx = ?", [idx]);
    if(result.affectedRows === 1){
        res.json({ message: "deleted" });
    }
    res.redirect("/");
};