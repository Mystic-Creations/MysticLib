package net.mysticcreations.lib.config.fields;

public class IntegerField extends ConfigField<Long> {
    long min;
    long max;
    long step;

    public IntegerField(String fieldName, Long defaultValue) {
        super(Long.class, fieldName, defaultValue);
        this.min = Long.MIN_VALUE;
        this.max = Long.MAX_VALUE;
        this.step = 1;
    }
    public IntegerField(String fieldName, Long defaultValue, long min, long max) {
        super(Long.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 1;
    }
    public IntegerField(String fieldName, Long defaultValue, long min, long max, long step) {
        super(Long.class, fieldName, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Long value) {
        return value >= this.min && value <= this.max;
    }
}
