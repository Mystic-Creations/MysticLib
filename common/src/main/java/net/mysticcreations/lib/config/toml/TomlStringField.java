package net.mysticcreations.lib.config.toml;

public class TomlStringField extends TomlField {

    public String value;

    public TomlStringField(String name, String value) {
        super(name);
        this.value = value;
    }

    public TomlStringField setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return name + " = \"" + escapeTomlString(value) + "\"";
    }

    private static String escapeTomlString(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

}
