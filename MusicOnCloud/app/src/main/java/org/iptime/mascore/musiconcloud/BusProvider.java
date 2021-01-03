package org.iptime.mascore.musiconcloud;

import com.squareup.otto.Bus;

/**
 * Created by Owner on 2017-03-16.
 */

public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
