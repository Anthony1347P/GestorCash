const express = require('express');
const router = express.Router();
const movimientoController = require('../controllers/movimientoController');
const verificarToken = require('../middlewares/authMiddleware');

router.use(verificarToken);

router.get('/', movimientoController.listar);
router.post('/', movimientoController.crear);
router.delete('/:id', movimientoController.eliminar);

module.exports = router;