package net.minecraftforge.client.event.sound.server;

import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.client.event.sound.server.*;

public class Frame
{
    private ArrayList<Component> components;
    private Module.Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    
    public Frame(final Module.Category category) {
        this.components = new ArrayList<Component>();
        this.category = category;
        this.width = 70;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        int tY = this.barHeight;
        for (final Module mod : Module.getCategoryModules(this.category)) {
            final Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }
    
    public void renderFrame() {
        Gui.drawRect(this.x - 3, this.y, this.x + this.width + 3, this.y + this.barHeight, kmethMain.color.getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.category.getName(), (float)((this.x + 2) * 2 + 5), (float)((this.y + 2) * 2 + 5), -1);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (float)((this.x + this.width - 10) * 2 + 5), (float)((this.y + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.renderComponent();
            }
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public void refresh() {
        int offset = this.barHeight;
        for (final Component component : this.components) {
            component.setOffset(offset);
            offset += component.getHeight();
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
