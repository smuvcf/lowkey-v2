package net.minecraftforge.client.event.sound.server;

import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;

public class ClickGUI extends Module
{
    public ClickGUI() {
        super("Click GUI", 54, Category.R);
    }
    
    @Override
    public void onEnable() {
        if (Wrapper.getPlayer() != null && Wrapper.getMinecraft().currentScreen == null) {
            Wrapper.getMinecraft().displayGuiScreen((GuiScreen)kmethMain.getClickGUI());
            this.setState(false);
        }
    }
}
