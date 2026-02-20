package net.mysticcreations.lib.config.toml;

import java.util.ArrayList;
import java.util.List;

public abstract class TomlElement {

    public String inlineComment;
    public List<String> headerComments;

    protected TomlElement() {
        this.inlineComment = null;
        this.headerComments = new ArrayList<>();
    }

    public TomlElement setInlineComment(String comment) {
        this.inlineComment = comment;
        return this;
    }

    public TomlElement setHeaderComments(List<String> headerComments) {
        this.headerComments = headerComments;
        return this;
    }

    public TomlElement addHeaderComment(String comment) {
        this.headerComments.add(comment);
        return this;
    }
}
