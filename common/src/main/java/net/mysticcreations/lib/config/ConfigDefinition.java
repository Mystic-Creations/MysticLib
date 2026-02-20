package net.mysticcreations.lib.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.config.fields.ConfigCat;
import net.mysticcreations.lib.config.fields.ConfigComment;
import net.mysticcreations.lib.config.fields.ConfigField;

import java.util.*;

public abstract class ConfigDefinition {
    public final ResourceLocation id;
    public final FileTypes configType;

    public List<ConfigItem> items;
    private Stack<ConfigCat> cats = new Stack<>();

    public ConfigDefinition(ResourceLocation id, FileTypes type) {
        this.id = id;
        this.configType = type;
        items = new ArrayList<>();
    }

    protected void addField(ConfigField<?> item) {
        if (cats.empty()) {
            items.add(item);
        } else {
            ConfigCat cat = cats.peek();
            cat.addItem(item);
        }
    }

    protected void openCat(String catName) {
        ConfigCat cat = new ConfigCat(catName);
        cats.push(cat);
    }

    protected void closeCat() {
        ConfigCat poppedCat = cats.pop();
        if (cats.empty()) {
            items.add(poppedCat);
        } else {
            ConfigCat cat = cats.peek();
            cat.addItem(poppedCat);
        }
    }


    public FileTypes getConfigType() {
        return configType;
    }
}
