package com.taller3arqui.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller3arqui.demo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}