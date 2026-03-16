package net.mysticcreations.lib.config.toml;

public class TomlBooleanField extends TomlField {
    public boolean value;

    public TomlBooleanField(String name, boolean value) {
        super(TomlDottedElementName.fromString(name));
        this.value = value;
    }

    public TomlBooleanField(TomlDottedElementName name, boolean value) {
        super(name);
        this.value = value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
