package org.injustice.framework;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class StrategyHandler {
    private static final List<Strategy> strategies = new LinkedList<Strategy>();

    private int corePoolSize = 4;
    private int maxPoolSize = 4;
    private long keepAliveTime = 5000;
    private ExecutorService service = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>()
    );


    public void provide(final Strategy strategy) {
        strategies.add(strategy);
    }

    public void executeStrategy(Strategy s) {
        StrategyThread thread = new StrategyThread(s);
        service.execute(thread);
    }

    public List<Strategy> getStrategies() {
        return strategies;
    }
}
