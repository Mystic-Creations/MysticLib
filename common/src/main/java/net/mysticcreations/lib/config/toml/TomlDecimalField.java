package net.mysticcreations.lib.config.toml;

public class TomlDecimalField extends TomlField {

    public double value;
    public long exponent;
    public boolean hasExponent;

    public TomlDecimalField(String name, double value) {
        super(TomlDottedElementName.fromString(name));
        this.value = value;
        this.exponent = 0;
        this.hasExponent = false;
    }

    public TomlDecimalField(TomlDottedElementName name, double value) {
        super(name);
        this.value = value;
        this.exponent = 0;
        this.hasExponent = false;
    }

    public TomlDecimalField(String name, double value, long exponent) {
        super(TomlDottedElementName.fromString(name));
        this.value = value;
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public TomlDecimalField(TomlDottedElementName name, double value, long exponent) {
        super(name);
        this.value = value;
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public void setExponent(long exponent) {
        this.exponent = exponent;
        this.hasExponent = true;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
