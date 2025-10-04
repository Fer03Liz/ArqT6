package com.taller3arqui.demo.grpc;
import com.taller3arqui.demo.grpc.services.Services.PagoRequest;
import com.taller3arqui.demo.grpc.services.Services.PagoResponse;
import com.taller3arqui.demo.grpc.services.PagoServiceGrpc;

import io.grpc.stub.StreamObserver;

public class PagoGrpcService extends PagoServiceGrpc.PagoServiceImplBase {

    @Override
    public void procesarPago(PagoRequest request, StreamObserver<PagoResponse> responseObserver) {
        // Aquí recibes directamente el request generado por gRPC (ya con getters)
        String cliente = request.getCliente();
        double monto = request.getMonto();
        String metodoPago = request.getMetodoPago();

        // Ejemplo de lógica
        boolean exito = monto > 0;
        String mensaje = exito ? "Pago procesado con éxito" : "Error en el pago";

        // Respuesta usando el builder
        PagoResponse response = PagoResponse.newBuilder()
            .setEstado("OK")   // porque tu proto tiene `string estado`
            .setMensaje("Pago procesado correctamente")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
