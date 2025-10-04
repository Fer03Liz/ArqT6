package com.taller3arqui.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taller3arqui.demo.entidades.InventarioEntity;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    boolean existsByIdAndCantidadGreaterThanEqual(Long id, int cantidad);
}
