import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
    protected int x, y, speed, pierce;
    protected Color color;
    protected final int width, height;
    public Bullet(int x, int y, int width, int height, int speed, Color c) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = c;
    }

    // Moves the bullet upwards by a set speed
    public void move() {
        
    }    

    // Draws the bullet
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x-2, y-15, width, height);
    }
    
    
    public void setColor(Color c) {
        this.color = c;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setPierce(int pierce) {
        this.pierce = pierce;
    }
    // Returns the bounding rectangle for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Returns the current y position
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getPierce() {
        return pierce;
    }

    // Returns if the bullet is off the screen
    public boolean isOffScreen() {
        return y < 0 || y>600;
    }
}
