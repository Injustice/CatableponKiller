package org.injustice.fighter.node.abilities;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.powerbot.core.script.job.state.Node;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.DefenseAbility;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/04/13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class RejuvenateUser extends Node {
    BarNode rejuv = ActionBar.getNode(DefenseAbility.REJUVENATE);
    @Override
    public boolean activate() {
        return ActionBar.SlotData.validateSlot(rejuv.getSlot())
                        &&
                        ActionBar.getAdrenaline() == 100
                        &&
                        Util.getHpPercent() <= 60;
    }

    @Override
    public void execute() {
        if (rejuv.canUse()) {
            Var.status = "[REJUV] Using";
            rejuv.use();
        }
    }
}
