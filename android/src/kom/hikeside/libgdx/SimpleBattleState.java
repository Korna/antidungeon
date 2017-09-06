package kom.hikeside.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;

/**
 * Created by Koma on 17.01.2017.
 */
public class SimpleBattleState extends GameState {

    float acc = 0f;
    private float time = 3f;
    //private Texture tex_splash;

    private World world;
    private Box2DDebugRenderer b2dr;

    public SimpleBattleState(GameStateManagement gsm){
        super(gsm);
       // tex_splash = Game.res.getTexture("splash");
        world = gsm.world;
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void update(float delta) {
        acc += delta;
        if(acc >= time){
            //gsm.setState(GameStateManagement.State.SPLASH);
        }


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(maincamera.combined);
        batch.begin();

      //  batch.draw(tex_splash, GAME_WIDTH / 4 - tex_splash.getWidth() / 2, GAME_HEIGHT / 4 - tex_splash.getHeight() / 2);
        batch.end();

        if(true)
            b2dr.render(world, maincamera.combined);

        createAsteroid(GAME_WIDTH  / (4 * 2f), GAME_HEIGHT /  (2 * 2f)).render(batch);


    }

    private Sprite createAsteroid(float x, float y) {
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

        Asteroid s = new Asteroid(body);
        return s;
    }

    @Override
    public void dispose() {
      //  tex_splash.dispose();
        b2dr.dispose();
        world.dispose();
    }

}