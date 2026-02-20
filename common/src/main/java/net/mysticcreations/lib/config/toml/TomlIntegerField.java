package net.mysticcreations.lib.config.toml;

public class TomlIntegerField extends TomlField {

    public int value;

    public TomlIntegerField(String name, int value) {
        super(name);
        this.value = value;
    }

    public TomlIntegerField setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
