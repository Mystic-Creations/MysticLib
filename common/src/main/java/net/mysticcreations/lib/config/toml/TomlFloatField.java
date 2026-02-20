package net.mysticcreations.lib.config.toml;

public class TomlFloatField extends TomlField<TomlFloatField> {

    public float value;

    public TomlFloatField(String name, float value) {
        super(name);
        this.value = value;
    }

    public TomlFloatField setValue(float value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
