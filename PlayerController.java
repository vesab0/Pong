
import java.awt.Color;
import java.awt.event.KeyEvent;


public class PlayerController {
    public Rect rect;
    public KL keyListener;

    public boolean callParry;
    private boolean parryActive;
    private int parryCounter;

    public PlayerController(Rect rect, KL keyListener){
        this.rect = rect;
        this.keyListener = keyListener;
        this.callParry = false;
        this.parryActive = false;
        this.parryCounter = 0;
    }

    public void update(double dt){
        if (keyListener.isKeyPressed(KeyEvent.VK_S)){
            if((rect.y + Constants.PADDLE_SPEED * dt)+rect.height<Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM){
                this.rect.y = this.rect.y+Constants.PADDLE_SPEED*dt;
        }}

        else if (keyListener.isKeyPressed(KeyEvent.VK_W)){
            if(rect.y - Constants.PADDLE_SPEED * dt>Constants.TOOLBAR_HEIGHT){
                this.rect.y = this.rect.y-Constants.PADDLE_SPEED*dt;
            }
        }
        else if (keyListener.isKeyPressed(KeyEvent.VK_E) && !parryActive){
            rect.color = Color.RED;
            callParry = true;
            parryActive = true;
            parryCounter = 300; // Set the parry duration in milliseconds
        }

        if (parryActive) {
            parryCounter -= dt * 1000; // Decrease the counter based on the elapsed time
            if (parryCounter <= 0) {
                rect.color = Color.WHITE;
                callParry = false;
                parryActive = false;
            }
        

            //make parry
        }
    }
}
