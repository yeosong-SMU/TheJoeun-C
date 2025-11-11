import { Router } from 'express';

import { list, form, insert, detail, update, delete_one } from '../ProductController.js';

const router = Router();
router.get('/', list);
router.get('/form',form)
router.post('/insert', insert);
router.get('/detail/:product_code', detail);
router.post('/update/:product_code', update);
router.get('/delete/:product_code', delete_one);

export default router;
