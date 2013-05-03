package org.injustice.fighter.util.enums;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public enum Loot {
    // I have this so that when/if I add a GUI
    // people will be able to choose what to pick up
    // and so that I can add other loot easily
    GOLD_CHARM(true, 12158),
    GREEN_CHARM(true, 12159),
    CRIMSON_CHARM(true, 12160),
    BLUE_CHARM(true, 12163),
    ALL_CHARMS(true, 12158, 12159, 12160, 12163),
    MEDIUM_CLUE_SCROLL(false, 3612),
    TOP_SCEPTRE(false, 9010);


    private int id;
    private int[] ids;
    private boolean stackable;

    Loot(final boolean stackable, final int id) {
        this.id = id;
        this.stackable = stackable;
    }

    Loot(final boolean stackable, final int... ids) {
        this.ids = ids;
        this.stackable = stackable;
    }

    public int getId() {
        return id;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int[] getIds() {
        return ids;
    }

}
