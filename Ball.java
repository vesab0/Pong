import java.util.Random;
import javax.swing.Timer;

public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;

    private double vy = 50;
    private double vx = 200;

    public int score = 0; 
    public int score2 = 0;

    public boolean parry;
    public boolean parry1;
    
    Window window ;
    PlayerController playerController;
    PlayerController2 playerController2;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Window window, PlayerController playerController, PlayerController2 playerController2){
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.window = window;
        this.playerController = playerController;
        this.playerController2 = playerController2;
    }
    public double calculateNewVelocityAngle(Rect paddle){
        double relativeIntersectY = (paddle.y +(paddle.height/2.0)) - (this.rect.y +(this.rect.height/2.0));
        double normalIntersectY = relativeIntersectY / (paddle.height/2.0);
        double theta = normalIntersectY*Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }

    public void update (double dt){

        if(playerController.callParry){
            parry();
        }
        if(playerController2.callParry1){
            parry1();
        }

        //player1 bounce
        if(vx<0){
            if(this.rect.x<=this.leftPaddle.x +this.leftPaddle.width && this.rect.y >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height && this.rect.x + this.rect.width>= this.leftPaddle.x){
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs((Math.cos(theta))* Constants.BALL_SPEED);
                double newVy = (-Math.sin(theta))* Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;

                if (parry == true){
                    vx = vx*2;
                    Timer timer = new Timer(500, event -> vx = vx/2);
                    timer.setRepeats(false);
                    timer.start();
                }
            }

            //player2 win
            else if(this.rect.x + this.rect.width <this.leftPaddle.x){
                score2 +=1;
                System.out.println("player2 " + score2);
                window.scored1();
                recenter();
            }
        }
        //player2 bounce
        else if (vx > 0){
            if(this.rect.x + this.rect.width>=this.rightPaddle.x && this.rect.y >= this.rightPaddle.y && this.rect.y <= this.rightPaddle.y + this.rightPaddle.height && this.rect.x <= this.rightPaddle.x + this.rightPaddle.width){
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs((Math.cos(theta))* Constants.BALL_SPEED);
                double newVy = (-Math.sin(theta))* Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;

                if (parry1 == true){
                    vx = vx*2;
                    Timer timer1 = new Timer(500, event -> vx = vx/2);
                    timer1.setRepeats(false);
                    timer1.start();
                }
            }

            //player1 win
            else if(this.rect.x + this.rect.width > this.rightPaddle.x + this.rightPaddle.width){
                score +=1;
                System.out.println("player1 "+score);
                window.scored();
                recenter();
            }

        }

        //bounce on edge
        if (vy > 0 ){
            if(this.rect.y +this.rect.height> Constants.SCREEN_HEIGHT){
                this.vy *= -1;
            }
        } else if (vy<0){
            if(this.rect.y<Constants.TOOLBAR_HEIGHT){
                this.vy *= -1;
            }
        }

        this.rect.x += vx*dt;
        this.rect.y += vy*dt;

    }
    public void recenter (){
        this.rect.x = Constants.SCREEN_WIDTH/2;
        this.rect.y = Constants.SCREEN_HEIGHT/2;
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI; // Random angle between 0 and 2π radians
        double speed = Constants.BALL_SPEED; // Set the desired speed of the ball
        vx = speed * Math.cos(angle);
        vy = speed * Math.sin(angle);
    }
    public void parry(){
        parry = true;
        Timer timer3 = new Timer(300, event -> parry = false);
                    timer3.setRepeats(false);
                    timer3.start();
    }
    public void parry1(){
        parry1 = true;
        Timer timer4 = new Timer(300, event -> parry1 = false);
                    timer4.setRepeats(false);
                    timer4.start();
    }
}

