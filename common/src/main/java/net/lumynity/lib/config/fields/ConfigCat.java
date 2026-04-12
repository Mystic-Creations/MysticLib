package net.lumynity.lib.config.fields;

import net.lumynity.lib.config.ConfigItem;

import java.util.ArrayList;
import java.util.List;

public class ConfigCat implements ConfigItem {

    public List<ConfigItem> items = new ArrayList<>();
    public String catName;
    public ConfigCat(String catName) {
        this.catName = catName;
    }

    public void addItem(ConfigItem item) {
        items.add(item);
    }

}
