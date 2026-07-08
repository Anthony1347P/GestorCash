const pool = require('../config/db');

const UsuarioModel = {

    buscarPorCorreo: async (correo) => {
        const query = 'SELECT id, nombre, correo, password_hash FROM usuarios WHERE correo = $1';
        const { rows } = await pool.query(query, [correo]);
        return rows[0];
    },

    crear: async (nombre, correo, passwordHash) => {
        const query = `
            INSERT INTO usuarios (nombre, correo, password_hash)
            VALUES ($1, $2, $3)
            RETURNING id, nombre, correo
        `;
        const { rows } = await pool.query(query, [nombre, correo, passwordHash]);
        return rows[0];
    }
};