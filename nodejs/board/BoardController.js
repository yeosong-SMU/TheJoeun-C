import multer from "multer";
import { pool } from "./db.js";
import fs from "fs/promises";
import path from 'path';

const storage = multer.diskStorage({
    destination(req, file, done) {
        done(null, "c:/upload");
    },
    filename(req, file, done) {
        const uniqueName = Date.now() + '-' + file.originalname;
        done(null, uniqueName);
    },
});
const upload = multer({ storage, limits: { fileSize: 10*1024*1024 }});

export const list = async (req, res) => {
    const page = parseInt(req.params.page) || 1;
    const limit = 10;
    const offset = (page -1) * limit;

    const [[{ total }]] = await pool.query(`select count(*) as total from board`);
    const sql = `select idx, writer, title, hit, filename, filesize, date_format(post_date, '%Y-%m-%d') postdate, down, (select count(*) from board_comment where board_idx = b.idx) cnt from board b order by idx desc limit ? offset ?  `;

    const [rows] = await pool.query(sql, [limit, offset]);

    const totalPages = Math.ceil(total / limit);
    res.render("list", {
        items: rows,
        currentPage: page,
        totalPages: totalPages
    });
};

export const form = async (req, res) => {
    res.render("form");
};

export const insert = (req, res) => {
    const form_data = upload.single('file');

    form_data(req, res, async (err) => {
        if(err) {
            console.error(err);
            return res.status(500).send("Upload failed");
        }

        try {
            const { writer, title, contents } = req.body;
            const filename = req.file ? req.file.filename : '-';
            const filesize = req.file ? req.file.size : 0;

            await pool.query("insert into board (writer, title, contents, filename, filesize, post_date) values (?,?,?,?,?,now())", [writer, title, contents, filename, filesize]);
            res.redirect("/");
        } catch(dbErr) {
            console.error(dbErr);
            res.status(500).send("Database error");
        }
    });
};

export const download = async (req, res) => {
    const { idx } = req.params;
    try {
        const [result] = await pool.query("select filename from board where idx = ?", [idx]);
        if(!result || result.length === 0 || !result[0].filename || result[0].filename === '-') {
            return res.status(404).send("No file associated with this entry.");
        }

        const savedFilename = result[0].filename;
        const file = path.join("c:/upload", savedFilename);

        //파일 존재 여부 확인
        await fs.access(file);

        //원래 파일명 복원
        const originalFilename = savedFilename.includes('-') ? savedFilename.substring(savedFilename.indexOf('-') + 1) : savedFilename;

        //다운로드
        res.download(file, originalFilename, async (err) => {
            if(err) {
                console.error("Download error:", err);
                return res.status(500).send("Failed to download file.");
            }
            await pool.query("update board set down = down + 1 where idx = ?", [idx]);
        });
    } catch (err) {
        console.error("Error during download:", err);
        res.status(500).send("Internal server error");
    }
};

export const detail = async (req, res) => {
    const {idx} = req.params;
    await pool.query("update board set hit = hit + 1 where idx = ?", [idx]);
    const [result] = await pool.query("select idx, writer, title, contents, hit, filename, filesize, date_format(post_date, '%Y-%m-%d %H:%i:%s') post_date, down from board where idx = ?", [idx]);
    const [comment_list] = await pool.query("select idx, board_idx, writer, contents, date_format(post_date, '%Y-%m-%d %H:%i:%s') post_date from board_comment where board_idx = ? order by idx", [idx]);
    res.render("detail", {row: result[0], comment_list: comment_list });
};

export const reply_insert = async(req, res) => {
    const row = req.body;
    await pool.query("insert into board_comment (board_idx, writer, contents, post_date) values (?,?,?,now())", [row.board_idx, row.writer, row.contents]);
    res.redirect("/detail/" + row.board_idx);
};

export const update = (req, res) => {
    const form_data = upload.single('file');
    form_data(req, res, async(err) => {
        if(err) {
            console.error("Upload error:", err);
            return res.status(500).send("File upload failed.");
        }

        const {idx} = req.params;
        const { writer, title, contents, old_filename } = req.body;

        let filename = '-';
        let filesize = 0;

        try {
            if(req.file) {
                filename = req.file.filename;
                filesize = req.file.size;
            } else if (old_filename && old_filename !== '-'){
                filename = old_filename;

                //기존 파일 크기 구하기
                try {
                    const stat = await fs.stat("c:/upload/" + filename);
                    filesize = stat.size;
                } catch (e) {
                    filesize = 0;
                }
            }

            await pool.query("update board set writer=?, title=?, contents=?, filename=?, filesize=? where idx=?", [writer, title, contents, filename, filesize, idx]);

            res.redirect("/");
        } catch (dbErr) {
            console.error("DB error: ", dbErr);
            res.status(500).send("Database update failed.");
        }
    });
};

export const delete_one = async (req, res) => {
    const {idx} = req.params;
    const result = await pool.query("delete from board where idx = ?", [idx]);
    if(result.affectedRows === 1) {
        return res.json({message: "deleted"});
    } else {
        return res.status(404).send("Post not found");
    }
};