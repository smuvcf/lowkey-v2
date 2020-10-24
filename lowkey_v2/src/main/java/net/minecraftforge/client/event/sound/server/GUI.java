package net.minecraftforge.client.event.sound.server;

import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.client.event.sound.server.Component;
import net.minecraftforge.client.event.sound.server.Frame;

import java.util.*;

public class GUI extends GuiScreen
{
    public ArrayList<Frame> frames;
    
    public GUI() {
        this.frames = new ArrayList<Frame>();
        int frameX = 5;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category category = values[i];
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            this.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        final FontRenderer fontRenderer = this.fontRendererObj;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glScaled(1.5, 1.5, 1.5);
        final int posX = 0;
        this.mc.fontRendererObj.drawStringWithShadow("L", 5.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4850.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("e", 11.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4750.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("a", 17.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4650.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("k", 23.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4550.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("e", 28.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4450.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("d", 34.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4350.0f, 0.7f, 0.7f));
        GL11.glScaled(0.6666666666666666, 0.6666666666666666, 0.6666666666666666);
        this.mc.fontRendererObj.drawStringWithShadow("v", 65.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4250.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("2", 72.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4150.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow(".", 78.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4100.0f, 0.7f, 0.7f));
        this.mc.fontRendererObj.drawStringWithShadow("0", 80.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4000.0f, 0.7f, 0.7f));
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        for (final Frame frame : this.frames) {
            frame.renderFrame();
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Frame frame : this.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Frame frame : this.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : this.frames) {
            frame.setDrag(false);
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
}
