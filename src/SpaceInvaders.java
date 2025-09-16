import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class SpaceInvaders extends JPanel {
    private Spaceship spaceship;
    private ArrayList<Bullet> bullets;
    private ArrayList<Alien> aliens;
    private ArrayList<Block> blocks;
    private boolean left, right, shooting, spacePressed;
    private boolean isReloading;
    private long reloadStartTime;
    private int bulletsFired, maxBullets;
    private long powerUpDeliveredTime;
    private int SHOOT_COOLDOWN = 500;
    private long shootCooldownTime = System.currentTimeMillis();
    private int dashCoolDown = 1500;  // Cooldown time for dash (in milliseconds)
    private long dashCooldownTime = System.currentTimeMillis();
    private boolean isOnDashCoolDown = false;
    private double alienSpeed = 1;
    private String gameState;
    private Timer timer;
    private Station leftStation, rightStation;
    private Block carriedBlock;
    private long powerUpCooldownStartTime;  // Time when the cooldown starts for power-up delivery
    private final int POWER_UP_COOLDOWN = 10000;  // 10-second cooldown (10000 milliseconds)
    private boolean powerUpReadyLeftStation, powerUpReadyRightStation;
    private long score = 0;
    private int max;
    private int pierce = 1;
    private int mult = 1;
    private int bulletSpeed = 10;
    private boolean shild = false;
    private int fireDelay = 4000;
    private int bossFireDelay = 300;
    private double dropChance = 0.15;
    private String msg = "";
    private int cBl = 0;
    private Clip clip;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean clipPlaying = true;
    private int stage = 1;
    
    public SpaceInvaders() {
        spaceship = new Spaceship(300, 520);
        bullets = new ArrayList<>();
        aliens = new ArrayList<>();
        blocks = new ArrayList<>();
        maxBullets = 5;
        bulletsFired = 0;
        isReloading = false;
        gameState = "MENU";
        leftStation = new Station(50, 510);  // Left station position
        rightStation = new Station(screenSize.width-150, 510); // Right station position
        carriedBlock = null;
        powerUpReadyLeftStation = true;  // Left station is ready to receive power-ups initially
        powerUpReadyRightStation = true; // Right station is ready to receive power-ups initially
        try {
            File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceMenu.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        // Initialize aliens
        initialize(stage);

        // Set up the game window
        setPreferredSize(screenSize);
        setBackground(Color.BLACK);
        setFocusable(true);
        
         addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    left = true;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    right = true;
                } else if (e.getKeyCode() == KeyEvent.VK_W && bulletsFired < maxBullets && !isReloading && (System.currentTimeMillis() - shootCooldownTime >= SHOOT_COOLDOWN)) {
                    shooting = true;
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    if(!isReloading) startReloading();
                    else if (isReloading) {
                        isReloading = false;
                        reloadStartTime = 0;   
                    }
                      // Start reloading if not already reloading
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && (System.currentTimeMillis() - dashCooldownTime >= dashCoolDown)) {
                    spacePressed = true;
                    if (left) {
                        spaceship.moveDashLeft();
                        spaceship.setColor(Color.CYAN);
                        isOnDashCoolDown = true;
                    } else if (right) {
                        spaceship.moveDashRight();
                        spaceship.setColor(Color.CYAN);
                        isOnDashCoolDown = true;
                    }
                    else {
                        spaceship.setColor(new Color(0, 135, 255));
                        isOnDashCoolDown = true; 
                        shild = true;
                    }
                    dashCooldownTime = System.currentTimeMillis();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
                    System.exit(0); // Exits the application    
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (clipPlaying) {
                        clip.stop();
                        clipPlaying = false;
                    }
                   else {
                      clip.start();
                      clipPlaying = true;
                   }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    left = false;
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    right = false;
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    spacePressed = false;
                }  
            }
        });
        // Timer for the game loop
        timer = new Timer(10, e -> actionPerformed());
        timer.start();
    }

    private void initialize(int stage) {
        //Create a grid of aliens
        cBl = 0;
        isReloading = false; 
        bulletsFired = 0; // Reset ammo only when reload is done
        spaceship.setSpeed(4);
        pierce = 1;
        mult = 1;
        maxBullets = 5;
        bulletSpeed = 10;
        msg = "";
        blocks.clear();
        leftStation.reload();
        rightStation.reload();
        blocks.remove(spaceship.getCarriedBlock());
        spaceship.setCarriedBlock(false);
        spaceship.setX(600);
        if (stage > 0){
            for (int j=0; j<2; j++) {
                for (int i=0; i<3; i++) {
                    aliens.add(new AlienBl(200+i*120, 250+21*j)); 
                    cBl++;
                }
            }
            for (int j=0; j<2; j++) {
                for (int i=0; i<3; i++) {
                    aliens.add(new AlienBl(800+i*120, 250+21*j)); 
                    cBl++;
                }
            }
            for (int j=0; j < 3; j++) {
                for (int i=0; i < 5; i++) aliens.add(new AlienBa(300 + i * 200, 50 + j * 100));       
            }
        
            if(stage > 1) {
                gameState = "MENU";
                clip.stop();
                try {
                    File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceTriumpth.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<5; i++) aliens.add(new AlienDi(100 + i * 200, 40 + i * 50));
                
                if(stage > 2){
                    for (int i=0; i<4; i++) {
                        for (int j=0; j<2; j++) aliens.add(new AlienRu(300 + i * 250, 50 + j * 100));    
                    }
                
                    if (stage > 3){
                        for (int i=0; i<3; i++) aliens.add(new AlienSt(275 + i * 360, 50));
                        
                        if(stage > 4) {
                            clip.stop();
                            try {
                                File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceBoss.wav");
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                                clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                                clip.start();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                            aliens.add(new AlienBo(300, 50, true));
                            aliens.add(new AlienBo(600, 50, false));
                        }
                    }
                }
            }
        }
        if(stage == 6) {
            gameState = "VICTORY";
            clip.stop();
            try {
                File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceVictory.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            timer.stop();
            repaint();
        }
    }

    private void startReloading() {
        // Check if the spaceship is at either of the stations
        if (spaceship.getBounds().intersects(leftStation.getBounds()) || spaceship.getBounds().intersects(rightStation.getBounds())) {
            isReloading = true;
            reloadStartTime = System.currentTimeMillis();
        }
    }

    private void actionPerformed() {
        if (gameState.equals("IN_PROGRESS")) {
            if (left) spaceship.moveLeft(isOnDashCoolDown);
            if (right) spaceship.moveRight(isOnDashCoolDown);
            if (System.currentTimeMillis() - dashCooldownTime >= dashCoolDown) {
                    spaceship.setColor(Color.BLUE);
                    isOnDashCoolDown = false;
                    spaceship.setMoveEnable(true);
                    shild = false;
                    SHOOT_COOLDOWN = 500;
                } 
            // Handle reloading logic
            if (isReloading) {
                // Check if the spaceship is still at a station
                boolean isAtStation = spaceship.getBounds().intersects(leftStation.getBounds()) || spaceship.getBounds().intersects(rightStation.getBounds());
                if (!isAtStation) {
                    // Stop reloading if the spaceship leaves the station
                    isReloading = false;
                    reloadStartTime = 0;  // Reset reload timer if we leave the station
                }

                // Check if the reload time has finished
                if (isAtStation && System.currentTimeMillis() - reloadStartTime >= 1500) {
                    // Reload is done, allow shooting again and reset ammo counter
                    isReloading = false; 
                    bulletsFired = 0; // Reset ammo only when reload is done
                    spaceship.setSpeed(4);
                    pierce = 1;
                    mult = 1;
                    maxBullets = 5;
                    bulletSpeed = 10;
                    msg = "";
                    
                    // Handle station power-up
                    if (leftStation.getPowerUpReady() && leftStation.getBounds().intersects(spaceship.getBounds())) {
                        double roll = Math.random();
                        if(roll < 0.2){ spaceship.setSpeed(6); msg = "Speed Up";}
                        else if (roll < 0.4){ pierce = 2; msg = "Pierce Up";}
                        else if (roll < 0.6){ mult = 2; msg = "Mult Up";}
                        else if (roll < 0.8){ maxBullets = 9; msg = "Bullets Up";}
                        else {bulletSpeed = 15; msg = "Bullet Speed Up";}
                        leftStation.reload();
                    }
                    else if (rightStation.getPowerUpReady() && rightStation.getBounds().intersects(spaceship.getBounds())) {
                        double roll = Math.random();
                        if(roll < 0.2){ spaceship.setSpeed(6); msg = "Speed Up";}
                        else if (roll < 0.4){ pierce = 2; msg = "Pierce Up";}
                        else if (roll < 0.6){ mult = 2; msg = "Mult Up";}
                        else if (roll < 0.8){ maxBullets = 9; msg = "Bullets Up";}
                        else {bulletSpeed = 15; msg = "Bullet Speed Up";}
                        rightStation.reload();
                    }
                }
            }

            if (shooting && bulletsFired < maxBullets && !isReloading && (System.currentTimeMillis() - shootCooldownTime >= SHOOT_COOLDOWN)) {  // Prevent shooting while reloading
                bullets.add(new PlayerBullet(spaceship.getX() + spaceship.getWidth()/2, spaceship.getY(), bulletSpeed, pierce));
                bulletsFired++;
                shootCooldownTime = System.currentTimeMillis();
                shooting = false;
            }

            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.move();
                if (bullets.get(i).isOffScreen()) {
                    bullets.remove(i);
                    i--;
                }
            }
            
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                if(bullet instanceof PlayerBullet) {
                    for (int j = 0; j < aliens.size(); j++) {
                        Alien alien = aliens.get(j);
                        if (bullet.getBounds().intersects(alien.getBounds())) {
                            score += alien.getScore() * mult;
                            if (alien instanceof AlienBl) cBl --;
                            aliens.remove(j);
                            j--;  // Adjust the index after removal
                            bullet.setPierce(bullet.getPierce() - 1);
                            if (bullet.getPierce() <= 0) {
                                bullets.remove(i);
                                i--;  // Adjust the index after removal
                            }

                            if (Math.random() < dropChance && !(alien instanceof AlienBl)) {
                                blocks.add(new Block(alien.getX() + Alien.WIDTH / 2, alien.getY()));
                            }

                            // Check for victory condition
                            if (aliens.size() - cBl == 0) {
                                aliens.clear();
                                stage++;
                                initialize(stage);
                            }

                            break;  // Exit the inner loop once an alien is hit
                        }
                    }
                }
                else if(bullet instanceof EnemyBullet) {
                    if (bullet.getBounds().intersects(spaceship.getBounds())) {
                        if (!shild) {
                            spaceship.setColor(Color.gray);
                            isOnDashCoolDown = true;
                            dashCooldownTime = System.currentTimeMillis();
                            spaceship.setMoveEnable(false);
                            SHOOT_COOLDOWN = Integer.MAX_VALUE;
                            score -= 250;
                            if (score < 0) score = 0;
                        }
                        //else shild = false;
                        bullets.remove(bullet);
                    }
                }
            }


            for (Block block : blocks) {
                block.fall();
            }

            // Handle block pickup and station interaction
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (spaceship.getBounds().intersects(block.getBounds()) && !spaceship.getCarriedBlock()) {
                    spaceship.setCarriedBlock(true);
                    blocks.remove(i);
                    break;
                }
                else if (block.getY() > spaceship.getY()) {
                    blocks.remove(i);
                    break;
                }
            }

            // Handle power-up cooldown and reset stations when cooldown is over
            leftStation.update();
            rightStation.update();
            if (leftStation.getBounds().intersects(spaceship.getBounds()) && !leftStation.getIsActive() && spaceship.getCarriedBlock()) {
                leftStation.setIsActive(true);
                blocks.remove(spaceship.getCarriedBlock());
                spaceship.setCarriedBlock(false);
                powerUpDeliveredTime = System.currentTimeMillis();
                leftStation.setPowerUpCooldownStartTime(System.currentTimeMillis());  // Start the cooldown timer
            } else if (rightStation.getBounds().intersects(spaceship.getBounds()) && !rightStation.getIsActive() && spaceship.getCarriedBlock()) {
                rightStation.setIsActive(true);
                blocks.remove(spaceship.getCarriedBlock());
                spaceship.setCarriedBlock(false);
                powerUpDeliveredTime = System.currentTimeMillis();
                rightStation.setPowerUpReady(false);  // Block delivery is in cooldown for the right station
                rightStation.setPowerUpCooldownStartTime(System.currentTimeMillis());   // Start the cooldown timer
            }


            // Alien movement
            max = aliens.get(aliens.size() - 1).getY();
            if (max > spaceship.getY() - 300) max = spaceship.getY() - 300;
            for (Alien alien : aliens) {
                boolean goDown = false;
                alien.move(alienSpeed, max);  // Move the alien left or right
                if (alien.getX() <= 0) {
                    alien.setX(0);
                    alien.setMovingRight(true);  
                    goDown = true;
                    if(alien instanceof AlienBa) 
                        AlienBa.setMoveRight(true);                    
                }
         
                else if (alien.getX() >= screenSize.width - Alien.WIDTH * 2) {
                    alien.setX(screenSize.width - Alien.WIDTH * 2);
                    alien.setMovingRight(false);       
                    goDown = true;
                    if(alien instanceof AlienBa) 
                        AlienBa.setMoveRight(false);                  
                }
            
                if(goDown) {
                    alien.moveDown(); // Move aliens down after reversing direction
                    if(alien instanceof AlienBa) 
                        AlienBa.setMoveDown(true);
                }
                if(alien instanceof AlienDi && System.currentTimeMillis() - ((AlienDi)alien).getFireDelay() > fireDelay) {
                    bullets.add(new EnemyBullet(alien.getX() + alien.getWidth()/2, alien.getY(), 6, 18 ,7, Color.RED));
                    ((AlienDi)alien).setFireDelay(System.currentTimeMillis());
                }
                else if (alien instanceof AlienBo && System.currentTimeMillis() - ((AlienBo)alien).getFireDelay() > bossFireDelay && ((AlienBo)alien).getShotsFired() < 5) {
                    bullets.add(new EnemyBullet(alien.getX() + alien.getWidth()/2, alien.getY(), 35, 10 ,3, new Color(192, 192, 192)));
                    ((AlienBo)alien).setFireDelay(System.currentTimeMillis());
                    ((AlienBo)alien).setShotsFired();
                }
            }
            if(AlienBa.getMoveDown()) {
                for (Alien alien : aliens) {
                    if(alien instanceof AlienBa) alien.moveDown();
                }
            }
            if(System.currentTimeMillis() - AlienSt.getMoveCoolDown() > 100) AlienSt.setMoveCoolDown(System.currentTimeMillis());
            AlienBa.setMoveDown(false);

            // Check for defeat condition
            for (Alien alien : aliens) {
                if (alien.getY() >= 435) {
                    gameState = "DEFEAT";
                    clip.stop();
                    try {
                        File audioFile = new File("C:\\Users\\idoco\\Downloads\\invasionComplete.wav");
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                        clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }   
                    timer.stop();
                    repaint();
                    return;
                }
            }          
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState.equals("IN_PROGRESS")) {
            // Draw spaceship, bullets, aliens, etc.  
            ((Graphics2D)g).setColor(Color.gray);
            ((Graphics2D)g).setStroke(new BasicStroke(2));
            ((Graphics2D)g).drawLine(0, 450, screenSize.width, 450);
            
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("" + msg, 600, 300);
            
            spaceship.draw(g);
            
            
            for (Bullet bullet : bullets) {
                 bullet.draw(g);
            }

            for (Alien alien : aliens) {
                alien.draw(g);
            }

            for (Block block : blocks) {
                block.draw(g);
            }

            // Draw stations
            leftStation.draw(g);
            rightStation.draw(g);

            // Draw reload progress bar only for the station the player is at
            if (isReloading) {
                int reloadWidth = (int) ((System.currentTimeMillis() - reloadStartTime) / 15); // Calculate reload progress (half the original size)

                // If the spaceship is near the left station
                if (spaceship.getBounds().intersects(leftStation.getBounds())) {
                    g.setColor(new Color(0, 160, 0));
                    g.fillRect(50, 510, reloadWidth, 6);  // Left station reload progress (half size)
                    if(leftStation.getPowerUpReady()) {
                        g.setColor(new Color(255, 140, 0));
                        g.fillRect(50, 510, reloadWidth, 6);
                    }
                }

                // If the spaceship is near the right station
                if (spaceship.getBounds().intersects(rightStation.getBounds())) {
                    g.setColor(new Color(0, 160, 0));
                    g.fillRect(screenSize.width-150, 510, reloadWidth, 6);  // Right station reload progress (half size)
                    if(rightStation.getPowerUpReady()) {
                        g.setColor(new Color(255, 140, 0));
                        g.fillRect(screenSize.width-150, 510, reloadWidth, 6);
                    }
                }
            }

            // Draw ammo count following the spaceship's position
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("" + (maxBullets - bulletsFired), spaceship.getX() + 35, spaceship.getY() + 22);  // Ammo number above the spaceship
            g.setColor(Color.WHITE); // Set color to white for the score text
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size
            g.drawString("Score: " + score, 10, 30);  // Draw the score at (10, 30)
        } else if (gameState.equals("VICTORY")) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("You Win!", 600, 350);
            spaceship.setX(600);
            spaceship.draw(g);
            aliens.clear();
            initialize(5);
            if (!gameState.equals("NONE")) {
                clip.stop();
                try {
                    File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceVictory.wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
            for (Alien alien : aliens) {
                alien.draw(g);
            }
            gameState = "NONE";
        } else if (gameState.equals("DEFEAT")) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 600, 300);
            g.drawString("Score: " + score, 600, 350);
        }
        else if (gameState.equals("MENU")) {
            // Check for spacebar press to start the game
            g.setColor(Color.WHITE);  // Set text color to white
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 50));  // Combine bold and italic style
            g.drawString("Space Invaders", screenSize.width/2-175, 200);  // Draw the title of the game\
            g.setColor(Color.gray);
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
            g.drawString("[SPACE to start]", screenSize.width/2-100, 400);
            g.setColor(Color.yellow);
            g.drawString("stage " + stage, screenSize.width/2-50, 300);
            if (spacePressed) {
                gameState = "IN_PROGRESS";  // Transition to the "GAME" state when spacebar is pressed
                if(stage != 5){ 
                    clip.stop();
                    try {
                        File audioFile = new File("C:\\Users\\idoco\\Downloads\\spaceMusic.wav");
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                        clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(gameState.equals("NONE")) {
            
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        SpaceInvaders game = new SpaceInvaders();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
