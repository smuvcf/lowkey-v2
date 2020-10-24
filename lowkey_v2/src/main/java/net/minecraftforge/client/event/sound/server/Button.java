package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.client.event.sound.server.Component;
import net.minecraftforge.client.event.sound.server.Frame;
import org.lwjgl.opengl.*;


public class Button extends Component
{
    private Module mod;
    private Frame parent;
    private int offset;
    private boolean hovered;
    private ArrayList<Component> subcomponents;
    private boolean open;
    
    public Button(final Module mod, final Frame parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        int optionY = offset + 12;
        if (!mod.getValues().isEmpty()) {
            for (final NumberValue numberValue : mod.getValues()) {
                final Slider slider = new Slider(numberValue, this, optionY);
                this.subcomponents.add(slider);
                optionY += 12;
            }
        }
        if (!mod.getOptions().isEmpty()) {
            for (final BooleanValue booleanValue : mod.getOptions()) {
                final Checkbox checkbox = new Checkbox(booleanValue, this, optionY);
                this.subcomponents.add(checkbox);
                optionY += 12;
            }
        }
        this.subcomponents.add(new Keybind(this, optionY));
    }
    
    @Override
    public void setOffset(final int newOffset) {
        this.offset = newOffset;
        int optionY = this.offset + 12;
        for (final Component component : this.subcomponents) {
            component.setOffset(optionY);
            optionY += 12;
        }
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.hovered ? (this.mod.getState() ? new Color(16777215).darker().getRGB() : -14546254) : (this.mod.getState() ? new Color(14, 14, 14).getRGB() : -15658735));
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)((this.parent.getX() + 2) * 2), (float)((this.parent.getY() + this.offset + 2) * 2 + 4), this.mod.getState() ? 10066329 : -1);
        if (this.subcomponents.size() > 1) {
            Render.drawArrow((this.parent.getX() + this.parent.getWidth() - 10) * 2, (this.parent.getY() + this.offset + 2) * 2 + 5, this.open, this.mod.getState() ? kmethMain.color.getRGB() : (this.hovered ? -11154811 : kmethMain.color.getRGB()));
        }
        GL11.glPopMatrix();
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component component : this.subcomponents) {
                component.renderComponent();
            }
            Gui.drawRect(this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, Color.HSBtoRGB(System.currentTimeMillis() % 100000L / 4850.0f, 0.7f, 0.7f));
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component component : this.subcomponents) {
                component.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.setState(!this.mod.getState());
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component component : this.subcomponents) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component component : this.subcomponents) {
            component.keyTyped(typedChar, key);
        }
    }
    
    public Module getMod() {
        return this.mod;
    }
    
    public Frame getParent() {
        return this.parent;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
