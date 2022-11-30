package me.hypherionmc.craterlib.events;

/**
 * @author HypherionSA
 * @date 21/06/2022
 * CraterLib universal event
 */
public class Event {
    private boolean cancelled;

    /**
     * Can the event be cancelled. Override this method to control the behaviour
     * @return
     */
    public boolean isCancellable() {
        return false;
    }

    /**
     * Cancel the event
     * @param canceled True to cancel the event
     */
    public void setCancelled(boolean canceled) {
        if (!this.isCancellable()) {
            throw new RuntimeException("Cannot cancel event " + this);
        }
        this.cancelled = canceled;
    }

    /**
     * Used Internally to check if the event is cancelled
     * @return
     */
    public boolean isCancelled() {
        return cancelled;
    }
}
