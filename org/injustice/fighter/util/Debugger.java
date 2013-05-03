package org.injustice.fighter.util;

import org.powerbot.core.script.job.LoopTask;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 03/05/13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class Debugger extends LoopTask {
    @Override
    public int loop() {
        System.out.println("--- [DEBUG] --- " + Var.status + " --- " +
                Var.runTime.toElapsedString());
        return 1000;
    }
}
