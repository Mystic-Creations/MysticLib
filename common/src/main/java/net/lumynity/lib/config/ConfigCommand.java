package net.lumynity.lib.config;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.lumynity.lib.LumynLib;
import net.lumynity.lib.config.screen.ConfigScreenNetwork;
import net.lumynity.lib.util.CommandUtil;

public class ConfigCommand {

    private static LiteralArgumentBuilder<CommandSourceStack> buildCommand() {

        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("lumynlib_config").requires(s -> s.hasPermission(4));

        for (ConfigSerializer configSerializer : ConfigInitializer.definitions.values()) {
            ConfigDefinition definition = configSerializer.config;


            builder.then(Commands.literal(definition.id.toString()).executes((s) -> {
                if (!CommandUtil.checkIfPlayerExecuted(s)) {
                    return 1;
                }

                ServerPlayer player = s.getSource().getPlayer();

                // yay here
                LumynLib.LOGGER.info("OwO");

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
