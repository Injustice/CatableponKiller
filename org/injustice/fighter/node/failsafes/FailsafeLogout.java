package org.injustice.fighter.node.failsafes;

import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Food;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.DefenseAbility;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/04/13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class FailsafeLogout extends Node {
    BarNode rejuv = ActionBar.getNode(DefenseAbility.REJUVENATE);
    @Override
    public boolean activate() {
        Var.status = "[FAILSAFE] Validating";
        return Util.getHpPercent() > 10
                &&
                !Inventory.contains(Food.ALL.getIds())
                &&
                !rejuv.canUse();
    }

    @Override
    public void execute() {
        SceneObject closestGate = SceneEntities.getNearest(16089, 16090);
        if (closestGate != null &&
                Calculations.distanceTo(closestGate) <= 10 &&
                Calculations.distanceTo(Var.CENTRE_TILE) <= 5 &&
                Var.CENTRE_TILE.canReach()) {
            if (Util.isOnScreen(closestGate)) {
                if (closestGate.interact("Open")) {
                    Var.status = "[FAILSAFE] About to die, leaving room";
                    Util.waitFor(new Condition() {
                        @Override
                        public boolean validate() {
                            return Var.CENTRE_TILE.canReach();
                        }
                    }, 15000); // 10 seconds to log out after combat
                }
            }
        }
        tryAgain:
        if (Game.logout(true)) {
            Var.status = "[FAILSAFE] Logging out";
        } else {
            sleep(1500, 2000);
            break tryAgain;
        }
    }
}
