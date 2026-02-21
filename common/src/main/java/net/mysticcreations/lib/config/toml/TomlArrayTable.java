package net.mysticcreations.lib.config.toml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TomlArrayTable extends TomlTableBase<TomlArrayTable> {

    public TomlArrayTable(String name) {
        super(new String[]{name});
    }
    public TomlArrayTable(String[] name) {
        super(name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[[");
        for (int i = 0; i < name.length; i++) {
            String item = name[i];
            Pattern pattern = Pattern.compile("([A-Za-z0-9_-]+)");
            Matcher matcher = pattern.matcher(item);

            if (matcher.matches()) {
                builder.append(item);
            } else {
                builder.append("\"").append(TomlUtils.escapeTomlString(item)).append("\"");
            }
            if (i != (name.length - 1)) {
                builder.append(".");
            }
        }
        builder.append("]]");
        return builder.toString();
    }
}