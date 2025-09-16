import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienDi extends Alien {
    private boolean movingDown = true;
    private long fireDelay = System.currentTimeMillis();
    public static final int WIDTH = 60, HEIGHT = 35;
    public AlienDi(int x, int y) {
        super(x, y, 200);
    }

    // Diagonal movement to the bottom-right
    public void move(double speed, int max) {
        if(movingDown && y>max) movingDown = false;
        if(!movingDown && y<0) movingDown = true;
        if(movingRight) x += speed * 2;
        if(!movingRight) x -= speed * 2;
        if (movingDown) y += speed;
        if (!movingDown) y -= speed;
    
    }
    
    public void moveDown() {
        
    }
    
    // Override draw method to make the alien look different if needed
    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Change color to differentiate from the original Alien
        g.fillRect(x, y, WIDTH, HEIGHT); // Drawing the alien
    }

    // Override getBounds to match the new position and shape
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void setFireDelay(long fireDelay) {
        this.fireDelay = fireDelay;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public long getFireDelay() {
        return fireDelay;
    }
    
    public int getScore() {
        return score;
    }
}
