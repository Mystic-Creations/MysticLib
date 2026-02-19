package net.mysticcreations.lib.config.fields;

public class DoubleField extends ConfigField<Double> {
    double min;
    double max;
    double step;

    public DoubleField(String fieldName,Double defaultValue) {
        super(Double.class, fieldName, defaultValue);
        this.min = Double.MIN_VALUE;
        this.max = Double.MAX_VALUE;
        this.step = 0.25;
    }
    public DoubleField(String fieldName,Double defaultValue, double min, double max) {
        super(Double.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 0.25;
    }
    public DoubleField(String fieldName,Double defaultValue, double min, double max,  double step) {
        super(Double.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Double value) {
        return value >= this.min && value <= this.max;
    }
}
