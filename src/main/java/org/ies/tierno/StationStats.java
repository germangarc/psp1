package org.ies.tierno;

import static java.lang.classfile.Attributes.record;

public class StationStats {
    private int totalKwh = 0    ;
    private int totalCents = 0;

    public synchronized void register(int kwh) {
        totalKwh += kwh;
    }

    public synchronized StatsSnapshot snapshot(){
        return new StatsSnapshot(totalKwh, totalCents);
    }
}
