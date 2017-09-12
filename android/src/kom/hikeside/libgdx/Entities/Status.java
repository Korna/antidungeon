package kom.hikeside.libgdx.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Koma on 12.09.2017.
 */

public class Status {
    float x = 0;
    float y = 0;
    Texture texture;
    int time = 110;
    boolean showing = true;

    public Status(Body body, Texture texture){
        x = body.getPosition().x;
        y = body.getPosition().y;
        this.texture = texture;
    }
    float delta = 0;
    public void render(Batch batch){

        if(delta <= time) {
            delta += 2;
            y += 2;
            batch.begin();
            batch.draw(texture, x - texture.getWidth()/2, y + 48, texture.getWidth(), texture.getHeight());
            batch.end();
        }else{
            showing = false;
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

    public boolean isShowing() {
        return showing;
    }

    public void dispose(){
        this.texture.dispose();
    }
}
