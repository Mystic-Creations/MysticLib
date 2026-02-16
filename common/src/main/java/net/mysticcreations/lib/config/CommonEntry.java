package net.mysticcreations.lib.config;

public class CommonEntry<V, S> implements Entry<V, S> {
    protected final boolean serverOnly;
    protected final boolean clientOnly;
    protected final V defaultValue;
    protected final String description;
    protected final boolean shouldSync;
    protected final boolean requiresRestart;

    public CommonEntry(V defaultValue, boolean shouldSync, boolean requiresRestart, boolean clientOnly, boolean serverOnly, String description) {
        this.serverOnly = serverOnly;
        this.clientOnly = clientOnly;
        this.defaultValue = defaultValue;
        this.description = description;
        this.shouldSync = shouldSync;
        this.requiresRestart = requiresRestart;
    }

    public boolean serverOnly() {
        return this.serverOnly;
    }
    public boolean clientOnly() {
        return this.clientOnly;
    }

    public V defaultValue() {
        return this.defaultValue;
    }
    public String getDesc() {
        return this.description;
    }

    public boolean shouldSync() {
        return this.shouldSync;
    }
    public boolean requiresRestart() {
        return this.requiresRestart;
    }
}
