import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienSt extends Alien {
    private static final int WIDTH = 30, HEIGHT = 30;
    private static long moveCoolDown = System.currentTimeMillis();
    public AlienSt(int x, int y) {
        super(x, y, 100);
    }

    // Diagonal movement to the bottom-right
    public void move(double speed, int max) {
        if(System.currentTimeMillis() - moveCoolDown > 100) {
            y += speed;
        } 
    }
    
    // Override draw method to make the alien look different if needed
    public void draw(Graphics g) {
        g.setColor(new Color(176, 196, 222)); // Change color to differentiate from the original Alien
        g.fillRect(x, y, WIDTH, HEIGHT); // Drawing the alien
    }

    // Override getBounds to match the new position and shape
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
        
    public static void setMoveCoolDown(long moveCoolDown) {
        AlienSt.moveCoolDown = moveCoolDown;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public static long getMoveCoolDown() {
        return AlienSt.moveCoolDown;
    }
    
    public int getScore() {
        return score;
    }
}
