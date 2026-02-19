package net.mysticcreations.lib.config.fields;

public class IntegerField extends ConfigField<Integer> {
    public final int min;
    public final int max;
    public final int step;

    public IntegerField(String fieldName, Integer defaultValue) {
        super(Integer.class, fieldName, defaultValue);
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.step = 1;
    }
    public IntegerField(String fieldName, Integer defaultValue, int min, int max) {
        super(Integer.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 1;
    }
    public IntegerField(String fieldName, Integer defaultValue, int min, int max,  int step) {
        super(Integer.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Integer value) {
        return value >= this.min && value <= this.max;
    }
}
