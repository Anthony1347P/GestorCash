const express = require('express');
const dotenv = require('dotenv');

dotenv.config();

const app = express();
require('./config/db');

app.use(express.json());

const authRoutes = require('./routes/authRoutes');
const movimientoRoutes = require('./routes/movimientoRoutes');
const presupuestoRoutes = require('./routes/presupuestoRoutes');

app.use('/auth', authRoutes);
app.use('/movimientos', movimientoRoutes);
app.use('/presupuesto', presupuestoRoutes);

app.get('/health', (req, res) => {
    res.json({ status: 'success', message: 'Servidor de GestorCash operando correctamente' });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});

module.exports = app;