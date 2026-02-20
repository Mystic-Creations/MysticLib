package net.mysticcreations.lib.config.toml;

public abstract class TomlTableBase extends TomlElement {

    public String name;

    public TomlTableBase(String name) {
        this.name = name;
    }

    public int getTableDepth() {
        int depth = name.split("\\.").length - 1;
        if (depth < 1) return 0;
        return depth;
    }
}