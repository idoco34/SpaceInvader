import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Alien {
    protected int x, y, score;
    protected boolean movingRight;
    protected static final int WIDTH = 35, HEIGHT = 20;

    public Alien(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.movingRight = true;
        this.score = score;
    }

    public void move(double speed, int max) {
        if (movingRight) {
            x += speed;
        } else {
            x -= speed;
        }
    }

    public void moveDown() {
        y += 30;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
        
    public void setX(int x) {
        this.x = x;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean getMovingRight() {
        return movingRight;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getWidth() {
        return WIDTH;
    }
}
