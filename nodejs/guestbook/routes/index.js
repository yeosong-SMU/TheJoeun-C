import { Router } from 'express';
import { list, form, insert, passwd_check, detail, update, delete_one } from '../GuestbookController.js';
const router = Router();

router.get('/', list);
router.get('/form', form);
router.post('/insert', insert);
router.post('/passwd_check', passwd_check);
router.get('/detail/:idx', detail);
router.post('/update/:idx', update);
router.get('/delete/:idx', delete_one);

export default router;