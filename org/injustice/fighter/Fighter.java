package org.injustice.fighter;

import org.injustice.fighter.node.Attacker;
import org.injustice.fighter.node.Eater;
import org.injustice.fighter.node.abilities.AbilityHandler;
import org.injustice.fighter.node.abilities.RejuvenateUser;
import org.injustice.fighter.node.failsafes.FailsafeDoor;
import org.injustice.fighter.node.loot.CharmCounter;
import org.injustice.fighter.node.loot.CharmLooter;
import org.injustice.fighter.ui.FighterPaint;
import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Debugger;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 23/04/13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
@Manifest(authors = "Injustice", description = "Kills Catablepons in SoS", name = "CatableMassacre", version = 0.1)
public class Fighter extends ActiveScript implements PaintListener, MessageListener {
    private final Node[] nodes = { new RejuvenateUser(), new Attacker(),
            new CharmLooter(), new Eater(), new FailsafeDoor(), new AbilityHandler()};


    private int getExp(int skill) {
        return Skills.getExperience(skill);
    }

    private void initTotalExp() {
        Var.totalExp = getExp(Skills.ATTACK) +
                getExp(Skills.MAGIC) +
                getExp(Skills.DEFENSE) +
                getExp(Skills.CONSTITUTION) +
                getExp(Skills.STRENGTH) +
                getExp(Skills.RANGE) +
                getExp(Skills.MAGIC);
    }

    private int getLvl(int skill) {
        return Skills.getRealLevel(skill);
    }

    private void initTotalLvl() {
        Var.totalLvl = getLvl(Skills.ATTACK) +
                getLvl(Skills.MAGIC) +
                getLvl(Skills.DEFENSE) +
                getLvl(Skills.CONSTITUTION) +
                getLvl(Skills.STRENGTH) +
                getLvl(Skills.RANGE) +
                getLvl(Skills.MAGIC);
    }

    @Override
    public void onStart() {
        getContainer().submit(new CharmCounter());
        getContainer().submit(new Debugger());
        if (Game.isLoggedIn()) {
            Var.startTile = Players.getLocal().getLocation();
            initTotalExp();
            initTotalLvl();
            Var.startConstitutionExp = getExp(Skills.CONSTITUTION);
            Var.startConstitutionLvl = getLvl(Skills.CONSTITUTION);
        } else {
            Util.waitFor(new Condition() {
                @Override
                public boolean validate() {
                    Var.status = "[STARTUP] Not logged in";
                    return Game.isLoggedIn();
                }
            }, 5000);
        }
    }


    @Override
    public int loop() {
        // Debug
        for(Node n : nodes) {
            if (n.activate() && Util.isReady()) {
                n.execute();
            }
        }
        return 50;
    }

    @Override
    public void onRepaint(Graphics g) {
        FighterPaint.onRepaint(g);
    }

    @Override
    public void messageReceived(MessageEvent e) {
        String s = "reach";
        if (e.toString().contains(s)) {
            Var.cannotReach = true;
        }
    }
}
