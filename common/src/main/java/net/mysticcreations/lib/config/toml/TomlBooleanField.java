package net.mysticcreations.lib.config.toml;

public class TomlBooleanField extends TomlField {


    boolean value;

    public TomlBooleanField(String name, boolean value) {
        super(name);
    }
}
