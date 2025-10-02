package com.taller3arqui.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.entidades.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {}
