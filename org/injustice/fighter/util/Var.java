package org.injustice.fighter.util;

import org.injustice.fighter.Fighter;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class Var {
    /* - CONSTANTS - */
    public static final int CATABLEPON_ID = 4398;
    public static final int CATABLEPON_DEATH_ANIMATION_ID = 4270;

    public static final double VERSION = Fighter.class.
            getAnnotation(Manifest.class).version();

    public static final Area CATABLEPON_ROOM = new Area(new Tile(2167, 5287, 0),
            new Tile(2167, 5278, 0), new Tile(2155, 5278, 0), new Tile(2155, 5287, 0));

    public static final Tile CENTRE_TILE = new Tile(2162, 5283, 0);

    /* - DYNAMICS - */

    public static boolean cannotReach = false;


    public static int startConstitutionExp;
    public static int totalExp;
    public static int startConstitutionLvl;
    public static int totalLvl;
    public static int charmsLooted;

    public static String status;

    public static Timer runTime = new Timer(0);

    public static Tile startTile;
}
