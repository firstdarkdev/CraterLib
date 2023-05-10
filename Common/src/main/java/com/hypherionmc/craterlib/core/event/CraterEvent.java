package com.hypherionmc.craterlib.core.event;

import com.hypherionmc.craterlib.core.event.exception.CraterEventCancellationException;

public abstract class CraterEvent {

    private boolean canceled = false;

    public abstract boolean canCancel();

    public void cancelEvent() {
        try {
            if (!this.canCancel()) {
                throw new CraterEventCancellationException("Tried to cancel non-cancelable event: " + this.getClass().getName());
            }

            this.canceled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean wasCancelled() {
        return this.canceled;
    }

}
