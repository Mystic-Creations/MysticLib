package net.mysticcreations.lib.config.specification.annotations;

public @interface OpenCat {
    String value();
    //Dev note: keep it as "value" because as "name" the annotation will require "@OpenCat(name = "CATEGORY") "
}
