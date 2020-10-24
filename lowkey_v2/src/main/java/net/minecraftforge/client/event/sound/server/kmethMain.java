package net.minecraftforge.client.event.sound.server;

import java.awt.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.client.event.sound.server.Timer;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.sound.server.*;

@Mod(modid = "", acceptedMinecraftVersions = "")
public class kmethMain
{
    private static ArrayList<Module> modules;
    private static GUI clickGUI;
    public static Color color;
    
    @Mod.EventHandler
    public void fmlInitialization(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        FMLCommonHandler.instance().bus().register((Object)this);
        kmethMain.clickGUI = new GUI();
    }
    
    @SubscribeEvent
    public void keyInput(final InputEvent.KeyInputEvent event) {
        if (Wrapper.getPlayer() != null) {
            if (!Keyboard.getEventKeyState()) {
                return;
            }
            for (final Module mod : getModules()) {
                if (mod.getKey() == Keyboard.getEventKey()) {
                    mod.setState(!mod.getState());
                }
            }
        }
    }
    
    public static ArrayList<Module> getModules() {
        return kmethMain.modules;
    }
    
    public static GUI getClickGUI() {
        return kmethMain.clickGUI;
    }
    
    static {
        (kmethMain.modules = new ArrayList<Module>()).add(new AimAssist());
        kmethMain.modules.add(new AutoClicker());
        kmethMain.modules.add(new Reach());
        kmethMain.modules.add(new Velocity());
        kmethMain.modules.add(new ClickGUI());
        kmethMain.modules.add(new SelfDestruct());
        kmethMain.modules.add(new Nametags());
        kmethMain.modules.add(new Antibot());
        kmethMain.modules.add(new Timer());
        kmethMain.modules.add(new FP());
        kmethMain.modules.add(new Hitboxes());
        kmethMain.modules.add(new colors());
        kmethMain.color = new Color(58, 58, 58);
    }
}
