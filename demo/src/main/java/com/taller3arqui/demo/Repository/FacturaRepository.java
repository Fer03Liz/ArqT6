package com.taller3arqui.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.Entity.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {}
