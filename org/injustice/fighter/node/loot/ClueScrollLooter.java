package org.injustice.fighter.node.loot;

import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.enums.Loot;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/04/13
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class ClueScrollLooter extends Node {
    @Override
    public boolean activate() {
        Var.status = "[SCROLL] Validating";
        return GroundItems.getNearest(Loot.MEDIUM_CLUE_SCROLL.getId()) != null
                &&
                !Inventory.contains(Loot.MEDIUM_CLUE_SCROLL.getId())
                &&
                !Util.isUnderAttack()
                &&
                Players.getLocal().getInteracting() == null
                &&
                !Players.getLocal().isMoving();
    }

    @Override
    public void execute() {
        final GroundItem scroll = GroundItems.getNearest(Loot.MEDIUM_CLUE_SCROLL.getId());
        if (scroll != null) {
            if (Util.isOnScreen(scroll)) {
                if (scroll.interact("Take", scroll.getGroundItem().getName())) {
                    if (scroll.getGroundItem().getWidgetChild().validate()) {
                        Var.status = "[LOOT] Clue scroll";
                        Util.waitFor(new Condition() {
                            @Override
                            public boolean validate() {
                                return !Players.getLocal().isMoving();
                            }
                        }, 2500);
                    }
                }
            } else {
                Var.status = "[LOOT] Turning to scroll";
                Camera.turnTo(scroll);
            }
        }
    }
}
