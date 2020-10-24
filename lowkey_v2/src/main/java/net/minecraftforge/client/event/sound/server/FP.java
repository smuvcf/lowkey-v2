package net.minecraftforge.client.event.sound.server;

import net.minecraft.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.lang.reflect.*;
import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FP extends Module
{
    public static Minecraft mc;
    
    public FP() {
        super("FastPlace", 0, Category.M);
        FP.mc = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent a) {
        try {
            final Field field = Minecraft.class.getDeclaredField("field_71467_ac");
            field.setAccessible(true);
            final Field field2 = field;
            final Minecraft mc = FP.mc;
            field2.set(Minecraft.getMinecraft(), 0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
