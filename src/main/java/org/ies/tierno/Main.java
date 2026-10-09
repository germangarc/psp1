package org.ies.tierno;

import org.ies.tierno.model.ChargingStation;
import org.ies.tierno.model.StationStats;
import org.ies.tierno.model.StatsSnapshot;
import org.ies.tierno.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        List<Vehicle> vehicles = new ArrayList<>();
        int expectation = 0;

        for (int v = 0; v < 20; v++) {
            vehicles.add(new Vehicle(String.format("%04d-XYZ", random.nextInt(10000)), random.nextInt(20, 101)));
            expectation += vehicles.get(v).kwh();
        }

        StationStats stats = new StationStats();
        ChargingStation chargingStation = new ChargingStation(stats);

        try (ExecutorService pool = Executors.newFixedThreadPool(8)) {
            for (Vehicle vehicle : vehicles) {
                pool.submit(() -> {
                    try {
                        chargingStation.charge(vehicle);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        StatsSnapshot snapshot = stats.snapshot();

        System.out.println("Total de kWh registrados: " + snapshot.totalKwh());
        System.out.println("Total de kWh esperados: " + expectation);

        System.out.println("Total de céntimos registrados: " + snapshot.totalCents());
        System.out.println("Total de céntimos esperados: " + expectation*45L);
    }
}