package net.lumynity.lib.util;

import net.minecraft.server.level.ServerLevel;

public class SoundUtil {
    public static void playOnClient() {

    }
    public static void playGlobal(ServerLevel level) {
        level.playSound();
    }
}
