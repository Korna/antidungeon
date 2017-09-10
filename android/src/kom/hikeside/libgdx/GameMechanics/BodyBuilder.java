package kom.hikeside.libgdx.GameMechanics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static kom.hikeside.libgdx.Game.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;

/**
 * Created by Koma on 07.09.2017.
 */

public class BodyBuilder {
    int screen_width;
    int screen_height;
    World world;

    public BodyBuilder(int screen_width, int screen_height, World world) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.world = world;
    }

    enum BodySize{
        SMALL, MEDIUM, LARGE
    }

    private int[] getSize(BodySize bsize){
        switch(bsize){
            case SMALL:
                return new int[] {10, 10};
            case MEDIUM:
                return new int[] {20, 20};
            case LARGE:
                return new int[] {30, 30};
            default:
                return new int[] {10, 10};
        }
    }


    private Body createBoxBody(float x, float y, BodySize bsize) {
        FixtureDef fdef = new FixtureDef();
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getSize(bsize)[0], getSize(bsize)[1]);

        fdef.shape = shape;


        //fdef.filter.categoryBits = BIT_BORDER;
        def.position.set(x, y);
        Body pBody = this.world.createBody(def);
        pBody.createFixture(fdef);

        shape.dispose();

        return pBody;
    }
    private Body setInfo(Body body, String info){
        body.setUserData(info);
        return body;
    }

    public  Body createPlayerBody(float x, float y) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(x, y);

        CircleShape cshape = new CircleShape();
        cshape.setRadius(GAME_WIDTH/40);

        fdef.shape = cshape;
        fdef.isSensor = true;
        //    fdef.filter.categoryBits = BIT_ENEMY;
        //  fdef.filter.maskBits = BIT_PLAYER | BIT_BULLET | BIT_BORDER;
        fdef.isSensor = true;

        Body body = this.world.createBody(bdef);
        body.createFixture(fdef).setUserData(" ");
        return body;
    }






}
