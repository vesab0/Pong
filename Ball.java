public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;

    private double vy = 50;
    private double vx = 200;

    public int score = 0; 
    public int score2 = 0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle){
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
    }
    public double calculateNewVelocityAngle(Rect paddle){
        double relativeIntersectY = (paddle.y +(paddle.height/2.0)) - (this.rect.y +(this.rect.height/2.0));
        double normalIntersectY = relativeIntersectY / (paddle.height/2.0);
        double theta = normalIntersectY*Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }

    public void update (double dt){

        //player1 bounce
        if(vx<0){
            if(this.rect.x<=this.leftPaddle.x +this.leftPaddle.width && this.rect.y >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height && this.rect.x + this.rect.width>= this.leftPaddle.x){
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs((Math.cos(theta))* Constants.BALL_SPEED);
                double newVy = (Math.sin(theta))* Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            }

            //player2 win
            else if(this.rect.x + this.rect.width <this.leftPaddle.x){
                score2 +=1;
                System.out.println("player2 " + score2);
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
            }

            //player1 win
            else if(this.rect.x + this.rect.width > this.rightPaddle.x + this.rightPaddle.width){
                score +=1;
                System.out.println("player1 "+score);
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
    }
}

