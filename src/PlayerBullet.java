import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PlayerBullet extends Bullet {
    private int pierce;
    public PlayerBullet(int x, int y, int speed, int pierce) {
        super(x, y, 5, 15 ,speed, Color.MAGENTA);
        this.pierce = pierce;
    }

    // Moves the bullet upwards by a set speed
    public void move() {
        y -= speed;
    }    

    // Draws the bullet
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x-2, y-15, width, height);
    }
    
    public void setPierce(int pierce) {
        this.pierce = pierce;
    }
    
    public void setColor(Color c) {
        this.color = c;
    }
    
    // Returns the bounding rectangle for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Returns the current y position
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
