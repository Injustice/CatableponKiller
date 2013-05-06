package org.injustice.fighter.node.abilities;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.framework.Strategy;
import org.injustice.framework.Task;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.AbilityType;
import sk.action.ability.BarAbility;
import sk.action.ability.DefenseAbility;

public class AbilityHandler extends Strategy implements Task {
    BarNode rejuv = ActionBar.getNode(DefenseAbility.REJUVENATE);
    NPC[] possibleTargets = NPCs.getLoaded(Var.CATABLEPON_ID);

    public boolean activate() {
        return possibleTargets != null
                &&
                Players.getLocal().getInteracting() != null
                &&
                Players.getLocal().isInCombat()
                &&
                ActionBar.makeReady()
                &&
                Util.isUnderAttack();
    }

    public void execute() {
        BarNode basicFilter = ActionBar.getNode(new Filter<BarNode>() {
            @Override
            public boolean accept(BarNode n) {
                return n != null && n.isValid() && n.canUse() &&
                        abilityIsBasic(n);
            }
        });
        BarNode thresFilter = ActionBar.getNode(new Filter<BarNode>() {
            @Override
            public boolean accept(BarNode n) {
                return n != null && n.isValid() && n.canUse() &&
                        !abilityIsUltimate(n);
            }
        });
        for (BarNode node : ActionBar.getNodes())  {
            if (node != null && Util.isUnderAttack()) {
                if (node.isValid() && node.canUse()) {
                    if (Util.getHpPercent() < 60) {
                        if (!ActionBar.getNode(DefenseAbility.REJUVENATE).canUse() && !abilityIsBasic(node)) {
                            Var.status = "[ABILITY] Getting rejuv adrenaline";
                            Util.debug();
                            node = basicFilter;
                        }
                    }
                    try {
                    useAbility:
                    if (Players.getLocal().getInteracting() != null &&  // NPE here
                            Players.getLocal().getInteracting().getHealthPercent() < 30) {
                        if (!abilityIsBasic(node)) {
                            if (Players.getLocal().getInteracting().getHealthPercent() < 60) {
                                Var.status = "[ABILITY] Not using Ultimate";
                                Util.debug();
                                node = basicFilter;
                                break useAbility;
                            }
                            Var.status = "[ABILITY] Not using Threshold";
                            Util.debug();
                            node = thresFilter;
                        }
                    }
                        Var.status = "[ABILITY] " + format(node.toString().substring(9));
                        Util.debug();
                        node.use();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("NPE: " + e.getCause());
                        Var.status = "NPE: " + e.getCause();
                        Util.debug();
                    }
                    // Was getting NPEs in this part
                }
            }
        }
    }

    private boolean abilityIsUltimate(BarNode node) {
        BarAbility b = (BarAbility) node;
        return b.getDraggable().getType() == AbilityType.ULTIMATE;
    }

    private boolean abilityIsThresh(BarNode node) {
        BarAbility b = (BarAbility) node;
        return b.getDraggable().getType() == AbilityType.THRESHOLD;
    }

    private boolean abilityIsBasic(BarNode node) {
        return !abilityIsThresh(node) && !abilityIsUltimate(node);
    }

    private String format(String s) {
        String string = s.replace('_', ' ');
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
