const PresupuestoModel = require('../models/presupuestoModel');

const obtener = async (req, res) => {
    const usuarioId = req.usuario.id;
    const mes = req.query.mes;

    if (!mes || !/^\d{4}-\d{2}$/.test(mes)) {
        return res.status(400).json({ mensaje: 'El parametro mes debe tener formato YYYY-MM' });
    }

    try {
        const presupuesto = await PresupuestoModel.obtener(usuarioId, mes);
        if (!presupuesto) {
            return res.status(404).json({ mensaje: 'Sin presupuesto para el mes' });
        }
        return res.status(200).json(presupuesto);
    } catch (error) {
        console.error('Error al obtener presupuesto:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

const guardar = async (req, res) => {
    const usuarioId = req.usuario.id;
    const { mes, limite } = req.body;

    if (!mes || !/^\d{4}-\d{2}$/.test(mes)) {
        return res.status(400).json({ mensaje: 'El campo mes debe tener formato YYYY-MM' });
    }
    if (!limite || Number(limite) <= 0) {
        return res.status(400).json({ mensaje: 'El limite debe ser mayor a cero' });
    }

    try {
        const guardado = await PresupuestoModel.guardar(usuarioId, mes, limite);
        return res.status(200).json(guardado);
    } catch (error) {
        console.error('Error al guardar presupuesto:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

module.exports = { obtener, guardar };