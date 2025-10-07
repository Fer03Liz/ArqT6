package com.taller3arqui.demo.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

// IMPORTANTE: estos objetos (CrearPagoRequest, CrearPagoResponse) los generas a partir del XSD
// usando el plugin jaxb2-maven-plugin o clases simples DTO si solo quieres la prueba.

import com.taller3arqui.demo.DTO.CrearPagoRequest;
import com.taller3arqui.demo.DTO.CrearPagoResponse;
import com.taller3arqui.demo.servicios.PagoService;

@Endpoint
public class PagoEndpoint {

    private static final String NAMESPACE_URI = "http://taller3arqui.com/demo/soap";

    private final PagoService pagoService;

    public PagoEndpoint(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearPagoRequest")
    @ResponsePayload
    public CrearPagoResponse crearPago(@RequestPayload CrearPagoRequest request) {
        pagoService.procesarPago(request.toPagoRequest()); // convierte DTO SOAP a DTO interno
        CrearPagoResponse response = new CrearPagoResponse();
        response.setResultado("Pago procesado correctamente");
        return response;
    }
}

