package net.lumynity.lib.config.toml;

public class TomlStringField extends TomlField {

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

    public void setValue(String value) {
        this.value = value;
    }

    public void setStringType(TomlStringType type) {
        this.type = type;
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
