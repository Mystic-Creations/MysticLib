package net.mysticcreations.lib.config.toml;

import java.util.ArrayList;
import java.util.List;

public class TomlField<T extends TomlField<T>> extends TomlElement<T> {

    public TomlDottedElementName name;

    public TomlField(TomlDottedElementName name) {
        this.name = name;
    }

}
