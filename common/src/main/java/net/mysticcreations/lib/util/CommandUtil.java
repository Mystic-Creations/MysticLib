package net.mysticcreations.lib.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;

public class CommandUtil {
//    //Replace "new" permission system with the good ol' numbers             //for when they change it
//    public static boolean hasPerms(CommandSourceStack source, int level) {
//        return source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(level)));
//    }

    //Prevents commands from being ran from server console
    public static void checkIfPlayerExecuted(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (!(source.getEntity() instanceof ServerPlayer)) {
            sendFail(source, "Failed to execute \"" + context.getInput() + "\" - Command must be ran by a player.");
        }
    }

    //Command success/fail response
    public static void sendOk(CommandSourceStack source, String message) {
        source.sendSuccess(() -> Component.literal(message), false);
    }
    public static void sendFail(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal(message));
    }
    //Send chat message to player/server
    public static void sendTo(ServerPlayer player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }
    public static void broadcastTo(LevelAccessor world, String message, boolean bypassHiddenChat) {
        world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(message), bypassHiddenChat);
    }
}
