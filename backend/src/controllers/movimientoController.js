const MovimientoModel = require('../models/movimientoModel');

const CATEGORIAS_VALIDAS = [
    'Transporte', 'Gasolina', 'Alimentacion',
    'Educacion', 'Entretenimiento', 'Otros'
];

const listar = async (req, res) => {
    const usuarioId = req.usuario.id;
    const mes = req.query.mes;

    if (!mes || !/^\d{4}-\d{2}$/.test(mes)) {
        return res.status(400).json({ mensaje: 'El parametro mes debe tener formato YYYY-MM' });
    }

    try {
        const lista = await MovimientoModel.listarPorMes(usuarioId, mes);
        return res.status(200).json(lista);
    } catch (error) {
        console.error('Error al listar movimientos:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

const crear = async (req, res) => {
    const usuarioId = req.usuario.id;
    const { tipo, monto, categoria, fecha, descripcion } = req.body;

    if (!tipo || !monto || !categoria || !fecha) {
        return res.status(400).json({ mensaje: 'Faltan campos obligatorios' });
    }
    if (tipo !== 'ingreso' && tipo !== 'gasto') {
        return res.status(400).json({ mensaje: 'El tipo debe ser ingreso o gasto' });
    }
    if (!CATEGORIAS_VALIDAS.includes(categoria)) {
        return res.status(400).json({ mensaje: 'Categoria no valida' });
    }
    if (Number(monto) <= 0) {
        return res.status(400).json({ mensaje: 'El monto debe ser mayor a cero' });
    }

    try {
        const nuevo = await MovimientoModel.crear({
            usuarioId, tipo, monto, categoria, fecha, descripcion
        });
        return res.status(201).json(nuevo);
    } catch (error) {
        console.error('Error al crear movimiento:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

const eliminar = async (req, res) => {
    const usuarioId = req.usuario.id;
    const id = parseInt(req.params.id, 10);

    if (isNaN(id)) {
        return res.status(400).json({ mensaje: 'Id no valido' });
    }

    try {
        const eliminado = await MovimientoModel.eliminar(id, usuarioId);
        if (!eliminado) {
            return res.status(404).json({ mensaje: 'Movimiento no encontrado' });
        }
        return res.status(200).json({ eliminado: true });
    } catch (error) {
        console.error('Error al eliminar movimiento:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

module.exports = { listar, crear, eliminar };