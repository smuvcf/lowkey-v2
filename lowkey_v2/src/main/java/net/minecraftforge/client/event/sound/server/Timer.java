package net.minecraftforge.client.event.sound.server;

import java.util.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Timer extends Module
{
    public Random r;
    public float tsc;
    public static NumberValue speed;
    private float srb;
    public static Minecraft mc;
    
    public Timer() {
        super("Timer", 0, Category.M);
        this.srb = 1.0f;
        this.r = new Random();
        Timer.mc = Minecraft.getMinecraft();
        this.addValue(Timer.speed = new NumberValue("Speed", 1.0, 1.0, 2.0));
    }
    
    private void setTimerRate(final float tick) {
        try {
            final Field timerField = Minecraft.class.getDeclaredField("field_71428_T");
            final Field tickPSField = net.minecraft.util.Timer.class.getDeclaredField("field_74278_d");
            if (timerField != null) {
                timerField.setAccessible(true);
                final net.minecraft.util.Timer timer = (net.minecraft.util.Timer)timerField.get(Timer.mc);
                timerField.setAccessible(false);
                tickPSField.setAccessible(true);
                tickPSField.set(timer, 1.0f + ((float)Timer.speed.getValue() - 1.0f));
                tickPSField.setAccessible(false);
            }
            else {
                System.out.println("timerfield is null");
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void a(final TickEvent.RenderTickEvent e) {
        this.tsc = this.srb;
        if (Timer.mc == null) {
            System.out.println("mc is null");
            return;
        }
        this.setTimerRate(this.tsc);
    }
}
