package com.taller3arqui.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.entidades.PagoEntity;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {}
