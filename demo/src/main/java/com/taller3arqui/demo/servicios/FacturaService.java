package com.taller3arqui.demo.servicios;

import com.taller3arqui.demo.entidades.FacturaEntity;
import com.taller3arqui.demo.repositorios.FacturaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;


    public List<FacturaEntity> obtenerFacturas() {
        return facturaRepository.findAll();
    }



}
