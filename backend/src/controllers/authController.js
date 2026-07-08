const UsuarioModel = require('../models/usuarioModel');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const generarToken = (usuario) => {
    return jwt.sign(
        { id: usuario.id, correo: usuario.correo },
        process.env.JWT_SECRET,
        { expiresIn: '24h' }
    );
};

const registro = async (req, res) => {
    const { nombre, correo, password } = req.body;

    if (!nombre || !correo || !password) {
        return res.status(400).json({ mensaje: 'Todos los campos son obligatorios' });
    }

    try {
        const existe = await UsuarioModel.buscarPorCorreo(correo);
        if (existe) {
            return res.status(400).json({ mensaje: 'El correo ya esta registrado' });
        }

        const salt = await bcrypt.genSalt(10);
        const passwordHash = await bcrypt.hash(password, salt);

        const nuevo = await UsuarioModel.crear(nombre, correo, passwordHash);
        const token = generarToken(nuevo);

        return res.status(201).json({
            token,
            usuario: { id: nuevo.id, nombre: nuevo.nombre, correo: nuevo.correo }
        });
    } catch (error) {
        console.error('Error en registro:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

const login = async (req, res) => {
    const { correo, password } = req.body;

    if (!correo || !password) {
        return res.status(400).json({ mensaje: 'Todos los campos son obligatorios' });
    }

    try {
        const usuario = await UsuarioModel.buscarPorCorreo(correo);
        if (!usuario) {
            return res.status(401).json({ mensaje: 'Credenciales incorrectas' });
        }

        const valido = await bcrypt.compare(password, usuario.password_hash);
        if (!valido) {
            return res.status(401).json({ mensaje: 'Credenciales incorrectas' });
        }

        const token = generarToken(usuario);
        return res.status(200).json({
            token,
            usuario: { id: usuario.id, nombre: usuario.nombre, correo: usuario.correo }
        });
    } catch (error) {
        console.error('Error en login:', error);
        return res.status(500).json({ mensaje: 'Error interno del servidor' });
    }
};

module.exports = { registro, login };