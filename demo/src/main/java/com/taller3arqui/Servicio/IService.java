package com.taller3arqui.Servicio;

import com.taller3arqui.demo.Producto;
import java.util.List;
import java.util.Optional;

public interface IService {
    List<Producto> getAllProductos();
    Optional<Producto> getProductoById(Long id);
    Producto saveProducto(Producto producto);
    void deleteProducto(Long id);
    Producto updateProducto(Long id, Producto producto);
    List<Producto> findAll();
}