-- crea esquema o usa public
-- crea la base de datos si fuera necesario (POSTGRES_DB ya la crea, pero lo dejamos por si acaso)
-- NOTE: si usas POSTGRES_DB en .env no es estrictamente necesario crearla aquí.

\connect productosdb;

-- tabla productos
CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,
    titulo_productos VARCHAR(255) NOT NULL,
    descripcion TEXT,
    cantidad INTEGER NOT NULL DEFAULT 0,
    categoria VARCHAR(100),
    precio NUMERIC(10,2) NOT NULL DEFAULT 0
);
