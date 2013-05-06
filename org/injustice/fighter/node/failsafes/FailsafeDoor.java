package org.injustice.fighter.node.failsafes;

import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Gate;
import org.injustice.framework.Strategy;
import org.injustice.framework.Task;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 01/05/13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class FailsafeDoor extends Strategy implements Task {
    @Override
    public boolean activate() {
        return !Var.CENTRE_TILE.canReach() || Var.cannotReach;
    }

    @Override
    public void execute() {
        Var.status = "[GATE]";
        Util.debug();
        SceneObject gate = SceneEntities.getNearest(closestGate);
        if (gate != null) {
            Var.status = "[GATE] Opening";
            Util.debug();
            if (Util.isOnScreen(gate)) {
                if (gate.interact("Open")) {
                    Var.cannotReach = false;
                    org.powerbot.core.script.job.Task.sleep(1000, 1250);
                    Util.waitFor(new Condition() {
                        @Override
                        public boolean validate() {
                            return Var.CENTRE_TILE.canReach();
                        }
                    }, 2500);
                }
            }
        // Extra check, haven't thoroughly tested the Gate enum
        } else if (SceneEntities.getNearest(16089, 16090) != null){
            gate = SceneEntities.getNearest(16089, 16090);
            if (gate.interact("Open")) {
                Var.cannotReach = false;
            }
        } else {
            Var.status = "[GATE] Null";
            Util.debug();
        }
    }


    private Filter<SceneObject> closestGate = new Filter<SceneObject>() {
        @Override
        public boolean accept(SceneObject gate) {
            return (gate != null && gate.validate())
                    &&
                    (gate.getId() == Gate.NORTH_GATE_LEFT.getId() &&
                    gate.getLocation() == Gate.NORTH_GATE_LEFT.getLoc())
                    ||
                    (gate.getId() == Gate.NORTH_GATE_RIGHT.getId() &&
                    gate.getLocation() == Gate.NORTH_GATE_RIGHT.getLoc())
                    ||
                    (gate.getId() == Gate.SOUTH_GATE_LEFT.getId() &&
                    gate.getLocation() == Gate.SOUTH_GATE_LEFT.getLoc())
                    ||
                    (gate.getId() == Gate.SOUTH_GATE_RIGHT.getId() &&
                    gate.getLocation() == Gate.SOUTH_GATE_RIGHT.getLoc())
                    ||
                    (gate.getId() == Gate.WEST_GATE_LEFT.getId() &&
                    gate.getLocation() == Gate.WEST_GATE_LEFT.getLoc())
                    ||
                    (gate.getId() == Gate.WEST_GATE_RIGHT.getId() &&
                    gate.getLocation() == Gate.WEST_GATE_RIGHT.getLoc());
        }
    };
}
