package net.mysticcreations.lib.config.specification;

public class IntegerField extends ConfigField<Integer> {

    public final int min;
    public final int max;
    public final int step;

    public IntegerField(Integer defaultValue) {
        super(Integer.class, defaultValue);
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.step = 1;
    }

    public IntegerField(Integer defaultValue, int min, int max) {
        super(Integer.class, defaultValue);
        this.min = min;
        this.max = max;
        this.step = 1;
    }
    public IntegerField(Integer defaultValue, int min, int max,  int step) {
        super(Integer.class, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public boolean validateValue(Integer value) {
        return value >= this.min && value <= this.max;
    }


    public boolean setValue(int value) {
        return super.setValue(value);
    }

}
