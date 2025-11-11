import { Router } from 'express';
import { list, add, done, detail, update, delete_one } from '../TodoController.js';
const router = Router();

router.get('/', list);
router.post('/add', add);
router.get('/done/:idx', done);
router.get('/detail/:idx', detail);
router.post('/update/:idx', update);
router.get('/delete/:idx', delete_one);

export default router;