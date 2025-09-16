import java.awt.*;

public class Station {
    private int x, y;
    private final int width = 100, height = 55;
    private boolean isActive;
    private boolean powerUpReady;
    private long powerUpCooldownStartTime;
    private final int POWER_UP_COOLDOWN = 10000;  // 10 seconds cooldown
    private Color color;

    public Station(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = false;
        this.powerUpReady = false;  // Initially, the power-up is not ready
        this.powerUpCooldownStartTime = 0;
        this.color = Color.GREEN;
    }

    public void draw(Graphics g) {
        // Draw the station first
        g.setColor(color);
        g.fillRect(x, y, width, height);
        // Draw the deposited block at the station (if there's one)
        if (isActive != false && !powerUpReady) {
                long remainingTimeRight = POWER_UP_COOLDOWN - (System.currentTimeMillis() - powerUpCooldownStartTime);
                int secondsRight = (int) (remainingTimeRight / 1000);
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawString("" + secondsRight, x + 43, y + 35);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Method to add a block to the station (when spaceship deposits it)

    // Method to update the station's power-up state
    public void update() {
        // Check if the power-up cooldown has finished and the block is still deposited
        if (isActive != false && !powerUpReady && (System.currentTimeMillis() - powerUpCooldownStartTime >= POWER_UP_COOLDOWN)) {
            powerUpReady = true;  // The block turns into a power-up after cooldown
            color = new Color(255, 179, 71);
        }
    }


    // Reload method: Prepare the station for the next block to be deposited
    public void reload() {
        // The station is now ready to accept a new block
        powerUpReady = false;  // Reset power-up readiness
        isActive = false;
        powerUpCooldownStartTime = 0;  // Reset the cooldown timer
        color = Color.GREEN;
    }

    public void setColor(Color c) {
        color = c;
    }
    
    public void setPowerUpReady(boolean powerUpReady) {
        this.powerUpReady = powerUpReady;
    }
    
    public void setPowerUpCooldownStartTime(long powerUpCooldownStartTime) {
        this.powerUpCooldownStartTime = powerUpCooldownStartTime;
    }
    
    public void setIsActive(boolean ia) {
        isActive = ia;
    }
    // Getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public boolean getPowerUpReady() {
        return powerUpReady;
    }
    
    public long getPowerUpCooldownStartTime() {
        return powerUpCooldownStartTime;
    }
    
    public boolean getIsActive() {
        return isActive;
    }
}
