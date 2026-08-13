import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlamingoGame extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    int flamingoY = 250;
    int velocity = 0;
    int gravity = 1;
    boolean gameOver = false;
    int score = 0;

    ArrayList<Rectangle> obstacles = new ArrayList<>();
    Random rand = new Random();
    int frameCount = 0;

    int flamingoX = 100;
    int flamingoSize = 30;

    public FlamingoGame() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(20, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

      
        g.setColor(Color.PINK);
        g.fillOval(flamingoX, flamingoY, flamingoSize, flamingoSize);


        g.setColor(Color.ORANGE);
        g.drawLine(flamingoX + flamingoSize / 2, flamingoY + flamingoSize,
                flamingoX + flamingoSize / 2, flamingoY + flamingoSize + 15);


        g.setColor(Color.GREEN.darker());
        for (Rectangle r : obstacles) {
            g.fillRect(r.x, r.y, r.width, r.height);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 150, 250);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Press R to Restart", 210, 290);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            frameCount++;

            // Physics
            velocity += gravity;
            flamingoY += velocity;

            if (flamingoY > 470) {
                flamingoY = 470;
                gameOver = true;
            }
            if (flamingoY < 0) {
                flamingoY = 0;
                velocity = 0;
            }


            if (frameCount % 90 == 0) {
                int height = 50 + rand.nextInt(150);
                obstacles.add(new Rectangle(600, 500 - height, 40, height));
            }


            for (int i = obstacles.size() - 1; i >= 0; i--) {
                Rectangle r = obstacles.get(i);
                r.x -= 5;

                if (r.x + r.width < 0) {
                    obstacles.remove(i);
                    score++;
                }


                Rectangle flamingoBounds = new Rectangle(flamingoX, flamingoY, flamingoSize, flamingoSize);
                if (r.intersects(flamingoBounds)) {
                    gameOver = true;
                }
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            velocity = -12;
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    public void restartGame() {
        flamingoY = 250;
        velocity = 0;
        gameOver = false;
        score = 0;
        frameCount = 0;
        obstacles.clear();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flamingo Game");
        FlamingoGame game = new FlamingoGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
