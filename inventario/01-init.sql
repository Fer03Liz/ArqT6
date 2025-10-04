\connect inventariodb;

-- tabla inventario
CREATE TABLE IF NOT EXISTS inventario (
    id SERIAL PRIMARY KEY,
    titulo_productos VARCHAR(255) NOT NULL,
    descripcion TEXT,
    cantidad INTEGER NOT NULL DEFAULT 0,
    categoria VARCHAR(100),
    precio NUMERIC(10,2) NOT NULL DEFAULT 0
);