// package com.taller3arqui.demo.grpc;

// import com.taller3arqui.demo.services.pagoService;
// import com.taller3arqui.demo.dto.PagoRequest;


// @GrpcService
// public class PagoGrpcService extends PagoServiceGrpc.PagoServiceImplBase {

//     private final PagoService pagoService;

//     public PagoGrpcService(PagoService pagoService) {
//         this.pagoService = pagoService;
//     }

//     @Override
//     public void procesarPago(PagoRequest request, StreamObserver<PagoResponse> responseObserver) {
//         pagoService.procesarPago(
//             request.getCliente(),
//             request.getProductosList(),
//             request.getMetodoPago(),
//             request.getMonto()
//         );

//         PagoResponse response = PagoResponse.newBuilder()
//             .setEstado("OK")
//             .build();

//         responseObserver.onNext(response);
//         responseObserver.onCompleted();
//     }
// }
