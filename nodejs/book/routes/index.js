const express = require('express');
const router = express.Router();
const controller = require('../controller/BookController');

router.get('/', controller.list);
router.get('/new', controller.form);
router.post('/', controller.create);
router.get('/edit/:id', controller.edit);
router.post('/:id', controller.update);
router.get('/delete/:id', controller.delete);

module.exports = router;
