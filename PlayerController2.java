import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayerController2 {
    public Rect rect;
    public KL keyListener;

    public boolean callParry1;
    private boolean parryActive1;
    private int parryCounter1;

    public PlayerController2(Rect rect, KL keyListener){
        this.rect = rect;
        this.keyListener = keyListener;
        this.callParry1 = false;
        this.parryActive1 = false;
        this.parryCounter1 = 0;
    }

    public void update(double dt){
        if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)){
            if((rect.y + Constants.PADDLE_SPEED * dt)+rect.height<Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM){
                this.rect.y = this.rect.y+Constants.PADDLE_SPEED*dt;
        }}

        else if (keyListener.isKeyPressed(KeyEvent.VK_UP)){
            if(rect.y - Constants.PADDLE_SPEED * dt>Constants.TOOLBAR_HEIGHT){
                this.rect.y = this.rect.y-Constants.PADDLE_SPEED*dt;
            }
        }
        else if (keyListener.isKeyPressed(KeyEvent.VK_SPACE) && !parryActive1){
            rect.color = Color.RED;
            callParry1 = true;
            parryActive1 = true;
            parryCounter1 = 300; // Set the parry duration in milliseconds
        }

        if (parryActive1) {
            parryCounter1 -= dt * 1000; // Decrease the counter based on the elapsed time
            if (parryCounter1 <= 0) {
                rect.color = Color.WHITE;
                callParry1 = false;
                parryActive1 = false;
            }
        }
            //make parry
        
    }
}
