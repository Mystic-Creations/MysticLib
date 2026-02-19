package net.mysticcreations.lib.config.fields;

public class StringField extends ConfigField<String> {
    public StringField(String defaultValue) {
        super(String.class, defaultValue);
    }

    @Override
    public boolean validateValue(String value) {
        return value != null && !value.isEmpty();
        //Dev note: do we leave the !value.isEmpty() ?
        //someone might want their default string to be empty idk
    }
}
