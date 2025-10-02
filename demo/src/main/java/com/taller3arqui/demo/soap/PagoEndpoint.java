package com.taller3arqui.demo.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

// IMPORTANTE: estos objetos (CrearPagoRequest, CrearPagoResponse) los generas a partir del XSD
// usando el plugin jaxb2-maven-plugin o clases simples DTO si solo quieres la prueba.

import com.taller3arqui.demo.DTO.CrearPagoRequest;
import com.taller3arqui.demo.DTO.CrearPagoResponse;

@Endpoint
public class PagoEndpoint {

    private static final String NAMESPACE_URI = "http://taller3arqui.com/pagos";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearPagoRequest")
    @ResponsePayload
    public CrearPagoResponse crearPago(@RequestPayload CrearPagoRequest request) {
        CrearPagoResponse response = new CrearPagoResponse();
        response.setResultado("Pago SOAP creado para " + request.getUsuario() +
                              " con monto " + request.getMonto());
        return response;
    }
}
