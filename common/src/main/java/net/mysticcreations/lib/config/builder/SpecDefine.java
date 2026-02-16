package net.mysticcreations.lib.config.builder;

import java.util.function.Supplier;

public interface SpecDefine {
    SpecDefine openCat(String name);
    SpecDefine closeCat();

    Supplier<Boolean> create(String key, boolean defaultValue);
    Supplier<Integer> create(String key, int defaultValue, int min, int max);
    Supplier<Double> create(String key, double defaultValue, double min, double max);
    Supplier<String> create(String key, String defaultValue);

    SpecDefine describeCat(String comment);
    SpecDefine describe(String comment);

    SpecDefine gameRestart();
    SpecDefine noSync();

    SpecDefine clientOnly();
    SpecDefine serverOnly();

    void build();
}
