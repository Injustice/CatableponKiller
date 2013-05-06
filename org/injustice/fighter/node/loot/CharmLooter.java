
package org.injustice.fighter.node.loot;

import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Loot;
import org.injustice.framework.Strategy;
import org.injustice.framework.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/04/13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class CharmLooter extends Strategy implements Task {

    @Override
    public boolean activate() {
        return GroundItems.getNearest(Loot.ALL_CHARMS.getIds()) != null
                &&
                Players.getLocal().getInteracting() == null
                &&
                !Players.getLocal().isMoving()
                &&
                !Util.isUnderAttack()
                &&
                canPickCharms();
    }

    @Override
    public void execute() {
        final GroundItem charm = GroundItems.getNearest(Loot.ALL_CHARMS.getIds());
        int count = Inventory.getCount(charm.getId());
        if (count != Inventory.getCount(charm.getId())) {
            Var.charmsLooted++;
        }
        Var.status = ("[LOOT] In charm node");
        Util.debug();
        if (charm != null) {
            if (Util.isOnScreen(charm)) {
                if (charm.interact("Take", charm.getGroundItem().getName())) {
                    Var.status = "[LOOT] " + charm.getGroundItem().getName();
                    Util.debug();
                    if (checkCount(charm.getId(), count)) {
                        Var.charmsLooted++;
                    }
                    org.powerbot.core.script.job.Task.sleep(500);
                    Util.waitFor(new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving();
                        }
                    }, 1000);
                }
            } else {
                Camera.turnTo(charm);
                Var.status = "[LOOT] Turning to charm";
                Util.debug();
            }
        }
    }

    private boolean canPickCharms() {
        if (!Inventory.isFull())
            return true;
        final GroundItem charm = GroundItems.getNearest(Loot.ALL_CHARMS.getIds());
        return checkCharms(charm, Loot.GOLD_CHARM.getId()) ||
                checkCharms(charm, Loot.GREEN_CHARM.getId())||
                checkCharms(charm, Loot.BLUE_CHARM.getId()) ||
                checkCharms(charm, Loot.CRIMSON_CHARM.getId());
    }

    private boolean checkCharms(GroundItem g, int i) {
        return g.getId() == i && Inventory.contains(i);
    }

    private boolean checkCount(int i, int count) {
        return Inventory.getCount(i) < count;
    }
}
