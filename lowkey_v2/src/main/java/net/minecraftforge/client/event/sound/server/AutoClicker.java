package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraft.client.*;
import java.util.*;
import java.lang.reflect.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.nio.*;

public class AutoClicker extends Module
{
    public static NumberValue minVal;
    public static NumberValue maxVal;
    public static BooleanValue InvFill;
    public static BooleanValue BlockHit;
    public static BooleanValue BreakBlocks;
    public static BooleanValue Randomise;
    public static boolean isRunning;
    public static Minecraft mc;
    private long nlu;
    private long nld;
    private long nru;
    private long nrd;
    private long nd;
    private long ne;
    private double drr;
    private boolean dr;
    private Method gg;
    public Random r;
    private boolean hasSelectedBlock;
    private static Field bst;
    private static Field fff;
    private static Field bff;
    
    public AutoClicker() {
        super("Autoclicker", 0, Category.C);
        this.hasSelectedBlock = false;
        this.r = new Random();
        AutoClicker.minVal = new NumberValue("Min", 7.0, 0.0, 20.0);
        AutoClicker.maxVal = new NumberValue("Max", 12.0, 0.0, 20.0);
        AutoClicker.InvFill = new BooleanValue("Refill", false);
        AutoClicker.BlockHit = new BooleanValue("Block hit", false);
        AutoClicker.BreakBlocks = new BooleanValue("Break Blocks", false);
        AutoClicker.Randomise = new BooleanValue("Randomise", false);
        this.addValue(AutoClicker.minVal);
        this.addValue(AutoClicker.maxVal);
        this.addOption(AutoClicker.InvFill);
        this.addOption(AutoClicker.BlockHit);
        this.addOption(AutoClicker.BreakBlocks);
        this.addOption(AutoClicker.Randomise);
        try {
            this.gg = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.RenderTickEvent e) {
        if (this.gg != null) {
            if (AutoClicker.mc.currentScreen == null) {
                if (AutoClicker.BreakBlocks.getState() && this.getIfSelectingBlock(this.hasSelectedBlock)) {
                    return;
                }
                Mouse.poll();
                if (Mouse.isButtonDown(0)) {
                    if (this.nld > 0L && this.nlu > 0L) {
                        if (System.currentTimeMillis() > this.nld) {
                            final int attackKeyBind = AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode();
                            KeyBinding.setKeyBindState(attackKeyBind, true);
                            KeyBinding.onTick(attackKeyBind);
                            s(0, true);
                            this.vcx();
                            AutoClicker.isRunning = true;
                        }
                        else if (System.currentTimeMillis() > this.nlu) {
                            KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                            s(0, false);
                            AutoClicker.isRunning = false;
                        }
                    }
                    else {
                        this.vcx();
                    }
                    final boolean bh = AutoClicker.BlockHit.getState();
                    if (bh && Mouse.isButtonDown(1)) {
                        if (this.nrd > 0L && this.nru > 0L) {
                            if (System.currentTimeMillis() > this.nrd) {
                                KeyBinding.setKeyBindState(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                                KeyBinding.onTick(AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode());
                                s(1, true);
                                this.vcxx();
                            }
                            else if (System.currentTimeMillis() > this.nru) {
                                final int useItemKeyBind = AutoClicker.mc.gameSettings.keyBindUseItem.getKeyCode();
                                KeyBinding.setKeyBindState(useItemKeyBind, false);
                                s(1, false);
                            }
                        }
                        else {
                            this.vcxx();
                        }
                    }
                    else {
                        final long n = 0L;
                        this.nru = 0L;
                        this.nrd = 0L;
                    }
                }
                else {
                    final long n2 = 0L;
                    this.nru = 0L;
                    this.nrd = 0L;
                    this.nlu = 0L;
                    this.nld = 0L;
                }
            }
            else if (AutoClicker.mc.currentScreen instanceof GuiInventory) {
                if (Mouse.isButtonDown(0) && (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42))) {
                    final boolean inventoryFill = AutoClicker.InvFill.getState();
                    if (!inventoryFill) {
                        return;
                    }
                    if (this.nlu == 0L || this.nld == 0L) {
                        this.vcx();
                        return;
                    }
                    if (System.currentTimeMillis() > this.nld) {
                        this.vcx();
                        this.c(AutoClicker.mc.currentScreen);
                    }
                }
                else {
                    final long n3 = 0L;
                    this.nru = 0L;
                    this.nrd = 0L;
                    this.nlu = 0L;
                    this.nld = 0L;
                }
            }
        }
    }
    
    private boolean getIfSelectingBlock(boolean c) {
        if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
            final Entity player = (Entity)AutoClicker.mc.thePlayer;
            if (player != null) {
                final MovingObjectPosition mop = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(4.8, 1.0f);
                if (mop != null) {
                    final World world = AutoClicker.mc.thePlayer.worldObj;
                    if (world != null) {
                        final BlockPos pos = new BlockPos(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
                        if (pos != null) {
                            final Material mat = world.getBlockState(pos).getBlock().getMaterial();
                            if (mat != null) {
                                if (Minecraft.getMinecraft().objectMouseOver.entityHit != null) {
                                    c = false;
                                }
                                else if (mop.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                                    c = true;
                                }
                                else if (mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                                    c = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return c;
    }
    
    private void vcx() {
        final double mcc = AutoClicker.minVal.getValue();
        final double mca = AutoClicker.maxVal.getValue();
        if (mcc > mca) {
            return;
        }
        final double CPS = mcc + this.r.nextDouble() * (mca - mcc);
        long delay = (int)Math.round(1000.0 / CPS);
        if (System.currentTimeMillis() > this.nd) {
            if (!this.dr && this.r.nextInt(100) >= 85) {
                this.dr = true;
                this.drr = 1.1 + this.r.nextDouble() * 0.15;
            }
            else {
                this.dr = false;
            }
            this.nd = System.currentTimeMillis() + 500L + this.r.nextInt(1500);
        }
        if (this.dr) {
            delay *= (long)this.drr;
        }
        if (System.currentTimeMillis() > this.ne) {
            if (this.r.nextInt(100) >= 80) {
                delay += 50L + this.r.nextInt(150);
            }
            this.ne = System.currentTimeMillis() + 500L + this.r.nextInt(1500);
        }
        this.nld = System.currentTimeMillis() + delay;
        this.nlu = System.currentTimeMillis() + delay / 2L - this.r.nextInt(10);
    }
    
    public static void s(final int button, final boolean state) {
        final MouseEvent m = new MouseEvent();
        AutoClicker.fff.setAccessible(true);
        try {
            AutoClicker.fff.set(m, button);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        AutoClicker.fff.setAccessible(false);
        AutoClicker.bst.setAccessible(true);
        try {
            AutoClicker.bst.set(m, state);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        AutoClicker.bst.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)m);
        try {
            AutoClicker.bff.setAccessible(true);
            final ByteBuffer buffer = (ByteBuffer)AutoClicker.bff.get(null);
            AutoClicker.bff.setAccessible(false);
            buffer.put(button, (byte)(state ? 1 : 0));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void vcxx() {
        final double mcc = 4.0;
        final double mca = 6.0;
        final double CPS = 4.0 + this.r.nextDouble() * 2.0;
        final long delay = (int)Math.round(1000.0 / CPS);
        this.nrd = System.currentTimeMillis() + delay;
        this.nru = System.currentTimeMillis() + 20L + this.r.nextInt(30);
    }
    
    private void c(final GuiScreen s) {
        final int var1 = Mouse.getX() * s.width / AutoClicker.mc.displayWidth;
        final int var2 = s.height - Mouse.getY() * s.height / AutoClicker.mc.displayHeight - 1;
        final int var3 = 0;
        try {
            this.gg.setAccessible(true);
            this.gg.invoke(s, var1, var2, 0);
            this.gg.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        AutoClicker.isRunning = false;
        AutoClicker.mc = Minecraft.getMinecraft();
        try {
            AutoClicker.fff = MouseEvent.class.getDeclaredField("button");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            AutoClicker.bst = MouseEvent.class.getDeclaredField("buttonstate");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            AutoClicker.bff = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
