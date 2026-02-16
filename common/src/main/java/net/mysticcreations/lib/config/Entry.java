package net.mysticcreations.lib.config;

import java.nio.ByteBuffer;

public interface Entry<V, S> {
    boolean serverOnly();
    boolean clientOnly();

    V defaultValue();
    boolean validateValue(V value);

    String getDesc();
    String getAvailableValuesNote();

    boolean shouldSync();
    boolean requiresRestart();

    S serialize(V value);
    V deserialize(S serialized);
    byte[] write(V value);
    V read(ByteBuffer buffer);
}
