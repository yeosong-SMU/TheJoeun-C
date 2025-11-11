import {Router} from "express";
import {list, form, insert, download, detail, reply_insert, update, delete_one} from '../BoardController.js';

const router = Router();
router.get("/", list);
router.get('/page/:page', list);
router.get("/form", form);
router.post("/insert", insert);
router.get("/download/:idx", download);
router.get("/detail/:idx", detail);
router.post("/reply_insert", reply_insert);
router.post("/update/:idx", update);
router.get("/delete/:idx", delete_one);

export default router;