import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AlienBo extends Alien  {
    private static final int WIDTH = 80, HEIGHT = 80;
    private long moveCoolDown = System.currentTimeMillis();
    private long fireDelay = System.currentTimeMillis();
    private int shotsFired = 0;
    private boolean move = true;
    public AlienBo(int x, int y, boolean movingRight) {
        super(x, y, 500);
        this.movingRight = movingRight;
    }

    public void move(double speed, int max) {
        if (move){
            if (movingRight) 
                x += speed * 7;
            else 
                x -= speed * 7;
        }
    }

    public void moveDown() {
        if (move) 
            moveCoolDown = System.currentTimeMillis();
        move = false;
        if(System.currentTimeMillis() - moveCoolDown > 3000) {
            //y += 50;
            shotsFired = 0;
            move = true;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void setFireDelay(long fireDelay) {
        this.fireDelay = fireDelay;
    }

    void setShotsFired() {
        shotsFired++;
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
    
    public int getShotsFired() {
        return shotsFired;
    }
    
    public int getScore() {
        return score;
    }
}
