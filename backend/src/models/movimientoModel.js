const pool = require('../config/db');

const MovimientoModel = {

    listarPorMes: async (usuarioId, mes) => {
        const query = `
            SELECT
                id,
                tipo,
                monto::float AS monto,
                categoria,
                to_char(fecha, 'YYYY-MM-DD') AS fecha,
                COALESCE(descripcion, '') AS descripcion
            FROM movimientos
            WHERE usuario_id = $1
              AND to_char(fecha, 'YYYY-MM') = $2
            ORDER BY fecha DESC, id DESC
        `;
        const { rows } = await pool.query(query, [usuarioId, mes]);
        return rows;
    },

    crear: async ({ usuarioId, tipo, monto, categoria, fecha, descripcion }) => {
        const query = `
            INSERT INTO movimientos (usuario_id, tipo, monto, categoria, fecha, descripcion)
            VALUES ($1, $2, $3, $4, $5, $6)
            RETURNING
                id,
                tipo,
                monto::float AS monto,
                categoria,
                to_char(fecha, 'YYYY-MM-DD') AS fecha,
                COALESCE(descripcion, '') AS descripcion
        `;
        const { rows } = await pool.query(query, [
            usuarioId, tipo, monto, categoria, fecha, descripcion || ''
        ]);
        return rows[0];
    },

    eliminar: async (id, usuarioId) => {
        const query = `
            DELETE FROM movimientos
            WHERE id = $1 AND usuario_id = $2
            RETURNING id
        `;
        const { rows } = await pool.query(query, [id, usuarioId]);
        return rows[0];
    }
};

module.exports = MovimientoModel;