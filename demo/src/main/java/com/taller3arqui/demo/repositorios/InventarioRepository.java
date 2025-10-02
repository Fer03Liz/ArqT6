package com.taller3arqui.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.entidades.InventarioEntity;

public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    boolean existsByIdAndCantidadGreaterThanEqual(Long id, int cantidad);
}
