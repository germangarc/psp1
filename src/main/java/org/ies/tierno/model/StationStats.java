package org.ies.tierno.model;

public class StationStats {
    private int totalKwh = 0    ;
    private Long totalCents = 0L;

    public synchronized void register(int kwh) {
        totalKwh += kwh;
        totalCents += kwh * 45L;
    }

    public synchronized StatsSnapshot snapshot(){
        return new StatsSnapshot(totalKwh, totalCents);
    }
}
