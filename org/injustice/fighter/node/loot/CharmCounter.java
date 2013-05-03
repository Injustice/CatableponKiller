package org.injustice.fighter.node.loot;

import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Loot;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 03/05/13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public class CharmCounter extends LoopTask {
    private int getCount(Loot loot) {
        return Inventory.getItem(loot.getId()).getStackSize();
    }

    private int getTotalCount() {
        return  getCount(Loot.GOLD_CHARM) +
                getCount(Loot.GREEN_CHARM) +
                getCount(Loot.BLUE_CHARM) +
                getCount(Loot.CRIMSON_CHARM);
    }

    private int lastCount = getTotalCount();


    @Override
    public int loop() {
        if (lastCount != getTotalCount()) {
            Var.charmsLooted++;
        }
        lastCount = getTotalCount();
        return 100;
    }
}
