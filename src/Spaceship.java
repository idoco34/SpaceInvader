import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.*;

public class Spaceship {
    private int x, y;
    private final int width = 80, height = 28;
    private Color color;
    private boolean carriedBlock;
    private int speed;
    private boolean moveEnable = true;
    
    public Spaceship(int x, int y) {
        this.x = 600;
        this.y = y;
        this.color = Color.BLUE;  // Default spaceship color
        this.carriedBlock = false;  // Initially, no block is carried
        this.speed = 4;
    }

    public void moveLeft(boolean iodc) {
        if (x > 0 && moveEnable) {
            x -= speed;// Prevent moving off the left side
            if (iodc) x += 3;
        }
    }

    public void moveRight(boolean iodc) {
        if (x < Toolkit.getDefaultToolkit().getScreenSize().width - width && moveEnable) {
            x += speed;// Prevent moving off the right side (800 - width of spaceship = 750)
            if (iodc) x -= 3 ;
        }
    }

    public void moveDashLeft() {
        if (x > 0 && moveEnable) {  // Prevent dashing off the left side
            x -= 80;  // Dash movement to the left (60 is the dash distance)
        }
    }

    public void moveDashRight() {
        if (x < Toolkit.getDefaultToolkit().getScreenSize().width - width -50 && moveEnable) {  // Prevent dashing off the right side
            x += 80;  // Dash movement to the right (60 is the dash distance)
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height); // Draw the spaceship
        g.fillRect(x+35, y-15, 10, 15);
        if (carriedBlock != false) {
            // Update block's position to be above the spaceship
            g.setColor(new Color(204, 204, 50));
            g.fillRect(x + 30, y + 30, 20, 20); // Draw the carried block
        }
    }

    public void pickUpBlock(boolean cb) {
        this.carriedBlock = cb;  // Pick up the block
    }

    // New method to attempt to deposit the block at the station
    public void tryToDepositBlock(Station station) {
        // Check if the spaceship is in range of the station (based on bounding box overlap)
        if (getBounds().intersects(station.getBounds()) && carriedBlock) {
            station.setIsActive(true);  // Deposit the block at the station
            carriedBlock = false;  // Remove the block from the spaceship
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Return spaceship bounds for collision detection
    }

    public int getX() {
        return x;  // Get x-coordinate of the spaceship
    }

    public int getY() {
        return y;  // Get y-coordinate of the spaceship
    }

    public int getWidth() {
        return width;  // Get width of the spaceship
    }

    public int getHeight() {
        return height;  // Get height of the spaceship
    }
    
    public int getSpeed() {
        return speed;
    }

    public boolean getCarriedBlock() {
        return carriedBlock;  // Get the block currently carried by the spaceship
    }

    // Method to check if the spaceship is carrying a block
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setColor(Color c) {
        this.color = c;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setCarriedBlock(boolean cb) {
        carriedBlock = cb;
    }
    
    public void setMoveEnable(boolean moveEnable) {
        this.moveEnable = moveEnable;
    }
}
