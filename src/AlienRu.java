import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienRu extends Alien  {
    private static final int WIDTH = 25, HEIGHT = 70;
    
    public AlienRu(int x, int y) {
        super(x, y, 150);
        this.movingRight = false;
    }

    public void move(double speed, int max) {
        if (movingRight) {
            x += speed * 2;
        } else {
            x -= speed * 2;
        }
    }

    public void moveDown() {
        y += 30;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(200, 255, 0));
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
