package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.client.event.sound.server.Button;
import net.minecraftforge.client.event.sound.server.Component;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import java.awt.*;
import com.ibm.icu.math.*;

public class Slider extends Component
{
    private boolean hovered;
    private NumberValue value;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    
    public Slider(final NumberValue value, final Button button, final int offset) {
        this.value = value;
        this.parent = button;
        this.x = button.getParent().getX() + button.getParent().getWidth();
        this.y = button.getParent().getY() + button.getOffset();
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + this.parent.getParent().getWidth(), this.parent.getParent().getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
        final int drag = (int)(this.value.getValue() / this.value.getMax() * this.parent.getParent().getWidth());
        Gui.drawRect(this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + drag, this.parent.getParent().getY() + this.offset + 12, this.hovered ? -11184811 : -12303292);
        Gui.drawRect(this.parent.getParent().getX(), this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(String.valueOf(this.value.getName()) + ": " + this.value.getValue(), (float)(this.parent.getParent().getX() * 2 + 15), (float)((this.parent.getParent().getY() + this.offset + 2) * 2 + 5), kmethMain.color.getRGB());
        GL11.glPopMatrix();
    }
    
    @Override
    public void setOffset(final int newOffset) {
        this.offset = newOffset;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = (this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY));
        this.y = this.parent.getParent().getY() + this.offset;
        this.x = this.parent.getParent().getX();
        if (this.hovered && this.parent.isOpen() && Mouse.isButtonDown(0)) {
            final double diff = mouseX - this.parent.getParent().getX();
            final double value = this.round(diff / (this.parent.getParent().getWidth() - 1) * this.value.getMax(), 1);
            this.value.setValue(value);
            if (this.value.getName().equalsIgnoreCase("Red")) {
                kmethMain.color = new Color((int)this.value.getValue(), kmethMain.color.getGreen(), kmethMain.color.getBlue());
            }
            else if (this.value.getName().equalsIgnoreCase("Green")) {
                kmethMain.color = new Color(kmethMain.color.getRed(), (int)this.value.getValue(), kmethMain.color.getBlue());
            }
            else if (this.value.getName().equalsIgnoreCase("Blue")) {
                kmethMain.color = new Color(kmethMain.color.getRed(), kmethMain.color.getGreen(), (int)this.value.getValue());
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            final NumberValue numberValue = this.value;
            final double value = numberValue.getValue() - 0.1;
            numberValue.setValue(Math.round(value * 10.0) / 10.0);
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            final NumberValue numberValue = this.value;
            final double value = numberValue.getValue() + 0.1;
            numberValue.setValue(Math.round(value * 10.0) / 10.0);
        }
    }
    
    private double round(final double doubleValue, final int numOfDecimals) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(numOfDecimals, 4);
        return bigDecimal.doubleValue();
    }
    
    public boolean isMouseOnButtonD(final int x, final int y) {
        return x > this.x && x < this.x + (this.parent.getParent().getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }
    
    public boolean isMouseOnButtonI(final int x, final int y) {
        return x > this.x + this.parent.getParent().getWidth() / 2 && x < this.x + this.parent.getParent().getWidth() && y > this.y && y < this.y + 12;
    }
}
