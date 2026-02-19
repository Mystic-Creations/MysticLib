package net.mysticcreations.lib.config.fields;

public class BooleanField extends ConfigField<Boolean> {
    public BooleanField(String fieldName, boolean value) {
        super(Boolean.class, fieldName, value);
        this.value = value;
    }

    @Override
    public boolean validateValue(Boolean value) {
        return true;
    }
}
