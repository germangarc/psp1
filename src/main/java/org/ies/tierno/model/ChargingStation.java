package org.ies.tierno.model;

import java.util.concurrent.Semaphore;

public class ChargingStation {
    private final Semaphore chargers = new Semaphore(4);
    private final StationStats stats;

    public ChargingStation(StationStats stats) {
        this.stats = stats;
    }

    public ChargeResult charge(Vehicle vehicle) throws InterruptedException {
        chargers.acquire();
        try {
            System.out.println("El vehículo " + vehicle.plate() + " está cargando.");
            Thread.sleep(vehicle.kwh() * 20L);
            stats.register(vehicle.kwh());
            System.out.println("El vehículo " + vehicle.plate() + " ha finalizado su carga.");
        } finally {
            chargers.release();
        }
        ChargeResult chargeResult = new ChargeResult(vehicle.plate(), vehicle.kwh(), vehicle.kwh() * 45L);
        return chargeResult;
    }
}
