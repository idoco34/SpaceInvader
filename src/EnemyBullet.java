import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet extends Bullet {

    public EnemyBullet(int x, int y, int wide, int height, int speed, Color color) {
        super(x, y, wide, height ,speed, color);
    }

    // Moves the bullet upwards by a set speed
    public void move() {
        y += speed;
    }    

    // Draws the bullet
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
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

    // Returns if the bullet is off the screen
    public boolean isOffScreen() {
        return y < 0 || y>600;
    }
    
    public void setPierce(int pierce) {
        this.pierce = pierce;
    }
    
    public int getPierce() {
        return pierce;
    }
}
