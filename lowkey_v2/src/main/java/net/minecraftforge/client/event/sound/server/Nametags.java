package net.minecraftforge.client.event.sound.server;

import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;

import org.lwjgl.opengl.*;
import java.awt.*;

public class Nametags extends Module
{
    private Queue<Nameplate> tags;
    public static NumberValue r;
    public static NumberValue g;
    public static NumberValue b;
    public static BooleanValue unnick;
    
    public Nametags() {
        super("Nametags", 0, Category.R);
        this.tags = new ArrayDeque<Nameplate>();
        Nametags.r = new NumberValue("R", 150.0, 0.0, 255.0);
        Nametags.g = new NumberValue("G", 150.0, 0.0, 255.0);
        Nametags.b = new NumberValue("B", 150.0, 0.0, 255.0);
        this.addOption(Nametags.unnick = new BooleanValue("Hide", false));
        this.addValue(Nametags.r);
        this.addValue(Nametags.g);
        this.addValue(Nametags.b);
    }
    
    @SubscribeEvent
    public void onNameRender(final RenderLivingEvent.Specials.Pre event) {
        if (event.entity.getAlwaysRenderNameTagForRender() && event.entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.entity;
            final String name = player.getDisplayNameString();
            if (player != Wrapper.getPlayer() && !player.getDisplayNameString().contains("-") && !player.getDisplayNameString().contains("&")) {
                if (!Nametags.unnick.getState()) {
                    this.tags.add(new Nameplate(name, event.x, event.y, event.z, event.entity));
                }
                else {
                    this.tags.add(new Nameplate("Bruvv", event.x, event.y, event.z, event.entity));
                }
            }
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderHandEvent event) {
        GL11.glPushMatrix();
        GL11.glDepthRange(0.0, -100.0);
        while (!this.tags.isEmpty()) {
            GL11.glPushMatrix();
            this.tags.poll().renderPlate(new Color((int)Nametags.r.getValue(), (int)Nametags.g.getValue(), (int)Nametags.b.getValue()));
            GL11.glPopMatrix();
        }
        GL11.glDepthRange(0.0, 100.0);
        GL11.glPopMatrix();
    }
}
