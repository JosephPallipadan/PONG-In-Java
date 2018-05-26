import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PONG extends JPanel{
    Ball ball;
    Paddle paddleOne;
    Paddle paddleTwo;

    int playerOneScore = 0;
    int playerTwoScore = 0;

    Timer gameTimer;
    ActionListener gameTimerListener;
    KeyListener playerOneInput;
    KeyListener playerTwoInput;

    public static void main(String[] args) {
        JFrame window = new JFrame("PONG");
        window.setContentPane(new PONG());
        window.setSize(3840, 2160);
        window.setUndecorated(true);
        window.setVisible(true);
    }

    PONG() {
        ball = new Ball(1920-Ball.SIZE, 1080-Ball.SIZE);
        paddleOne = new Paddle(0, 0);
        paddleTwo = new Paddle(3840-Paddle.WIDTH, 0);

        gameTimerListener = e -> {
            ball.x+=ball.xVelocity;
            ball.y+=ball.yVelocity;

            if (ball.y>=2160-Ball.SIZE || ball.y<=0) {
                ball.yVelocity *=-1;
            }

            if (ball.x>=3840-Paddle.WIDTH-Ball.SIZE || ball.x<Paddle.WIDTH) {
                if ( (ball.y>=paddleOne.y && ball.y<=paddleOne.y+Paddle.HEIGHT-Ball.SIZE) || (ball.y>=paddleTwo.y && ball.y<=paddleTwo.y+Paddle.HEIGHT-Ball.SIZE) )
                    ball.xVelocity *= -1;
                else
                {
                   if (ball.x>=3840-Paddle.WIDTH-Ball.SIZE)
                       playerOneScore++;
                   else
                       playerTwoScore++;

                    ball = new Ball(1920-Ball.SIZE, 1080-Ball.SIZE);
                    ball.xVelocity = randomVelocity();
                    ball.yVelocity = randomVelocity();
                }
            }


            repaint();
        };

        playerTwoInput = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (paddleTwo.y-Paddle.OFFSET>=0) {
                        paddleTwo.y-=Paddle.OFFSET;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (paddleTwo.y+Paddle.OFFSET+Paddle.HEIGHT<=2160) {
                        paddleTwo.y+=Paddle.OFFSET;
                    }
                }
            }
        };

        playerOneInput = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    if (paddleOne.y+Paddle.OFFSET+Paddle.HEIGHT<=2160) {
                        paddleOne.y+=Paddle.OFFSET;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    if (paddleOne.y-Paddle.OFFSET>=0) {
                        paddleOne.y-=Paddle.OFFSET;
                    }
                }
            }
        };

        addKeyListener(playerOneInput);
        addKeyListener(playerTwoInput);

        gameTimer=new Timer(5, gameTimerListener);
        gameTimer.start();
    }

    private int randomVelocity() {
        int direction = ( (int) (Math.random()*2) ) == 0 ? 1 : -1 ;
        return direction*( (int) (Math.random()*10+1) )*2;
    }

    public void paintComponent(Graphics g) {
        requestFocus();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3840, 2160);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 200));

        g.drawLine(1870, 0, 1870, 150);
        g.drawString(playerOneScore+"", 1700, 150);
        g.drawString(playerTwoScore+"", 1940, 150);
        g.drawString("PONG", 1600, 2150);

        ball.draw(g);
        paddleOne.draw(g);
        paddleTwo.draw(g);
    }

    class Ball {
        final static int SIZE = 40;

        int x, y;
        int xVelocity, yVelocity;

        void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(x, y, SIZE, SIZE);
        }

        Ball(int x, int y) {
            this.x = x;
            this.y = y;

            xVelocity = randomVelocity();
            yVelocity = randomVelocity();
        }
    }

    class Paddle {
        final static int WIDTH = 20;
        final static int HEIGHT = 216;
        final static int OFFSET = 40;

        int x, y;

        void draw(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, WIDTH, HEIGHT);
        }

        Paddle(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
