package com.taller3arqui.demo.services;

import com.taller3arqui.demo.dto.PagoRequest;
import com.taller3arqui.demo.dto.ProductoCompra;
import com.taller3arqui.demo.Entity.Factura;
import com.taller3arqui.demo.Entity.Pago;
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
            boolean disponible = inventarioRepository.existsByIdAndStockGreaterThanEqual(
                    producto.getProductoId(),
                    producto.getCantidad()
            );
            if (!disponible) {
                throw new RuntimeException("Producto con ID " + producto.getProductoId() + " no disponible.");
            }
        }

        // 2. Registrar el pago en BD de pagos
        Pago pago = new Pago();
        pago.setUsuarioId(request.getUsuarioId());
        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodoPago());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);

        // 3. Registrar facturas en BD de facturación
        for (ProductoCompra producto : request.getProductos()) {
            Factura factura = new Factura();
            factura.setPagoId(pago.getId());
            factura.setProductoId(producto.getProductoId());
            factura.setCantidad(producto.getCantidad());
            factura.setFecha(LocalDateTime.now());
            facturacionRepository.save(factura);
        }
    }
}
