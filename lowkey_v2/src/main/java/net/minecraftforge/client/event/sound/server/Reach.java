package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;

public class Reach extends Module
{
    public static NumberValue rmv;
    public static NumberValue rml;
    public static BooleanValue Tbs;
    public static BooleanValue Hd;
    public static BooleanValue rdm;
    public static Minecraft mc;
    public Random r;
    
    public Reach() {
        super("Reach", 0, Category.C);
        this.r = new Random();
        Reach.mc = Minecraft.getMinecraft();
        Reach.rmv = new NumberValue("Min", 3.0, 3.0, 6.0);
        Reach.rml = new NumberValue("Max", 3.0, 3.0, 6.0);
        Reach.Tbs = new BooleanValue("Blatant", false);
        Reach.rdm = new BooleanValue("Random", false);
        Reach.Hd = new BooleanValue("Misplaced", false);
        this.addValue(Reach.rmv);
        this.addValue(Reach.rml);
        this.addOption(Reach.Tbs);
        this.addOption(Reach.rdm);
        this.addOption(Reach.Hd);
    }
    
    @SubscribeEvent
    public void a(final MouseEvent a) {
        if (!Reach.Tbs.getState() && Reach.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }
        final double d2 = Reach.rmv.getValue() + this.r.nextDouble() * (Reach.rml.getValue() - Reach.rmv.getValue());
        final Object[] objects = a(d2, 0.0, 0.0f);
        if (objects == null) {
            return;
        }
        Reach.mc.objectMouseOver = new MovingObjectPosition((Entity)objects[0], (Vec3)objects[1]);
        Reach.mc.pointedEntity = (Entity)objects[0];
    }
    
    public static Object[] a(final double d, final double expand, final float partialTicks) {
        final Entity renderViewEntity = Reach.mc.getRenderViewEntity();
        Entity entity = null;
        if (renderViewEntity == null || Reach.mc.theWorld == null) {
            return null;
        }
        Reach.mc.mcProfiler.startSection("pick");
        final Vec3 eyePosition = renderViewEntity.getPositionEyes(0.0f);
        final Vec3 lookVal = renderViewEntity.getLook(0.0f);
        final Vec3 newEyePos = eyePosition.addVector(lookVal.xCoord * d, lookVal.yCoord * d, lookVal.zCoord * d);
        Vec3 hitVec = null;
        final List targetList = Reach.mc.theWorld.getEntitiesWithinAABBExcludingEntity(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(lookVal.xCoord * d, lookVal.yCoord * d, lookVal.zCoord * d).expand(1.0, 1.0, 1.0));
        double reach = d;
        for (int i = 0; i < targetList.size(); ++i) {
            final Entity target = (Entity) targetList.get(i);
            if (target.canBeCollidedWith()) {
                final float collisionSize = target.getCollisionBorderSize();
                AxisAlignedBB newTargetBB = target.getEntityBoundingBox().expand((double)collisionSize, (double)collisionSize, (double)collisionSize);
                newTargetBB = newTargetBB.expand(expand, expand, expand);
                final MovingObjectPosition mop = newTargetBB.calculateIntercept(eyePosition, newEyePos);
                if (newTargetBB.isVecInside(eyePosition)) {
                    if (0.0 < reach || reach == 0.0) {
                        entity = target;
                        hitVec = ((mop == null) ? eyePosition : mop.hitVec);
                        reach = 0.0;
                    }
                }
                else if (mop != null) {
                    final double distanceToTarget = eyePosition.distanceTo(mop.hitVec);
                    if (distanceToTarget < reach || reach == 0.0) {
                        final boolean canRiderInteract = false;
                        if (target == renderViewEntity.ridingEntity) {
                            if (reach == 0.0) {
                                entity = target;
                                hitVec = mop.hitVec;
                            }
                        }
                        else {
                            entity = target;
                            hitVec = mop.hitVec;
                            reach = distanceToTarget;
                        }
                    }
                }
            }
        }
        if (reach < d && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Reach.mc.mcProfiler.endSection();
        if (entity == null || hitVec == null) {
            return null;
        }
        return new Object[] { entity, hitVec };
    }
}
