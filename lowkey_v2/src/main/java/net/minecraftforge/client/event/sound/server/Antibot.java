package net.minecraftforge.client.event.sound.server;

import net.minecraft.client.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

import java.util.*;

public class Antibot extends Module
{
    public static Minecraft mc;
    public static BooleanValue Hypixel;
    public static BooleanValue Mineplex;
    
    public Antibot() {
        super("Antibot", 0, Category.M);
        Antibot.mc = Minecraft.getMinecraft();
        Antibot.Hypixel = new BooleanValue("Hypixel", false);
        Antibot.Mineplex = new BooleanValue("Mineplex", false);
        this.addOption(Antibot.Hypixel);
        this.addOption(Antibot.Mineplex);
    }
    
    @Override
    public void update() {
        if (Antibot.mc.thePlayer != null && Antibot.mc.theWorld != null) {
            for (final Entity a : Antibot.mc.theWorld.loadedEntityList) {
                if (a instanceof EntityPlayer) {
                    final EntityPlayer b = (EntityPlayer)a;
                    if (b == Antibot.mc.thePlayer) {
                        continue;
                    }
                    if (!b.isInvisible()) {
                        continue;
                    }
                    if (b.ticksExisted <= 105) {
                        continue;
                    }
                    if (!b.isUser()) {
                        continue;
                    }
                    if (b.getName().endsWith("\u00ef¿½r")) {
                        continue;
                    }
                    Antibot.mc.theWorld.removeEntity((Entity)b);
                }
            }
        }
    }
}
