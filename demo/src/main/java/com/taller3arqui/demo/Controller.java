package com.taller3arqui.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller3arqui.Servicio.IService;;

@CrossOrigin(origins = "http://localhost:3000") // habilita acceso desde el frontend
@RestController
@RequestMapping("/api/productos")
public class Controller {

    @Autowired
    private IService servicio;

    //Prueba para verificar conexión
    @GetMapping("/test")
    public String verificarConexion() {
        return "Conexión OK con backend!";
    }

    // Listar todos los productos
    @GetMapping
    public List<Producto> obtenerProductos() {
        return servicio.findAll();
    }
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return servicio.saveProducto(producto);
    }

}
