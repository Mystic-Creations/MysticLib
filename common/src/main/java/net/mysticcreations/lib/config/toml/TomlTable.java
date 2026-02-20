package net.mysticcreations.lib.config.toml;

public class TomlTable extends TomlTableBase<TomlTable> {

    public String name;

    public TomlTable(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }

}
