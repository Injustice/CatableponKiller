package org.injustice.fighter;

import org.injustice.fighter.node.Attacker;
import org.injustice.fighter.node.Eater;
import org.injustice.fighter.node.abilities.AbilityHandler;
import org.injustice.fighter.node.failsafes.FailsafeDoor;
import org.injustice.fighter.node.failsafes.FailsafeLogout;
import org.injustice.fighter.node.loot.CharmLooter;
import org.injustice.fighter.node.loot.ClueScrollLooter;
import org.injustice.fighter.ui.FighterPaint;
import org.injustice.fighter.util.Condition;
import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.framework.Strategy;
import org.injustice.framework.StrategyHandler;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
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
@Manifest(authors = "Injustice", description = "Kills Catablepons in SoS", name = "CatableMassacreL", version = 2.0)
public class Fighter extends ActiveScript implements PaintListener, MessageListener {
 /*   private final Node[] nodes = { new RejuvenateUser(), new Attacker(),
            new CharmLooter(), new Eater(), new FailsafeDoor(), new AbilityHandler()};
 */

    StrategyHandler s = new StrategyHandler();
    private int getExp(int skill) {
        return Skills.getExperience(skill);
    }

    private void setTotalExp() {
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

    private void setTotalLvl() {
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
        s.provide(new AbilityHandler());
        s.provide(new FailsafeDoor());
        s.provide(new FailsafeLogout());
        s.provide(new CharmLooter());
        s.provide(new ClueScrollLooter());
        s.provide(new Attacker());
        s.provide(new Eater());


        Mouse.setSpeed(Mouse.Speed.FAST);
        Var.status = "[ONSTART] Starting...";
        System.out.println("------------------------------------");
        System.out.println("Welcome to Injustice's CatableKiller");
        System.out.println("------------------------------------");
        Util.debug();
        if (Util.isReady()) {
            Var.startTile = Players.getLocal().getLocation();
            setTotalExp();
            setTotalLvl();
            Var.startConstitutionExp = getExp(Skills.CONSTITUTION);
            Var.startConstitutionLvl = getLvl(Skills.CONSTITUTION);
        } else {
            Util.waitFor(new Condition() {
                @Override
                public boolean validate() {
                    Var.status = "[STARTUP] Not logged in";
                    return Util.isReady();
                }
            }, 5000);
        }
    }

    @Override
    public void onStop() {
        Util.ExpStats e = new Util.ExpStats();
        Var.status = "[STOPPING]";
        int expGained = e.getTotalExp() - Var.totalExp;
        int killed = expGained / 133;
        int constExp = e.getExp(Skills.CONSTITUTION) - Var.startConstitutionExp;
        Util.debug();
        System.out.println("---------------------------------");
        System.out.println("--------------STATS--------------");
        System.out.println("Killed: " + (expGained / 133));
        System.out.println("Killed PH: " + (int) (killed * 3600000d / Var.runTime.getElapsed()));
        System.out.println("Exp gained: " + expGained);
        System.out.println("Exp PH: " + (int) (expGained * 3600000d / Var.runTime.getElapsed()));
        System.out.println("Const exp: " + constExp);
        System.out.println("Const PH: " + (int) (constExp * 3600000d / Var.runTime.getElapsed()));
        System.out.println("Levels: " + (e.getTotalLvl() - Var.totalLvl));
//        System.out.println("Charms: " + Var.charmsLooted);
//        System.out.println("Rejuvs: " + Var.rejuvs);
        System.out.println("Runtime: " + Var.runTime.toElapsedString());
        System.out.println("--------------STATS--------------");
        System.out.println("---------------------------------");
        System.out.println("Thanks for using my script!");
        System.out.println("Please post proggies!");
    }


    @Override
    public int loop() {
        //       for(Node n : nodes) {
        for (Strategy strat : s.getStrategies()) {
            if (strat.activate() && Util.isReady() &&
                    Var.totalExp != 0 && Var.totalLvl != 0 && // paint counters
                    Var.startConstitutionExp != 0 &&
                    Var.startConstitutionLvl != 0 &&
                    (!strat.isRunning() && strat.getCondition().activate())) {
                try {
                    s.executeStrategy(strat);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Var.status = "[NPE] Node";
                    Util.debug();
                }
            } else if (Var.totalLvl == 0) {
                setTotalLvl();;
            } else if (Var.totalExp == 0) {
                setTotalExp();
            } else if (Var.startConstitutionExp == 0) {
                Var.startConstitutionExp = getExp(Skills.CONSTITUTION);
            } else if (Var.startConstitutionLvl == 0) {
                Var.startConstitutionLvl = getExp(Skills.CONSTITUTION);
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
