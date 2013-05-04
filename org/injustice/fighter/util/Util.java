package org.injustice.fighter.util;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/04/13
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    /**
     * Checks if logged in and game is not loading
     * @return true if ready to use script
     */
    public static boolean isReady() {
        return Game.getClientState() == 11;
    }

    /**
     * Gets the Player's HP even if not in combat
     * @return Player's HP
     */
    public static int getHpPercent() {
        if (!Players.getLocal().isInCombat()) {
            int currHP = Widgets.get(748, 5).getHeight();
            if ((Math.abs(100 - 100 * currHP / 28) * 120 / 100) >= 100)
                return 100;
            return Math.abs(100 - 100 * currHP / 28) * 120 / 100;
        }
        return Players.getLocal().getHealthPercent();
    }

    /**
     * Checks if an entity is on screen, avoiding the Action Bar bounds
     * @param e Entity to check for
     * @return true if the Entity is on screen
     */
    public static boolean isOnScreen(Entity e) {
        setActionbar(true);
        WidgetChild actionbar = Widgets.get(640, 6);
        return e.isOnScreen() && (actionbar == null || !(actionbar != null &&
                            actionbar.getBoundingRectangle().contains(e.getCentralPoint())));

    }

    /**
     * Opens/closes the action bar
     * @param open set the actionbar open
     */
    public static void setActionbar(boolean open) {
        WidgetChild actionbar = Widgets.get(640, 6);
        if (open = actionbar.isOnScreen())
            return;
        WidgetChild toggle = Widgets.get(640, open ? 28 : 30);
        if (open ? !actionbar.isOnScreen() : actionbar.isOnScreen()) {
            if (toggle.validate() && toggle.isOnScreen()) {
                toggle.interact(open ? "Expand" : "Minimise");
            }
        }
    }

    /**
     * Dynamic sleep
     * Waits for either {@link Condition} to be true or timeout to be reached
     * whichever comes first
     * @param c Condition to check for
     * @param timeout Max waiting time
     * @return condition
     */
    public static boolean waitFor(final Condition c, final long timeout) {
        final Timer t = new Timer(timeout);
        while (!c.validate() || t.isRunning()) {
            Task.sleep(50, 75);
        }
        return c.validate();
    }

    /**
     * @param number    number we want to format
     * @param precision precision in dp
     * @return formatted number
     */
    public static String format(long number, int precision) {
        String sign = number < 0 ? "-" : "";
        number = Math.abs(number);
        if (number < 1000) {
            return sign + number;
        }
        int exponent = (int) (Math.log(number) / Math.log(1000));
        return String.format("%s%." + precision + "f%s", sign,
                number / Math.pow(1000, exponent),
                "kmbtpe".charAt(exponent - 1));
    }

    /**
     * Checks if the player is under attack
     * @return true if player is being attacked
     */
    public static boolean isUnderAttack() {
        return NPCs.getNearest(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.getInteracting() != null &&
                        npc.getInteracting().equals(Players.getLocal()) &&
                        npc.getLevel() > 0 &&
                        npc.getHealthPercent() > 0;
            }
        }) != null;
    }

    /**
     * Outputs the status to terminal with other information
     */
    public static void debug() {
        if (Var.DEBUG_MODE) {
            System.out.println("[DEBUG] - " + Var.runTime.toElapsedString() + " - " +
                    Var.status);
        }
    }

    public static class ExpStats {
        public ExpStats() {

        }

        public int getTotalLvl() {
            return getLvl(Skills.ATTACK) +
                    getLvl(Skills.MAGIC) +
                    getLvl(Skills.DEFENSE) +
                    getLvl(Skills.CONSTITUTION) +
                    getLvl(Skills.STRENGTH) +
                    getLvl(Skills.RANGE) +
                    getLvl(Skills.MAGIC);
        }

        public int getLvl(int skill) {
            return Skills.getRealLevel(skill);
        }

        public int getTotalExp() {
            return getExp(Skills.ATTACK) +
                    getExp(Skills.MAGIC) +
                    getExp(Skills.DEFENSE) +
                    getExp(Skills.CONSTITUTION) +
                    getExp(Skills.STRENGTH) +
                    getExp(Skills.RANGE) +
                    getExp(Skills.MAGIC);
        }

        public int getExp(int skill) {
            return Skills.getExperience(skill);
        }
    }

}
