package net.mysticcreations.lib.config.toml;

public abstract class TomlTableBase<T extends TomlTableBase<T>> extends TomlElement<T> {

    public TomlDottedElementName name;

    public TomlTableBase(TomlDottedElementName name) {
        this.name = name;
    }

    public int getTableDepth() {
        int depth = name.depth() - 1;
        if (depth < 1) return 0;
        return depth;
    }
}