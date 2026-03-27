package net.mysticcreations.lib.config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.screen.ConfigScreenNetwork;
import net.mysticcreations.lib.config.screen.client.ConfigScreen;
import net.mysticcreations.lib.util.CommandUtil;

public class ConfigCommand {

    private static LiteralArgumentBuilder<CommandSourceStack> buildCommand() {

        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("mysticlib_config").requires(s -> s.hasPermission(4));

        for (ConfigSerializer configSerializer : ConfigInitializer.definitions.values()) {
            ConfigDefinition definition = configSerializer.config;


            builder.then(Commands.literal(definition.id.toString()).executes((s) -> {
                if (!CommandUtil.checkIfPlayerExecuted(s)) {
                    return 1;
                }

                ServerPlayer player = s.getSource().getPlayer();

                // yay here
                MysticLib.LOGGER.info("OwO");

                ConfigScreenNetwork.sendConfigScreenPacket(player, configSerializer);

                return 1;
            }));
        }

        return builder;
    }
    public static void register() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            dispatcher.register(buildCommand());
        });}
}
