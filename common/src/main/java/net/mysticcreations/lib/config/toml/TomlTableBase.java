package net.mysticcreations.lib.config.toml;

public abstract class TomlTableBase<T extends TomlTableBase<T>> extends TomlElement<T> {

    public String name;

    public TomlTableBase(String name) {
        if (name.contains("[") || name.contains("]")) {
            throw new IllegalArgumentException("Table names cannot contain [ or ] characters");
        }
        this.name = name;
    }

    public int getTableDepth() {
        int depth = name.split("\\.").length - 1;
        if (depth < 1) return 0;
        return depth;
    }
}