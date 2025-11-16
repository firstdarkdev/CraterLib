package com.hypherionmc.craterlib.core.event;

import com.hypherionmc.craterlib.core.event.annot.Cancellable;
import com.hypherionmc.craterlib.core.event.exception.CraterEventCancellationException;

public class CraterEvent {

    private boolean canceled = false;

    private boolean canCancel() {
        return this.getClass().isAnnotationPresent(Cancellable.class);
    }

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