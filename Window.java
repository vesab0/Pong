import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect playerOne, playerTwo, ballRect;
    public PlayerController playerController;
    public PlayerController2 playerController2;
    public Ball ball;

    public Window(){
        this.setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        this.setTitle("pong");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;

        g2 = (Graphics2D)this.getGraphics();

        playerOne = new Rect(50,100,10,100);
        playerController = new PlayerController(playerOne, keyListener);
        playerTwo = new Rect(Constants.SCREEN_WIDTH-70,100, 10, 100);
        playerController2 = new PlayerController2(playerTwo, keyListener);
        ballRect = new Rect(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,15,15);
        ball = new Ball(ballRect, playerOne, playerTwo);
        
    }

    public void update(double dt){
            //System.out.println(dt + "seconds passed since the last frame");
            //System.out.println(1/dt +"fps");
        Image dbImage = createImage(getWidth(),getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage,0,0,this);

        playerController.update(dt);
        playerController2.update(dt);
        ball.update(dt);
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0 , Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Font font = new Font("Times New Roman", Font.PLAIN,14);
        Text text = new Text(Ball.score, font ,100,100);
        text.draw(g2);
        playerOne.draw(g2);
        playerTwo.draw(g2);
        ballRect.draw(g2);
    }

    @Override
    public void run(){
        double lastFrameTime = 0.0;
        while (true) {      
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);
        }
    }
}

