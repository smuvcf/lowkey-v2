package net.minecraftforge.client.event.sound.server;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import java.awt.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class Nameplate
{
    private String name;
    private double x;
    private double y;
    private double z;
    private int width;
    private EntityLivingBase owner;
    private Minecraft mc;
    private RenderManager rm;
    
    public Nameplate(final String name, final double x, final double y, final double z, final EntityLivingBase owner) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.owner = owner;
        this.width = this.mc.fontRendererObj.getStringWidth(this.name) / 2;
        this.rm = this.mc.getRenderManager();
    }
    
    public void renderPlate(final Color color) {
        final float distance = this.mc.thePlayer.getDistanceToEntity((Entity)this.owner);
        final float scale = Math.abs(distance / 10.0f);
        final float border = 2.0f;
        final float rX = -this.width - 2.0f;
        final float rY = -2.0f;
        final float rX2 = this.width + 2.0f;
        final float rY2 = 10.0f;
        GL11.glEnable(3042);
        GL11.glTranslated(this.x, this.y + this.owner.height + 0.5, this.z);
        GL11.glNormal3d(0.0, 1.0, 0.0);
        GL11.glRotated((double)(-this.rm.playerViewY), 0.0, 1.0, 0.0);
        GL11.glRotated((double)this.rm.playerViewX, 1.0, 0.0, 0.0);
        GL11.glScaled(-0.05, -0.05, -0.05);
        GL11.glTranslated(0.0, (double)(-(scale * 7.0f)), 0.0);
        if (scale > 1.0f) {
            GL11.glScaled((double)scale, (double)scale, (double)scale);
        }
        Gui.drawRect((int)rX, -2, (int)rX2, 10, 1056964608);
        this.mc.fontRendererObj.drawStringWithShadow(this.name, (float)(-this.width), 0.0f, color.getRGB());
        GL11.glDisable(3042);
    }
}
