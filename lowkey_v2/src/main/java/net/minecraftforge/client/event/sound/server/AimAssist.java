package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AimAssist extends Module
{
    public static NumberValue a;
    public static NumberValue b;
    public static NumberValue c;
    public static BooleanValue d;
    public static BooleanValue e;
    public static BooleanValue f;
    public static Minecraft mc;
    int tru;
    boolean tre;
    
    public AimAssist() {
        super("AimAssist", 0, Category.C);
        this.tru = 201;
        this.tre = false;
        AimAssist.mc = Minecraft.getMinecraft();
        AimAssist.a = new NumberValue("Speed", 1.0, 1.0, 10.0);
        AimAssist.b = new NumberValue("FOV", 47.0, 15.0, 180.0);
        AimAssist.c = new NumberValue("Distance", 4.5, 1.0, 10.0);
        AimAssist.d = new BooleanValue("Click", false);
        AimAssist.e = new BooleanValue("Weapon", false);
        this.addValue(AimAssist.a);
        this.addValue(AimAssist.b);
        this.addValue(AimAssist.c);
        this.addOption(AimAssist.d);
        this.addOption(AimAssist.e);
    }
    
    public Entity ent() {
        Entity en = null;
        int f = (int)AimAssist.b.getValue();
        if (AimAssist.mc.thePlayer.worldObj != null) {
            for (final Object o : AimAssist.mc.theWorld.loadedEntityList) {
                final Entity ent = (Entity)o;
                if (ent.isEntityAlive() && !ent.isInvisible() && ent != AimAssist.mc.thePlayer && AimAssist.mc.thePlayer.getDistanceToEntity(ent) < (float)AimAssist.c.getValue() && ent instanceof EntityLivingBase && AimAssist.mc.thePlayer.canEntityBeSeen(ent) && isFovLargeEnough(ent, f)) {
                    en = ent;
                    f = (int)entityPosCompare(ent);
                }
            }
        }
        return en;
    }
    
    public boolean isOnSameTeam(final Entity ent) {
        return !(ent instanceof EntityLivingBase) || ((EntityLivingBase)ent).getTeam() == null || !((EntityLivingBase)ent).isOnSameTeam((EntityLivingBase)AimAssist.mc.thePlayer);
    }
    
    public static float rotationUntilTarget(final Entity ent) {
        if (ent != null) {
            final double x = ent.posX - AimAssist.mc.thePlayer.posX;
            final double y = ent.posY - AimAssist.mc.thePlayer.posY;
            final double z = ent.posZ - AimAssist.mc.thePlayer.posZ;
            double yaw = Math.atan2(x, z) * 57.29577951308232;
            yaw = -yaw;
            double pitch = Math.asin(y / Math.sqrt(x * x + y * y + z * z)) * 57.29577951308232;
            pitch = -pitch;
            return (float)yaw;
        }
        return -1.0f;
    }
    
    public static double entityPosCompare(final Entity ent) {
        return ((AimAssist.mc.thePlayer.rotationYaw - rotationUntilTarget(ent)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static boolean isFovLargeEnough(final Entity en, float a) {
        a *= 0.5;
        final double v = ((AimAssist.mc.thePlayer.rotationYaw - rotationUntilTarget(en)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (v > 0.0 && v < a) || (-a < v && v < 0.0);
    }
    
    @SubscribeEvent
    public void tickEvent(final TickEvent.RenderTickEvent event) {
        if (AimAssist.mc.thePlayer != null) {
            if (AimAssist.mc.gameSettings.keyBindAttack.isKeyDown()) {
                this.tre = true;
            }
            if (this.tre) {
                ++this.tru;
            }
            if (AimAssist.e.getState()) {
                if (AimAssist.mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(AimAssist.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(AimAssist.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                    return;
                }
            }
            if (AimAssist.d.getState() && !AimAssist.mc.gameSettings.keyBindAttack.isKeyDown() && this.tru >= 200) {
                this.tre = false;
                return;
            }
            if (this.tru >= 200) {
                this.tru = 0;
                return;
            }
            final Entity h = this.ent();
            if (h != null) {
                final float distanceTo = h.getDistanceToEntity((Entity)AimAssist.mc.thePlayer);
                if (h != null && (entityPosCompare(h) > 1.0 || entityPosCompare(h) < -1.0)) {
                    final boolean i = entityPosCompare(h) > 0.0;
                    final EntityPlayerSP thePlayer = AimAssist.mc.thePlayer;
                    thePlayer.setAngles((float)(i ? (-(Math.abs(entityPosCompare(h)) * (AimAssist.a.getValue() / 50.0))) : (Math.abs(entityPosCompare(h)) * AimAssist.a.getValue() / 50.0)), 0.0f);
                }
            }
        }
    }
}
