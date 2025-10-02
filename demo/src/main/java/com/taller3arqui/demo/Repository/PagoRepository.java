package com.taller3arqui.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.Entity.PagoEntity;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {}
