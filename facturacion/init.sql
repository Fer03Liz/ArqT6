--BASE DE DATOS FACTURACION Y TABLA FACTURAS
\connect facturaciondb;

CREATE TABLE IF NOT EXISTS facturas (
    id SERIAL PRIMARY KEY,
    descripcion_pedido varchar(100) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total NUMERIC(10,2) NOT NULL,
    estado VARCHAR(50) DEFAULT 'generada'
);
