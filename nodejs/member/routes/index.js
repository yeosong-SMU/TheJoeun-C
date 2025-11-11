import {Router} from 'express';
import { getRegister, postRegister, getLogin, postLogin, logout, list, main, update, remove } from '../controller/MemberController.js';
const router = Router();

router.get('/register', getRegister);
router.post('/register', postRegister);
router.get('/login', getLogin);
router.post('/login', postLogin);
router.get('/logout', logout);
router.get('/list', list);
router.get('/main', main);
router.post('/update', update);
router.post('/remove', remove);

export default router;
