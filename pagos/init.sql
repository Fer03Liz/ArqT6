--base de datos y tabla pagos
\connect pagosdb;

CREATE TABLE IF NOT EXISTS pagos (
    id SERIAL PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto NUMERIC(10,2) NOT NULL,
    moneda VARCHAR(10) DEFAULT 'COP',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
