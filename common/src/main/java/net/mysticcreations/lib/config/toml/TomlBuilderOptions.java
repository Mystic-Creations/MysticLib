package net.mysticcreations.lib.config.toml;

public class TomlBuilderOptions {

    public boolean indentTables;
    public int indentAmount;

    public TomlBuilderOptions() {
        this.indentTables = true;
        this.indentAmount = 1;
    }

    public TomlBuilderOptions indentTables(boolean indentTables) {
        this.indentTables = indentTables;
        return this;
    }

}
