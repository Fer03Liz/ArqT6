package com.taller3arqui.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taller3arqui.demo.Entity.PagoEntity;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {}
