package com.taller3arqui.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller3arqui.Servicio.IService;
import com.taller3arqui.productoRepository;
import com.taller3arqui.producto;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
    
    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
    
    @Override
    public Producto updateProducto(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        }
        return null;
    }

   @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }   

}