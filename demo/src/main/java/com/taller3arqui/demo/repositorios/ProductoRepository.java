package com.taller3arqui.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller3arqui.demo.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    
}