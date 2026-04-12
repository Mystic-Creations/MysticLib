package net.lumynity.lib.config.fields;

import net.lumynity.lib.config.ConfigItem;

public class ConfigComment implements ConfigItem {

    String comment;

    public ConfigComment(String comment) {
        this.comment = comment;
    }

}
