package com.taller3arqui.demo.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
public class InventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo_productos", nullable = false)
    private String tituloProductos;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "precio", nullable = false)
    private Double precio;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTituloProductos() { return tituloProductos; }
    public void setTituloProductos(String tituloProductos) { this.tituloProductos = tituloProductos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
