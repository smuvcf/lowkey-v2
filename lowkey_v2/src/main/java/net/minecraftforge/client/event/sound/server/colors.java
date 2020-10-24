package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class colors extends Module
{
    public static NumberValue r;
    public static NumberValue g;
    public static NumberValue b;
    
    public colors() {
        super("Colors", 0, Category.R);
        colors.r = new NumberValue("Red", 150.0, 0.0, 255.0);
        colors.g = new NumberValue("Green", 150.0, 0.0, 255.0);
        colors.b = new NumberValue("Blue", 150.0, 0.0, 255.0);
        this.addValue(colors.r);
        this.addValue(colors.g);
        this.addValue(colors.b);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent event) {
        System.out.println(colors.r.getValue() + " " + colors.g.getValue() + " " + colors.b.getValue());
        kmethMain.color = new Color((int)colors.r.getValue(), (int)colors.g.getValue(), (int)colors.b.getValue());
    }
    
    @Override
    public boolean getState() {
        return true;
    }
}
