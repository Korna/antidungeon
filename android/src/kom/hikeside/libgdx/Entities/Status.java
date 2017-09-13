package kom.hikeside.libgdx.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Koma on 12.09.2017.
 */

public class Status {
    private float x = 0;
    private float y = 0;

    private float width;
    private float height;
    private Texture texture;
    private final int TIME_SHOW_STATUS = 110;
    private boolean showing = true;

    public Status(Body body, Texture texture){
        x = body.getPosition().x - texture.getWidth() / 2;
        y = body.getPosition().y + 48;

        width = texture.getWidth();
        height = texture.getHeight();

        this.texture = texture;
    }
    float delta = 0;

    public void render(Batch batch){

        if(renderTimeListener()) {
            batch.begin();
            batch.draw(texture, x, y, width, height);
            batch.end();
        }



        /*

        sprite.setAlpha(1.0f - delta/110); // 0f is full transparent when 1f is full visible
        if(delta <= time) {
            delta += 2;
            y += 2;
            sprite.setCenterY(y);
            batch.begin();
            sprite.draw(batch);
            batch.end();
        }else{
            showing = false;
        }

         */
    }
    public boolean renderTimeListener(){
        if(delta <= TIME_SHOW_STATUS                                                                 ) {
            delta += 2;
            y += 2;
            return true;
        }else
            showing = false;

        return false;

    }

    public boolean isShowing() {
        return showing;
    }


}
