package net.minecraftforge.client.event.sound.server;

import net.minecraftforge.client.event.sound.server.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import java.util.*;

public abstract class Module
{
    private boolean state;
    private String name;
    private int key;
    private Category category;
    private ArrayList<BooleanValue> options;
    private ArrayList<NumberValue> values;
    
    public Module(final String name, final int key, final Category category) {
        this.state = false;
        this.name = name;
        this.key = key;
        this.category = category;
        this.options = new ArrayList<BooleanValue>();
        this.values = new ArrayList<NumberValue>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public ArrayList<BooleanValue> getOptions() {
        return this.options;
    }
    
    public void addOption(final BooleanValue option) {
        this.options.add(option);
    }
    
    public ArrayList<NumberValue> getValues() {
        return this.values;
    }
    
    public void addValue(final NumberValue value) {
        this.values.add(value);
    }
    
    public boolean getState() {
        return this.state;
    }
    
    public void setState(final boolean enabled) {
        if (this.state == enabled) {
            return;
        }
        this.state = enabled;
        if (enabled) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            FMLCommonHandler.instance().bus().register((Object)this);
            this.onEnable();
        }
        else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            FMLCommonHandler.instance().bus().unregister((Object)this);
            this.onDisable();
        }
    }
    
    public static ArrayList<Module> getCategoryModules(final Category cat) {
        final ArrayList<Module> modsInCategory = new ArrayList<Module>();
        for (final Module mod : kmethMain.getModules()) {
            if (mod.getCategory() == cat) {
                modsInCategory.add(mod);
            }
        }
        return modsInCategory;
    }
    
    public static Module getModule(final Class<? extends Module> clazz) {
        for (final Module mod : kmethMain.getModules()) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void update() {
    }
    
    public enum Category
    {
        C("C", 0), 
        R("R", 0), 
        M("M", 0);
        
        private String name;
        private int id;
        
        private Category(final String name, final int id) {
            this.name = name;
            this.id = id;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getID() {
            return this.id;
        }
    }
}
