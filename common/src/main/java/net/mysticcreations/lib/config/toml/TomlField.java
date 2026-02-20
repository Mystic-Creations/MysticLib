package net.mysticcreations.lib.config.toml;

import java.util.ArrayList;
import java.util.List;

public class TomlField<T extends TomlField<T>> extends TomlElement<T> {

    public String name;

    public TomlField(String name) {
        this.name = name;
    }

}
