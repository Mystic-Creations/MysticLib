package net.mysticcreations.lib.config.fields;

public class FloatField extends ConfigField<Float> {
    float min;
    float max;
    double step;

    public FloatField(String fieldName, Float defaultValue) {
        super(Float.class, fieldName, defaultValue);
        this.min = Float.MIN_VALUE;
        this.max = Float.MAX_VALUE;
        this.step = 0.25;
    }
    public FloatField(String fieldName, Float defaultValue, float min, float max) {
        super(Float.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 0.25;
    }
    public FloatField(String fieldName, Float defaultValue, float min, float max, float step) {
        super(Float.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Float value) {
        return value >= this.min && value <= this.max;
    }
}
