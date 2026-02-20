package net.mysticcreations.lib.config.toml;

public class TomlArrayTable extends TomlTableBase<TomlArrayTable> {

    public TomlArrayTable(String name) {
        super(name);
    }


    @Override
    public String toString() {
        return "[[" + name + "]]";
    }

}