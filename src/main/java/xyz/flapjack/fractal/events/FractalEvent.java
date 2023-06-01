package xyz.flapjack.fractal.events;

/* Weave. */
import net.weavemc.loader.api.event.Event;

public class FractalEvent extends Event {
    public boolean isCancelled = false;
    public boolean isRecognised = true;

    /**
     * Sets an event cancel state.
     * @param isCancelled argument.
     */
    public void setState(final boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
