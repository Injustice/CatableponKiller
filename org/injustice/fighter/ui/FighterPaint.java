package org.injustice.fighter.ui;

import org.injustice.fighter.util.Util;
import org.injustice.fighter.util.Var;
import org.injustice.fighter.util.enums.Loot;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import sk.action.ActionBar;
import sk.action.BarNode;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 10/04/13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class FighterPaint {
    static Font font1 = new Font("Tahoma", 0, 15);
    static Font font2 = new Font("Tahoma", 0, 9);
    static Font font3 = new Font("Tahoma", 0, 11);
    static Color grey = new Color(0, 0, 0, 100);
    static BasicStroke stroke1 = new BasicStroke(1);
    static Util.ExpStats exp = new Util.ExpStats();

    public static void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        Point loc = new Point(10, 220);
        Color green = new Color(20, 126, 59, 150);
        int expGained = exp.getTotalExp() - Var.totalExp;
        int expHour = (int) (expGained * 3600000d / Var.runTime.getElapsed());
        int kills = expGained / 133;
        int killsPH = (int) (kills * 3600000d / Var.runTime.getElapsed());
        int constExp = exp.getExp(Skills.CONSTITUTION) - Var.startConstitutionExp;
        int constPH = (int) (constExp * 3600000d / Var.runTime.getElapsed());

        g.setStroke(stroke1);
        g.setColor(grey);
        g.fillRoundRect(5, 178, 200, 120, 7, 7);
        g.setColor(Color.GREEN);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 178, 200, 120, 7, 7);
        g.drawRect(5, 178, 200, 21);
        g.setFont(font1);

        g.drawString("Injustice's Catablepon Killer", 8, 196);
        g.setFont(font2);
        g.drawString("v" + Var.VERSION, 185, 196);
        g.setFont(font3);
        g.drawString("Kills: " + Util.format(kills, 2), loc.x, loc.y);
        g.drawString("Kills PH: " + Util.format(killsPH, 2), loc.x + 100, loc.y);
        g.drawString("Exp gain: " + Util.format(expGained, 2), loc.x, loc.y + 15);
        g.drawString("Exp PH: " + Util.format(expHour, 2), loc.x + 100, loc.y + 15);
        g.drawString("Const Exp: " + Util.format(constExp, 2), loc.x, loc.y + 30);
        g.drawString("Const PH: " + Util.format(constPH, 2), loc.x + 100, loc.y + 30);
        g.drawString("Levels: " + (exp.getTotalLvl() - Var.totalLvl), loc.x, loc.y + 45);
        g.drawString("Charms picked: " + Util.format(Var.charmsLooted, 2), loc.x + 100, loc.y + 45);
        g.drawString("Run time: " + Var.runTime.toElapsedString(), loc.x, loc.y + 60);
        g.drawString("Rejuvenates: " + Var.rejuvs, loc.x + 100, loc.y + 60);
        g.drawString("Status: " + Var.status, loc.x, loc.y + 75);
        g.setColor(Color.RED);
        g.setFont(font1);
        g.drawString("" + Players.getLocal().getHealthPercent(),
                Players.getLocal().getCentralPoint().x,
                Players.getLocal().getCentralPoint().y);
        g.setColor(green);
        drawMouse(g);
        drawTiles(g);
    }

    /**
     * @param g1 Graphics
     * @author not me, will find - phl0w/pxpc2, not sure
     */
    private static void drawMouse(Graphics g1) {
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        Color outerCircle = gradient[0];
        g1.setColor(gradient[0]);
        int circleRadius = 7;
        int circleDiameter = circleRadius * 2;
        g1.drawLine(p.x + circleRadius, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x - 2000, p.y, p.x - circleRadius, p.y);
// Vertical
        g1.drawLine(p.x, p.y + circleRadius, p.x, p.y + 2000);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y - circleRadius);
        for (int r = gradient.length - 1; r > 0; r--) {
            int steps = 200 / ((gradient.length - 1) * 2);
            for (int i = steps; i > 0; i--) {
                float ratio = (float) i / (float) steps;
                int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
                        .getRed() * (1 - ratio));
                int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
                        .getGreen() * (1 - ratio));
                int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
                        .getBlue() * (1 - ratio));
                Color stepColor = new Color(red, green, blue);
                g1.setColor(stepColor);
// Horizontal
                g1.drawLine(p.x + circleRadius, p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        - circleRadius, p.y);
// Vertical
                g1.drawLine(p.x, p.y + circleRadius, p.x, p.y
                        + ((i * 5) + (100 * r)));
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        - circleRadius);
            }
        }
        g1.setColor(outerCircle);
        final long mpt = System.currentTimeMillis() - Mouse.getPressTime();
        if (Mouse.getPressTime() == -1 || mpt >= 200) {
            g1.drawOval(p.x - circleRadius / 3, p.y - circleRadius / 3,
                    circleDiameter / 3, circleDiameter / 3);
        }
        if (mpt < 200) {
            g1.drawLine(p.x - circleRadius, p.y + circleRadius, p.x
                    + circleRadius, p.y - circleRadius);
            g1.drawLine(p.x - circleRadius, p.y - circleRadius, p.x
                    + circleRadius, p.y + circleRadius);
        }
        g1.setColor(outerCircle);
        g1.drawOval(p.x - circleRadius, p.y - circleRadius, circleDiameter,
                circleDiameter);
    }

    private static void drawTiles(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        final NPC[] catablepons = NPCs.getLoaded(Var.CATABLEPON_ID);
        NPC current = (NPC) Players.getLocal().getInteracting();
        final GroundItem charm = GroundItems.getNearest(Loot.ALL_CHARMS.getIds());
        for (Tile t : Var.CATABLEPON_ROOM.getTileArray()) {
            if (Var.CATABLEPON_ROOM.contains(t) && t.isOnMap() && catablepons != null) {
                for (NPC n : catablepons) {
                    if (n != null) {
                        if (Var.CATABLEPON_ROOM.contains(n)) {
                            Tile tile = n.getLocation();
                            g.setColor(Color.GREEN);
                            tile.draw(g);
                        } else {
                            g.setColor(Color.RED);
                            n.getLocation().draw(g);
                        }
                    }
                }
                if (current != null) {
                    g.setColor(Color.CYAN);
                    current.getLocation().draw(g);
                }
            }
            if (charm != null) {
                g.setColor(Color.WHITE);
                charm.getLocation().draw(g);
            }
        }
        g.setColor(Color.BLACK);
        Players.getLocal().getLocation().draw(g);
        for (BarNode b : ActionBar.getAllNodes()) {
            if (b != null && b.canUse()) {
                g.setColor(Color.GREEN);
                ActionBar.SlotData.getMainChild(b.getSlot()).draw(g);
            } else if (b != null) {
                g.setColor(Color.RED);
                ActionBar.SlotData.getMainChild(b.getSlot()).draw(g);
            }
        }
    }
}
