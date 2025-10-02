package com.taller3arqui.demo.DTO;

public class CrearPagoRequest {
    private String usuario;
    private double monto;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}
