package com.example.controller;


import com.example.dtos.RouteRequestDTO;
import com.example.dtos.RouteResponseDTO;
import com.example.service.RouteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class RouteController {

    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    ResponseEntity<?> getRoute(@Valid @RequestBody RouteRequestDTO request) {
        logger.info("Recebida solicitação de rota: origem={} destino={}", request.getOrigin(), request.getDestination());

        if (request.getOrigin() == null || request.getDestination() == null) {
            return ResponseEntity.badRequest().body("Origem e destino são obrigatórios.");
        }

        if (request.getOrigin().equalsIgnoreCase(request.getDestination())) {
            return ResponseEntity.badRequest().body("A origem e o destino não podem ser iguais.");
        }



        RouteResponseDTO response = routeService.calcularRota(request);
        return ResponseEntity.ok(response);
    }
}
