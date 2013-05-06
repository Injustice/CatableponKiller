package org.injustice.fighter.node;

import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Loot;
import org.injustice.framework.Strategy;
import org.injustice.framework.Task;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 24/04/13
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class Attacker extends Strategy implements Task {
    @Override
    public boolean activate() {
        return getCurrentInteracting() == null
                &&
                !Util.isUnderAttack()
                &&
                Players.getLocal().getHealthPercent() > 41
                &&
                GroundItems.getNearest(Loot.ALL_CHARMS.getIds()) == null
                &&
                !Var.cannotReach;
    }

    @Override
    public void execute() {
        NPC target;
        target = NPCs.getNearest(targetFilter);
        top:
        if (target != null) {
            if (Util.isOnScreen(target)) {
                if (target.interact("Attack")) {
                    Var.status = "[ATTACK] Attacking";
                    Util.debug();
                    Util.waitFor(new Condition() {
                        @Override
                        public boolean validate() {
                            return Players.getLocal().isInCombat();
                        }
                    }, 2500);
                    if (Camera.getPitch() != 86) {
                        new Thread(new Runnable() {
                            public void run() {
                                Camera.setPitch(true);
                            }
                        }).start();
                    }
                }
            } else {
                Var.status = ("[ATTACK] Turning to target");
                Util.debug();
                Camera.turnTo(target);
                Util.waitFor(new Condition() {
                    @Override
                    public boolean validate() {
                        return NPCs.getNearest(targetFilter).isOnScreen();
                    }
                }, 500);
            }
        } else {
            if (NPCs.getNearest(Var.CATABLEPON_ID) != null) {
                target = NPCs.getNearest(targetFilter);
                break top;
            }
            Var.status = "[ATTACK] Waiting for spawn";
            Util.debug();
            Util.waitFor(new Condition() {
                @Override
                public boolean validate() {
                    return NPCs.getNearest(Var.CATABLEPON_ID) != null;
                }
            }, 5000);
        }
    }

    private Filter<NPC> targetFilter = new Filter<NPC>() {
        @Override
        public boolean accept(NPC npc) {
            return (npc != null
                    &&
                    npc.validate()
                    &&
                    npc.getId() == Var.CATABLEPON_ID
                    &&
                    npc.getAnimation() != Var.CATABLEPON_DEATH_ANIMATION_ID
                    &&
                    (npc.getInteracting() == null ||
                            npc.getInteracting() == Players.getLocal().get())
            );
        }
    };



    private org.powerbot.game.api.wrappers.interactive.Character getCurrentInteracting() {
        return Players.getLocal().getInteracting() != null ?
                Players.getLocal().getInteracting() :
                null;
    }
}
