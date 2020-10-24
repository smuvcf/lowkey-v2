package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Velocity extends Module
{
    public static NumberValue x;
    public static NumberValue y;
    public static NumberValue chance;
    public static Minecraft mc;
    public Random random;
    
    public Velocity() {
        super("Velocity", 0, Category.C);
        this.random = new Random();
        Velocity.mc = Minecraft.getMinecraft();
        Velocity.x = new NumberValue("Horizontal", 100.0, 0.0, 100.0);
        Velocity.y = new NumberValue("Vertical", 100.0, 0.0, 100.0);
        Velocity.chance = new NumberValue("Chance", 100.0, 0.0, 100.0);
        this.addValue(Velocity.x);
        this.addValue(Velocity.y);
        this.addValue(Velocity.chance);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent ee) {
        if (Velocity.mc.thePlayer == null) {
            return;
        }
        if (Velocity.mc.theWorld == null) {
            return;
        }
        Double random = Math.random();
        random *= 100.0;
        if (Velocity.mc.thePlayer.hurtTime == Velocity.mc.thePlayer.maxHurtTime && Velocity.mc.thePlayer.maxHurtTime > 0) {
            if (random < Velocity.chance.getValue()) {
                final EntityPlayerSP thePlayer8;
                final EntityPlayerSP entityPlayerSP2;
                final EntityPlayerSP thePlayer7 = entityPlayerSP2 = (thePlayer8 = Velocity.mc.thePlayer);
                entityPlayerSP2.motionX *= Velocity.x.getValue() / 100.0;
                final EntityPlayerSP thePlayer10;
                final EntityPlayerSP entityPlayerSP3;
                final EntityPlayerSP thePlayer9 = entityPlayerSP3 = (thePlayer10 = Velocity.mc.thePlayer);
                entityPlayerSP3.motionY *= Velocity.y.getValue() / 100.0;
                final EntityPlayerSP thePlayer11 = Velocity.mc.thePlayer;
                final EntityPlayerSP entityPlayerSP4;
                final EntityPlayerSP entityPlayerSP = entityPlayerSP4 = thePlayer8;
                entityPlayerSP4.motionZ *= Velocity.x.getValue() / 100.0;
            }
            else {
                final EntityPlayerSP thePlayer13;
                final EntityPlayerSP entityPlayerSP5;
                final EntityPlayerSP thePlayer12 = entityPlayerSP5 = (thePlayer13 = Velocity.mc.thePlayer);
                entityPlayerSP5.motionX *= 1.0;
                final EntityPlayerSP thePlayer15;
                final EntityPlayerSP entityPlayerSP6;
                final EntityPlayerSP thePlayer14 = entityPlayerSP6 = (thePlayer15 = Velocity.mc.thePlayer);
                entityPlayerSP6.motionZ *= 1.0;
                final EntityPlayerSP thePlayer17;
                final EntityPlayerSP entityPlayerSP7;
                final EntityPlayerSP thePlayer16 = entityPlayerSP7 = (thePlayer17 = Velocity.mc.thePlayer);
                entityPlayerSP7.motionY *= 1.0;
            }
        }
    }
}
