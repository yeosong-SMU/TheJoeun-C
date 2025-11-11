import multer from "multer";
import { pool } from "./db.js";
import path from "path";
import { fileURLToPath } from "url";
let filename = '';

const upload = multer({
    storage: multer.diskStorage({
        filename(req, file, done) {
            const originalName = Buffer.from(file.originalname, 'latin1').toString('utf8');
            done(null, originalName);
        },
        destination(req, file, done) {
            filename = file.originalname;
            const __dirname = path.dirname(fileURLToPath(import.meta.url));
            done(null, path.join(__dirname, "public/images"));
        },
    }),
    limits: { fileSize: 10 * 1024 * 1024 },
});

export const list = async (req, res) => {
    const [rows] = await pool.query("select * from product order by product_code desc");
    res.render("list", { items: rows });
};

export const form = async(req, res) => {
    res.render("form");
};

export const insert = async(req, res) => {
    const form_data = upload.fields([{ name: 'product_name' }, { name: 'description' }, { name: 'price' }, {name: 'filename' }]);

    form_data (req, res, async function (err) {
        if(err) {
            console.error('파일 업로드 오류: ', err);
            return res.status(500).send('파일 업로드 실패');
        }

        try{
            const row = req.body;
            let filename = '-';
            if(req.files && req.files['filename'] && req.files['filename'][0]) {
                filename = req.files['filename'][0].filename;
            }

            await pool.query("insert into product (product_name, description, price, filename) values (?,?,?,?)", [row.product_name, row.description, row.price, filename]);

            res.redirect("/");
        }catch(dbError) {
            console.error('DB 오류: ', dbError);
            res.status(500).send('DB 저장 실패');
        }
    });
};

export const detail = async(req, res) => {
    const { product_code } = req.params;
    const [result] = await pool.query('select * from product where product_code = ?', [product_code]);
    res.render('edit', { item: result[0] });
};

export const update = (req, res) => {
    const { product_code } = req.params;
    const form_data = upload.fields([{ name: 'filename' }]);

    form_data(req, res, async function (err) {
        if (err) {
            console.error('파일 업로드 오류: ', err);
            return res.status(500).send('업로드 실패');
        }

        try {
            const row = req.body;
            let filename = '-';

            if(req.files && req.files['filename'] && req.files['filename'][0]) {
                filename = req.files['filename'][0].filename;
            } else if(row.old_filename) {
                filename = row.old_filename;
            }

            await pool.query('update product set product_name=?, description=?, price=?, filename=? where product_code=?', [row.product_name, row.description, row.price, filename, product_code]);

            res.redirect('/');
        } catch(dbError) {
            console.error('DB 오류: ', dbError);
            res.status(500).send('DB 오류');
        }
    });
};

export const delete_one = async (req, res) => {
    const { product_code } = req.params;
    const result = await pool.query('delete from product where product_code = ?', [product_code]);
    if(result.affectedRows === 1) {
        res.join({ message : 'deleted'});
    }
    res.redirect('/');
};