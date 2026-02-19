package net.mysticcreations.lib.config.fields;

import net.mysticcreations.lib.config.ConfigItem;

public class ConfigComment implements ConfigItem {

    String comment;

    public ConfigComment(String comment) {
        this.comment = comment;
    }

}
