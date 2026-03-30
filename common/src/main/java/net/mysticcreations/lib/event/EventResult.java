package net.mysticcreations.lib.event;

public class EventResult {

    public boolean cancel;

    private EventResult(boolean cancel) {
        this.cancel = cancel;
    }

    public EventResult pass() {
        return new EventResult(false);
    }

    public EventResult cancel() {
        return new EventResult(true);
    }

}
