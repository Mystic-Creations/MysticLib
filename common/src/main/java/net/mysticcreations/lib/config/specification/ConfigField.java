package net.mysticcreations.lib.config.specification;

public abstract class ConfigField<T> {
    public String fieldName;
    public T value;
    public Class<T> type;

    public ConfigField(Class<T> type, T defaultValue) {
        this.type = type;
        value = defaultValue;
    }

    public boolean setValue(T value) {
        if (!validateValue(value)) {
            return false;
        }
        this.value = value;
        return true;
    }

    public abstract boolean validateValue(T value);

    public T getValue() {
        return value;
    }

    // Maybe validate values from previously saved configs?
    public void setFieldValue(Object value) {
        this.setValue(this.type.cast(value));
    }

}
