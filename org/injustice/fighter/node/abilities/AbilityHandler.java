package org.injustice.fighter.node.abilities;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import sk.action.ActionBar;
import sk.action.BarNode;
import sk.action.ability.AbilityType;
import sk.action.ability.BarAbility;

public class AbilityHandler extends Node {
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
        for (BarNode node : ActionBar.getAllNodes())  {
            if (node != null && Util.isUnderAttack()) {
                if (node.isValid() && node.canUse()) {
                    if (Util.getHpPercent() < 60) {
                        if (!abilityIsBasic(node)) {
                            Var.status = "[ABILITIES] Getting rejuv adrenaline";
                            node = basicFilter;
                        }
                    }
                    try {
                    useAbility:
                    if (Players.getLocal().getInteracting().getHealthPercent() < 30) {
                        if (!abilityIsBasic(node)) {
                            if (Players.getLocal().getInteracting().getHealthPercent() < 60) {
                                Var.status = "[ABILITY] Not using Ultimate";
                                node = basicFilter;
                                break useAbility;
                            }
                            Var.status = "[ABILITIES] Not using Threshold";
                            node = thresFilter;
                        }
                    }
                        Var.status = "[ABILITIES] " + format(node.toString().substring(9));
                        node.use();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("NPE: " + e.getMessage());
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
