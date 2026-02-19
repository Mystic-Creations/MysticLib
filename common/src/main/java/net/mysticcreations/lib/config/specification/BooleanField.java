package net.mysticcreations.lib.config.specification;

public class BooleanField extends ConfigField<Boolean> {
    public BooleanField(boolean value) {
        super(Boolean.class, value);
        this.value = value;
    }

    @Override
    public boolean validateValue(Boolean value) {
        return true;
    }
}
