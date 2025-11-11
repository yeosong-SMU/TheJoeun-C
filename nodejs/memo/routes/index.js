import { Router } from "express";
import {
  list,
  insert,
  detail,
  update,
  delete_one,
} from "../MemoController.js";
const router = Router();

router.get("/", list);
router.post("/insert", insert);
router.get("/detail/:idx", detail);
router.post("/update/:idx", update);
router.get("/delete/:idx", delete_one);

export default router;