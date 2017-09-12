package kom.hikeside.libgdx.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Koma on 19.01.2017.
 */
public class TexturedBody extends MySprite {

    float x;
    float y;

    public TexturedBody(Body body, Texture tex) {
        super(body);
        x = body.getPosition().x;
        y = body.getPosition().y;

        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];

        setAnimation(sprites, 1 / 12f);

    }

    public void attack(){

        getBody().setLinearVelocity(1000, getBody().getLinearVelocity().y);

    }

    public void returnAfterAttack(){
        getBody().setLinearVelocity(-1000, getBody().getLinearVelocity().y);

    }

    public void getHit(){


    }



}

