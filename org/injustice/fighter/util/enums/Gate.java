package org.injustice.fighter.util.enums;

import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 01/05/13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public enum Gate {
    // Failsafe
    NORTH_GATE_LEFT(16089, new Tile(2162, 5287, 0)),
    NORTH_GATE_RIGHT(16090, new Tile(2163, 5287, 0)),
    WEST_GATE_LEFT(16089, new Tile(2155, 5286, 0)),
    WEST_GATE_RIGHT(16090, new Tile(2156, 5286, 0)),
    SOUTH_GATE_LEFT(16089, new Tile(2163, 5277, 0)),
    SOUTH_GATE_RIGHT(16090, new Tile(2164, 5277, 0));

    private int id;
    private Tile loc;

    Gate(int id, Tile loc) {
        this.id = id;
        this.loc = loc;
    }

    public int getId() {
        return id;
    }

    public Tile getLoc() {
        return loc;
    }

}
