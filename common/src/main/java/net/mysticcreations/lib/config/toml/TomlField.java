package net.mysticcreations.lib.config.toml;

import java.util.ArrayList;
import java.util.List;

public class TomlField extends TomlElement {

    public TomlDottedElementName name;

    public TomlField(TomlDottedElementName name) {
        super();
        this.name = name;
    }

}
