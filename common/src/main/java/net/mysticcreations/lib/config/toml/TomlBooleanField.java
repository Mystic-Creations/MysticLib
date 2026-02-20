package net.mysticcreations.lib.config.toml;

public class TomlBooleanField extends TomlField<TomlBooleanField> {


    boolean value;

    public TomlBooleanField(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public TomlBooleanField setValue(boolean value) {
        this.value = value;
        return this;
    }

    public TomlBooleanField toggle() {
        this.value = !this.value;
        return this;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
