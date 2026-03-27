package net.mysticcreations.lib.config.screen;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.ConfigSerializer;
import net.mysticcreations.lib.config.screen.client.ConfigScreen;
import net.mysticcreations.lib.config.toml.TomlElement;
import net.mysticcreations.lib.config.toml.TomlParser;
import net.mysticcreations.lib.config.toml.TomlParsingException;

import java.util.Arrays;
import java.util.List;

public class ConfigScreenNetwork {

    public static ResourceLocation SEND_CONFIG_SCREEN_PACKET = new ResourceLocation(MysticLib.MODID, "send_config_screen_packet");
    public static ResourceLocation REQUEST_CONFIG_PACKET = new ResourceLocation(MysticLib.MODID, "request_config_packet");
    public static ResourceLocation MODIFY_VALUE_PACKET = new ResourceLocation(MysticLib.MODID, "modify_value_packet");

    public static void registerPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, SEND_CONFIG_SCREEN_PACKET, (buf, context) -> {
            // Logic
            String tomlString = buf.readUtf();

            MysticLib.LOGGER.info("UwU");
            try {
                MysticLib.LOGGER.info(tomlString);
                TomlParser parser = new TomlParser(tomlString);
                MysticLib.LOGGER.info("UwU3");
                List<TomlElement> tomlElements = parser.getElements();
                MysticLib.LOGGER.info("UwU4");
                // create a create a filled in config definition
                Minecraft.getInstance().setScreen(new ConfigScreen(tomlElements));
                MysticLib.LOGGER.info("UwU5");
            } catch (TomlParsingException e) {
                MysticLib.LOGGER.error("Error while receiving config screen contents");
                MysticLib.LOGGER.error(e.getStackTrace());
            }
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, REQUEST_CONFIG_PACKET, (buf, context) -> {
            Player player = context.getPlayer();

            ResourceLocation configId = ResourceLocation.tryParse(context.toString());
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, MODIFY_VALUE_PACKET, (buf, context) -> {
            Player player = context.getPlayer();

            String tomlString = buf.toString();
        });

    }

    public static void sendConfigScreenPacket(ServerPlayer player, ConfigSerializer serializer) {
        try {

            // tell them

            String tomlPacketContents = serializer.getPacketString();

            MysticLib.LOGGER.info(tomlPacketContents);

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBytes(tomlPacketContents.getBytes());

            NetworkManager.sendToPlayer(player, SEND_CONFIG_SCREEN_PACKET, buf);

        } catch (TomlParsingException e) {
            MysticLib.LOGGER.error("Error while receiving config screen contents");
            MysticLib.LOGGER.error(e.getStackTrace());
        }

    }

}
