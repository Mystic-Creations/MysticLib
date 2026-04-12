package net.lumynity.lib.config.toml;

public class TomlIntegerField extends TomlField {

    public long value;
    public long exponent;
    public boolean hasExponent;

    public TomlIntegerField(String name, long value) {
        super(TomlDottedElementName.fromString(name));
        this.value = value;
        this.exponent = 0;
        this.hasExponent = false;
    }

    public TomlIntegerField(TomlDottedElementName name, long value) {
        super(name);
        this.value = value;
        this.exponent = 0;
        this.hasExponent = false;
    }

    public TomlIntegerField(String name, long value, long exponent) {
        super(TomlDottedElementName.fromString(name));
        this.value = value;
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public TomlIntegerField(TomlDottedElementName name, long value, long exponent) {
        super(name);
        this.value = value;
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public void setExponent(long exponent) {
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
