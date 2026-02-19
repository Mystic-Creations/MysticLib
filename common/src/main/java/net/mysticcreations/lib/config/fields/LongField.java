package net.mysticcreations.lib.config.fields;

public class LongField extends ConfigField<Long> {
    long min;
    long max;
    double step;

    public LongField(Long defaultValue) {
        super(Long.class, defaultValue);
        this.min = Long.MIN_VALUE;
        this.max = Long.MAX_VALUE;
        this.step = 0.25;
    }
    public LongField(Long defaultValue, long min, long max) {
        super(Long.class, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 0.25;
    }
    public LongField(Long defaultValue, long min, long max,  long step) {
        super(Long.class, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Long value) {
        return value >= this.min && value <= this.max;
    }
}
