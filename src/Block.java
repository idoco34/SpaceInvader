import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block {
    private int x, y;
    private final int width = 20, height = 20;
    private Color color;
    private boolean pickedUp;  // Flag to track if the block is picked up

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = new Color(204, 204, 50);  // Default color
        this.pickedUp = false;   // Initially, not picked up
    }

    // Simulate falling unless the block is picked up
    public void fall() {
        if (!pickedUp) {  // Only fall if not picked up
            y += 5;  // Fall speed (adjust as necessary)
        }
    }

    // Draw the block on the screen
    public void draw(Graphics g) {
        if (pickedUp) {
            g.setColor(Color.GREEN);  // Change color when picked up
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);  // Draw a white border to indicate pickup
            g.drawRect(x, y, width, height);
        } else {
            g.setColor(color);  // Regular color if not picked up
            g.fillRect(x, y, width, height);
        }
    }

    // Returns the bounds of the block for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Check if the block is picked up
    public boolean isPickedUp() {
        return pickedUp;
    }

    // Set the block's picked-up state
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    // Set the x position of the block
    public void setX(int x) {
        this.x = x;
    }

    // Set the y position of the block
    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
    // Get the block's height
    public int getHeight() {
        return height;
    }

    // Get the block's width
    public int getWidth() {
        return width;
    }

    // Set the block's color
    public void setColor(Color color) {
        this.color = color;
    }
}
