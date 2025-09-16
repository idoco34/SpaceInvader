import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienBa extends Alien  {
    private static final int WIDTH = 35, HEIGHT = 20;
    private static boolean moveDown = false, moveRight = true;
    public AlienBa(int x, int y) {
        super(x, y, 50);
    }

    public void move(double speed, int max) {
        if (moveRight) {
            x += speed;
        } else {
            x -= speed;
        }
    }

    public void moveDown() {
        if(moveDown) {
           y += 20;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public static void setMoveDown(boolean moveDown) {
        AlienBa.moveDown = moveDown;
    }
    
    public static void setMoveRight(boolean moveRight) {
        AlienBa.moveRight = moveRight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public static boolean getMoveDown() {
        return moveDown;
    }
    
    public int getScore() {
        return score;
    }
}
