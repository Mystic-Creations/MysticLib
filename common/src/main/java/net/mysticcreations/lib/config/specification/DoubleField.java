package net.mysticcreations.lib.config.specification;

public class DoubleField extends ConfigField<Double>{

    double min;
    double max;
    double step;

    public DoubleField(Double defaultValue) {
        super(Double.class, defaultValue);
    }

    @Override
    public boolean validateValue(Double value) {
        return value >= this.min && value <= this.max;
    }
}
