package net.lumynity.lib.config.screen;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.lumynity.lib.LumynLib;
import net.lumynity.lib.config.ConfigSerializer;
import net.lumynity.lib.config.screen.client.ConfigScreen;
import net.lumynity.lib.config.toml.TomlElement;
import net.lumynity.lib.config.toml.TomlParser;
import net.lumynity.lib.config.toml.TomlParsingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConfigScreenNetwork {

    public static ResourceLocation SEND_CONFIG_SCREEN_PACKET = LumynLib.asResource("send_config_screen_packet");
    public static ResourceLocation REQUEST_CONFIG_PACKET = LumynLib.asResource("request_config_packet");
    public static ResourceLocation MODIFY_VALUE_PACKET = LumynLib.asResource("modify_value_packet");

    public static void registerPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, SEND_CONFIG_SCREEN_PACKET, (buf, context) -> {
            // Logic
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String tomlString = new String(bytes, StandardCharsets.UTF_8);

            LumynLib.LOGGER.info("UwU");
            try {
                LumynLib.LOGGER.info(tomlString);
                TomlParser parser = new TomlParser(tomlString);
                LumynLib.LOGGER.info("UwU3");
                List<TomlElement> tomlElements = parser.getElements();
                LumynLib.LOGGER.info("UwU4");
                // create a create a filled in config definition
                Minecraft.getInstance().setScreen(new ConfigScreen(tomlElements));
                LumynLib.LOGGER.info("UwU5");
            } catch (TomlParsingException e) {
                LumynLib.LOGGER.error("Error while receiving config screen contents");
                LumynLib.LOGGER.error(e.getStackTrace());
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

            LumynLib.LOGGER.info(tomlPacketContents);

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeBytes(tomlPacketContents.getBytes());

            NetworkManager.sendToPlayer(player, SEND_CONFIG_SCREEN_PACKET, buf);

        } catch (TomlParsingException e) {
            LumynLib.LOGGER.error("Error while receiving config screen contents");
            LumynLib.LOGGER.error(e.getStackTrace());
        }

    }

}
