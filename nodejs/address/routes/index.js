import { Router } from "express";
import { list, form, insert, detail, update, 
  delete_one,
} from "../AddressController.js";
const router = Router();

router.get("/", list);
router.get("/form", form);
router.post("/insert", insert);
router.get("/detail/:idx", detail);
router.post("/update/:idx", update);
router.get("/delete/:idx", delete_one);

export default router;