package com.taller3arqui.demo.DTO;

import java.util.List;

public class PagoRequest {
    private String cliente;
    private String metodoPago;
    private Double monto;
    private Long usuarioId;
    private List<ProductoCompra> productos;

    // Getters y setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public List<ProductoCompra> getProductos() { return productos; }
    public void setProductos(String productos2) { this.productos = productos2; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
