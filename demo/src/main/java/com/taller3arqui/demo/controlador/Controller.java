package com.taller3arqui.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.*;

import com.taller3arqui.Servicio.IService;
import com.taller3arqui.demo.servicios.PagoService;
import com.taller3arqui.demo.dto.PagoRequest;
import com.taller3arqui.demo.entidades.Producto;
import com.taller3arqui.demo.servicios.ProductoService;
import com.taller3arqui.demo.servicios.FacturaService;
import com.taller3arqui.demo.servicios.PagoService;
import com.taller3arqui.demo.entidades.PagoEntity;
import com.taller3arqui.demo.entidades.FacturaEntity;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private ProductoService servicio;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private PagoService pagoService;

    // ----------------- ENDPOINTS DE PRODUCTOS -----------------

    // Prueba para verificar conexión
    @GetMapping("/productos/test")
    public String verificarConexion() {
        return "Conexión OK con backend!";
    }

    // Listar todos los productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos() {
        return servicio.findAll();
    }

    @GetMapping("/pagos")
    public List<PagoEntity> obtenerPagos() {
        return pagoService.obtenerPagos();
    }

    @GetMapping("/facturacion")
    public List<FacturaEntity> obtenerFacturas() {
        return facturaService.obtenerFacturas();
    }


    // Crear un nuevo producto
    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return servicio.saveProducto(producto);
    }

    // ----------------- ENDPOINTS DE PAGOS -----------------

    // Procesar un pago completo (inventario + pago + facturación)
    @PostMapping("/pagos")
    @Transactional // asegura rollback distribuido con JTA si algo falla
    public ResponseEntity<String> procesarPago(@RequestBody PagoRequest request) {
        try {
            pagoService.procesarPago(request);
            return ResponseEntity.ok("Pago procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el proceso de pago: " + e.getMessage());
        }
    }
}
