package org.injustice.fighter.util.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public enum Food {
    // I have this so that when/if I add a GUI people
    // will be able to choose which foods they eat
    TROUT("Trout", 333, 1),
    SALMON("Salmon", 351, 2),
    TUNA("Tuna", 329, 3),
    LOBSTER("Lobster", 361, 4),
    SWORDFISH("Swordfish", 379, 5),
    MONKFISH("Monkfish", 365, 6),
    SHARK("Shark", 373, 7),             // TODO foods
    MANTARAY("Manta Ray", 7946, 8),
    ROCKTAIL("Rocktail", 385, 9),
    ALL(333, 351, 329,
            361, 379, 365,
            373, 7946, 385, 697, 391, 15266, 15272);

    private String name;
    private int id;
    private int[] ids;
    private int i;

    Food(final String name, final int id, final int i) {
        this.name = name;
        this.id = id;
        this.i = i;
    }

    Food(final int... ids) {
        this.ids = ids;
    }

    public int getId() {
        return id;
    }

    public int[] getIds() {
        return ids;
    }

    public String getName() {
        return name;
    }

    public static final int[] FOOD_IDS = {333, 351, 329,
            361, 379, 365,
            373, 7946, 385, 697, 391, 15266, 15272};
}
