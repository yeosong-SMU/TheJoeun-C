const express = require('express');
const router = express.Router();
const controller = require('../controller/ReservationController');

router.get('/', controller.list);
router.get('/new', controller.newForm);
router.post('/', controller.create);
router.get('/edit/:id', controller.editForm);
router.post('/update/:id', controller.update);
router.get('/delete/:id', controller.delete);

module.exports = router;