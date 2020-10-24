package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.entity.*;
import java.util.*;

public class Hitboxes extends Module
{
    private static NumberValue expand;
    
    public Hitboxes() {
        super("Hitboxes", 0, Category.C);
        this.addValue(Hitboxes.expand = new NumberValue("Expand", 0.0, 0.0, 1.0));
    }
    
    public Entity entity() {
        Entity en = null;
        if (Wrapper.getPlayer().worldObj != null) {
            for (final Object o : Wrapper.getWorld().loadedEntityList) {
                en = (Entity)o;
            }
        }
        return en;
    }
    
    @Override
    public void update() {
        final Entity renderViewEntity = Reach.mc.getRenderViewEntity();
        final List targetList = Reach.mc.theWorld.loadedEntityList;
        final Entity ent = this.entity();
        for (final Object aTargetList : targetList) {
            final Entity target = (Entity)aTargetList;
            if (target != Wrapper.getPlayer() && target.canBeCollidedWith()) {
                target.width = (float)(1.0 + Hitboxes.expand.getValue());
            }
        }
    }
}
