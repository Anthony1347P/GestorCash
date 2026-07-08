const express = require('express');
const router = express.Router();
const presupuestoController = require('../controllers/presupuestoController');
const verificarToken = require('../middlewares/authMiddleware');

router.use(verificarToken);

router.get('/', presupuestoController.obtener);
router.post('/', presupuestoController.guardar);

module.exports = router;