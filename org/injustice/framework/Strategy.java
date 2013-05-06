package org.injustice.framework;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 06/05/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public abstract class Strategy implements Condition {
    private Condition condition;
    private Task task;
    private boolean running;

    public Strategy() {
        this.condition = this;
        if (this instanceof Task) {
            this.task = (Task) this;
        } else {
            throw new IllegalArgumentException("Doesn't implement Task");
        }
    }

    public Strategy(final Condition cond, final Task task) {
        this.condition = cond;
        this.task = task;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public Task getTask() {
        return this.task;
    }

    public boolean isRunning() {
        return running;
    }

    public void started() {
        running = true;
    }

    public void finished() {
        running = false;
    }
}
