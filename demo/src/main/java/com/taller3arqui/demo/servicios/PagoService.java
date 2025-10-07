package com.taller3arqui.demo.servicios;

import com.taller3arqui.demo.DTO.PagoRequest;
import com.taller3arqui.demo.DTO.ProductoCompra;
import com.taller3arqui.demo.entidades.FacturaEntity;
import com.taller3arqui.demo.entidades.InventarioEntity;
import com.taller3arqui.demo.entidades.PagoEntity;
import com.taller3arqui.demo.repositorios.FacturaRepository;
import com.taller3arqui.demo.repositorios.InventarioRepository;
import com.taller3arqui.demo.repositorios.PagoRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<PagoEntity> obtenerPagos() {
        return pagoRepository.findAll();
    }

    @Transactional
    public void procesarPago(PagoRequest request) {
        // 1. Validar y actualizar inventario
        for (ProductoCompra producto : request.getProductos()) {
            InventarioEntity inventario = inventarioRepository.findById(producto.getProductoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto con ID " + producto.getProductoId() + " no encontrado."));

            if (inventario.getCantidad() < producto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + inventario.getTituloProductos());
            }

            // Descontar stock
            inventario.setCantidad(inventario.getCantidad() - producto.getCantidad());
            inventarioRepository.save(inventario);
        }

        // 2. Registrar pago
        PagoEntity pago = new PagoEntity();
        pago.setCliente(request.getCliente());
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);

        // 3. Generar facturas asociadas al pago
        for (ProductoCompra producto : request.getProductos()) {
            FacturaEntity factura = new FacturaEntity();
            factura.setCliente(request.getCliente());
            factura.setDescripcionPedido("Compra de producto ID: " + producto.getProductoId());
            factura.setProductoId(producto.getProductoId());
            factura.setCantidad(producto.getCantidad());

            double precio = inventarioRepository.findById(producto.getProductoId())
                    .map(InventarioEntity::getPrecio)
                    .orElse(0.0);
            factura.setTotal(producto.getCantidad() * precio);
            factura.setFecha(LocalDateTime.now());
            facturacionRepository.save(factura);
        }
    }
}
