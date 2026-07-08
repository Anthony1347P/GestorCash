const pool = require('../config/db');

const PresupuestoModel = {

    obtener: async (usuarioId, mes) => {
        const query = `
            SELECT id, mes, limite::float AS limite
            FROM presupuestos
            WHERE usuario_id = $1 AND mes = $2
        `;
        const { rows } = await pool.query(query, [usuarioId, mes]);
        return rows[0];
    },

    // upsert: si ya existe presupuesto para ese mes lo actualiza
    guardar: async (usuarioId, mes, limite) => {
        const query = `
            INSERT INTO presupuestos (usuario_id, mes, limite)
            VALUES ($1, $2, $3)
            ON CONFLICT (usuario_id, mes)
            DO UPDATE SET limite = EXCLUDED.limite
            RETURNING id, mes, limite::float AS limite
        `;
        const { rows } = await pool.query(query, [usuarioId, mes, limite]);
        return rows[0];
    }
};

module.exports = PresupuestoModel;