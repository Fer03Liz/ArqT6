package com.taller3arqui.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.Entity.InventarioEntity;

public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    boolean existsByIdAndCantidadGreaterThanEqual(Long id, int cantidad);
}
