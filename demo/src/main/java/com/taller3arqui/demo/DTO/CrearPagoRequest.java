package com.taller3arqui.demo.DTO;

public class CrearPagoRequest {
    private String usuario;
    private double monto;
    private String metodoPago;
    private String productos; // JSON o CSV de productos

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public String getProductos() { return productos; }
    public void setProductos(String productos) { this.productos = productos; }

    public PagoRequest toPagoRequest() {
    PagoRequest pagoRequest = new PagoRequest();
    pagoRequest.setMonto(this.monto);
    pagoRequest.setMetodoPago(this.metodoPago);
    pagoRequest.setProductos(this.productos);
    return pagoRequest;
}

}
