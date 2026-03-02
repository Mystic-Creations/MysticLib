package net.mysticcreations.lib.config.toml;

public class TomlStringField extends TomlField<TomlStringField> {

    public String value;
    public TomlStringType type;

    public TomlStringField(String name, String value) {
        super(TomlDottedElementName.fromString(name));
        this.type = TomlStringType.DOUBLE;
        this.value = value;
    }

    public TomlStringField(TomlDottedElementName name, String value, TomlStringType type) {
        super(name);
        this.value = value;
        this.type = type;
    }

    public TomlStringField setValue(String value) {
        this.value = value;
        return this;
    }

    public TomlStringField setStringType(TomlStringType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        switch (type) {
            case DOUBLE -> {
                return name + " = \"" + TomlStringUtils.escapeTomlString(value) + "\"";
            }
            case LITERAL -> {
                return name + " = '" + value + "'";
            }
            case TRIPLE_DOUBLE -> {
                return name + " = \"\"\"" + value + "\"\"\"";
            }
            case NO_QUOTE -> {
                return "";
            }
        }
        return "";
    }

}
