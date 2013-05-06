package org.injustice.framework;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class StrategyThread implements Runnable {
    Strategy strategy;

    public StrategyThread(Strategy s) {
        strategy = s;
    }

    @Override
    public void run() {
        strategy.started();
        strategy.getTask().execute();
        strategy.finished();
    }
}
