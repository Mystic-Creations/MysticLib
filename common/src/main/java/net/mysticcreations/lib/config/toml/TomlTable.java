package net.mysticcreations.lib.config.toml;

public class TomlTable extends TomlTableBase {

    public String name;

    public TomlTable(String name) {
        super(name);
    }

    public int getTableDepth() {
        int depth = name.split("\\.").length - 1;
        if (depth < 1) return 0;
        return depth;
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }

}
