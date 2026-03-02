package net.mysticcreations.lib.config.toml;

public class TomlStringField extends TomlField<TomlStringField> {

    public String value;
    public TomlStringType type;

    public TomlStringField(String name, String value) {
        super(name);
        this.value = value;
        this.type = TomlStringType.DOUBLE;
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
