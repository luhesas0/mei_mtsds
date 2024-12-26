package com.estg.exceptions;

public class VehicleCapacityExceeded extends RuntimeException {
    public VehicleCapacityExceeded(Integer id) {
        super("Capacidade do veículo do funcionário com o id " + id + " excedida.");
    }
}
