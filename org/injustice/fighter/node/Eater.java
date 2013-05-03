package org.injustice.fighter.node;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Food;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import sk.action.ability.DefenseAbility;

public class Eater extends Node {
    DefenseAbility rejuvenate = DefenseAbility.REJUVENATE;
    public boolean activate() {
        return Inventory.contains(Food.ALL.getIds())
                &&
                Util.getHpPercent() <= 40
                &&
                !rejuvenate.isVisible();
    }

    public void execute() {
        Item food = Inventory.getItem(Food.ALL.getIds());
        if (food != null) {
            if (food.getWidgetChild().interact("Eat")) {
                Var.status = "[EAT] " + food.getName();
            }
        }
    }

}