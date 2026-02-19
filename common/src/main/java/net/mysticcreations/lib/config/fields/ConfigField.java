package net.mysticcreations.lib.config.fields;

import net.mysticcreations.lib.config.ConfigItem;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigField<T> implements ConfigItem {
    public final String fieldName;
    public T value;
    public final Class<T> type;
    public String inlineComment = null;
    public List<String> headerComments = new ArrayList<>();

    public ConfigField(Class<T> type, String fieldName, T defaultValue) {
        this.type = type;
        value = defaultValue;
        this.fieldName = fieldName;
    }

    public boolean setValue(T value) {
        if (!validateValue(value)) {
            return false;
        }
        this.value = value;
        return true;
    }

    public void setInlineComment(String comment) {
        this.inlineComment = comment;
    }

    public void addHeaderComment(String comment) {
        this.headerComments.add(comment);
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
