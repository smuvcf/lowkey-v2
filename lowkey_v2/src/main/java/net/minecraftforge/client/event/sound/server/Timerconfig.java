package net.minecraftforge.client.event.sound.server;

public class Timerconfig
{
    private long previousMS;
    
    public Timerconfig() {
        this.previousMS = 0L;
    }
    
    public boolean hasReached(final double ms) {
        return this.getTime() - this.previousMS >= ms;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public void reset() {
        this.previousMS = this.getTime();
    }
}
