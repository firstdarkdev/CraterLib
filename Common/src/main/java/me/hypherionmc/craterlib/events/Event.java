package me.hypherionmc.craterlib.events;

/**
 * @author HypherionSA
 * @date 21/06/2022
 */
public class Event {
    private boolean cancelled;

    public boolean isCancellable() {
        return false;
    }

    public void setCancelled(boolean canceled) {
        if (!this.isCancellable()) {
            throw new RuntimeException("Cannot cancel event " + this);
        }
        this.cancelled = canceled;
    }


    public boolean isCancelled() {
        return cancelled;
    }
}
