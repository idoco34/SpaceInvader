import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienBl extends Alien  {
    private static final int WIDTH = 60, HEIGHT = 20;
    
    public AlienBl(int x, int y) {
        super(x, y, 0);
    }

    public void move(double speed, int max) {}  
    

    public void moveDown() {}

    public void draw(Graphics g) {
        g.setColor(new Color(224, 224, 224));
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getScore() {
        return score;
    }
}
