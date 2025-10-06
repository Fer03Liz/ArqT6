package com.taller3arqui.demo.Services;

import com.taller3arqui.demo.DTO.PagoRequest;
import com.taller3arqui.demo.DTO.ProductoCompra;
import com.taller3arqui.demo.Entity.FacturaEntity;
import com.taller3arqui.demo.Entity.PagoEntity;
import com.taller3arqui.demo.Repository.FacturaRepository;
import com.taller3arqui.demo.Repository.InventarioRepository;
import com.taller3arqui.demo.Repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagoService {

    private final InventarioRepository inventarioRepository;
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturacionRepository;

    public PagoService(InventarioRepository inventarioRepository,
                       PagoRepository pagoRepository,
                       FacturaRepository facturacionRepository) {
        this.inventarioRepository = inventarioRepository;
        this.pagoRepository = pagoRepository;
        this.facturacionRepository = facturacionRepository;
    }

    public void procesarPago(PagoRequest request) {
        // 1. Revisar inventario
        for (ProductoCompra producto : request.getProductos()) {
            boolean disponible = inventarioRepository.existsByIdAndCantidadGreaterThanEqual(
                    producto.getProductoId(),
                    producto.getCantidad()
            );
            if (!disponible) {
                throw new RuntimeException("Producto con ID " + producto.getProductoId() + " no disponible.");
            }
        }

        // 2. Registrar el pago en BD de pagos
        PagoEntity pago = new PagoEntity();
        pago.setUsuarioId(request.getUsuarioId());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);

        // 3. Registrar facturas en BD de facturación
        for (ProductoCompra producto : request.getProductos()) {
            FacturaEntity factura = new FacturaEntity();
            factura.setId(pago.getId());
            factura.setProductoId(producto.getProductoId());
            factura.setCantidad(producto.getCantidad());
            factura.setFecha(LocalDateTime.now());
            facturacionRepository.save(factura);
        }
    }
}