package net.mysticcreations.lib.config.specification.annotations;

public @interface Comment {
    String value();
    //Dev note: keep it as "value" because as "comment" the annotation will require "@Comment(name = "COMMENT") "
}
