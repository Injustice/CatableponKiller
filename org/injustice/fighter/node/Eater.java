package org.injustice.fighter.node;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Food;
import org.injustice.framework.Strategy;
import org.injustice.framework.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.DefenseAbility;

public class Eater extends Strategy implements Task {
    DefenseAbility rejuvenate = DefenseAbility.REJUVENATE;
    BarNode rejuv = ActionBar.getNode(rejuvenate);
    public boolean activate() {
        return Inventory.contains(Food.ALL.getIds())
                &&
                Util.getHpPercent() <= 40
                &&
                !rejuv.canUse();
    }

    public void execute() {
        Item food = Inventory.getItem(Food.ALL.getIds());
        if (food != null) {
            if (food.getWidgetChild().interact("Eat")) {
                Var.status = "[EAT] " + food.getName();
                Util.debug();
            }
        }
    }

}