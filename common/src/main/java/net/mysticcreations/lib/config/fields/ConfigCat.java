package net.mysticcreations.lib.config.fields;

import net.mysticcreations.lib.config.ConfigItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
