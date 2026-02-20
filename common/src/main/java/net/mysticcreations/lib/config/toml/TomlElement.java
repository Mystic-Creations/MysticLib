package net.mysticcreations.lib.config.toml;

import java.util.ArrayList;
import java.util.List;

public abstract class TomlElement<T extends TomlElement<T>> {

    public String inlineComment;
    public List<String> headerComments;

    protected TomlElement() {
        this.inlineComment = null;
        this.headerComments = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    public T setInlineComment(String comment) {
        this.inlineComment = comment;
        return self();
    }

    public T setHeaderComments(List<String> headerComments) {
        this.headerComments = headerComments;
        return self();
    }

    public T addHeaderComment(String comment) {
        this.headerComments.add(comment);
        return self();
    }
}