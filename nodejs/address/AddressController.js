import { pool } from "./db.js";

export const list = async (req, res) => {
    const [rows] = await pool.query("SELECT * FROM address");
    res.render("list", { items: rows });
};

export const form = async (req, res) => {
    res.render("form");
};

export const insert = async (req, res) => {
    const row = req.body;
    await pool.query("insert into address set ?", [row]);
    res.redirect("/");
};

export const detail = async (req, res) => {
    const { idx } = req.params;
    const [result] = await pool.query("select * from address where idx = ?", [idx, ]);
    res.render("edit", { item: result[0] });
};

export const update = async (req, res) => {
    const { idx } = req.params;
    const row = req.body;
    await pool.query("update address set ? where idx = ?", [row, idx]);
    res.redirect("/");
};

export const delete_one = async (req, res) => {
    const { idx } = req.params;
    const result = await pool.query("delete from address where idx = ?", [idx]);
    if(result.affectedRows === 1){
        res.json({ message: "deleted" });
    }
    res.redirect("/");
};