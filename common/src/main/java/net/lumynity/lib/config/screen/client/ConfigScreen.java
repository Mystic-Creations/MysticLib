package net.lumynity.lib.config.screen.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.lumynity.lib.config.toml.TomlElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class ConfigScreen extends Screen {

    private static Font font = Minecraft.getInstance().font;

    public ConfigScreen(List<TomlElement> elements) {
        super(Component.literal("skbii"));
    }

    @Override
    protected void init() {
        super.init();
    }
    @Override
    public void renderBackground(@NotNull GuiGraphics gui) {

        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.5f);

    }
    @Override
    public void render(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {

        super.render(gui, mouseX, mouseY, partialTicks);

        gui.drawString(font, "WE ARE CHARLIE", 100, 100, CommonColors.WHITE);
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_ESCAPE) {
            return true;
        }
        return false;
    }

}
