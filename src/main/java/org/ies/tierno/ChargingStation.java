package org.ies.tierno;

import java.util.concurrent.Semaphore;

public class ChargingStation {
    private final Semaphore chargers = new Semaphore(4);
    private final StationStats stats;

    public ChargingStation(StationStats stats) {
        this.stats = stats;
    }

    public void chargeResoult(Vehicle vehicle) throws InterruptedException {
        chargers.acquire();
        try {
            System.out.println("El vehículo está cargando.");
            Thread.sleep(vehicle.kwh() * 20L);
            stats.register(vehicle.kwh());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            chargers.release();
        }
    }
}
