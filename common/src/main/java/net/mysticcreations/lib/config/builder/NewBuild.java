package net.mysticcreations.lib.config.builder;

import net.mysticcreations.lib.config.JsonBuilder;
import net.mysticcreations.lib.config.TomlBuilder;

public interface NewBuild {
    static SpecDefine newJsonConfig(String modId, String name, boolean makeOwnDirectory) {
        if (name != null && name.matches("[^\\\\/:*?\"<>|]")) {
            throw new IllegalArgumentException("File name can't include illegal characters.");
        } else {
            return new JsonBuilder(modId, name, makeOwnDirectory);
        }
    }

    static SpecDefine newTomlConfig(String modId, String name, boolean makeOwnDirectory) {
        if (name != null && name.matches("[^\\\\/:*?\"<>|]")) {
            throw new IllegalArgumentException("File name can't include illegal characters.");
        } else {
            return new TomlBuilder(modId, name, makeOwnDirectory);
        }
    }
}
