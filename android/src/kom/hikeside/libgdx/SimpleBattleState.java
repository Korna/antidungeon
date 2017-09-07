package kom.hikeside.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;

/**
 * Created by Koma on 17.01.2017.
 */
public class SimpleBattleState extends GameState {

    float acc = 0f;
    private float time = 3f;
    //private Texture tex_splash;
    private Stage stage;

    private World world;
    private Box2DDebugRenderer b2dr;

    PlayerView object;
    PlayerView enemy;

    public SimpleBattleState(GameStateManagement gsm){
        super(gsm);
       // tex_splash = Game.res.getTexture("splash");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buildTable();

        world = gsm.world;
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        object = createPlayer(createPlayerBody(GAME_WIDTH  / (8 * 2f), GAME_HEIGHT /  (3 * 2f)));
        enemy = createPlayer(createPlayerBody(GAME_WIDTH  / (1.5f * 2f), GAME_HEIGHT /  (2 * 2f)));
    }

    @Override
    public void update(float delta) {
        acc += delta;
        if(acc >= time){
            //gsm.setState(GameStateManagement.State.SPLASH);
        }

        object.update(delta);
        enemy.update(delta);

        stage.act(delta);

        doWorldStep(delta);
    }


    public static final float STEP = 1 / 60f;
    private float accumulator = 0;
    private void doWorldStep(float deltaTime){
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while(accumulator >= STEP){
            world.step(STEP, 6, 2);
            accumulator -= STEP;
        }

    }


    private void buildTable() {


        //creating font
        BitmapFont white = Game.res.getBitmapFont("white_font");

        TextureAtlas mainMenuAtlas = Game.res.getTextureAtlas("ui_buttons");
        Skin skin = new Skin(mainMenuAtlas);

        Table table = new Table(skin);
        table.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);


        // creating buttons
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.normal.up");
        textButtonStyle.down = skin.getDrawable("button.normal.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;





        table.add(addButton());


        stage.addActor(table);
    }

    TextButton.TextButtonStyle textButtonStyle;
    final int BUTTON_HEIGHT = GAME_WIDTH/10;
    final int BUTTON_WIDTH = GAME_WIDTH/10;


    private TextButton addButton(){

        final String text = "Attack";

        TextButton buttonPlay = new TextButton(text, textButtonStyle);
        buttonPlay.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        //buttonPlay.setHeight(BUTTON_HEIGHT);
        //buttonPlay.setWidth(BUTTON_WIDTH);

        buttonPlay.setTransform(true);
        buttonPlay.setScale(6.0f);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                makeAction(text);
                object.attack();

                notifyPlayers();

            }
        });

        return buttonPlay;
    }

    private void makeAction(String action){
        switch(action){
            case "Attack":

                break;
        }
    }

    private void notifyPlayers(){

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(maincamera.combined);
        batch.begin();

      //  batch.draw(tex_splash, GAME_WIDTH / 4 - tex_splash.getWidth() / 2, GAME_HEIGHT / 4 - tex_splash.getHeight() / 2);
        batch.end();

        if(true)
            b2dr.render(world, maincamera.combined);

        object.render(batch);
        enemy.render(batch);

        stage.draw();

    }

    private Body createPlayerBody(float x, float y) {
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

    private PlayerView createPlayer(Body body){
        PlayerView s = new PlayerView(body);
        return s;
    }

    @Override
    public void dispose() {
      //  tex_splash.dispose();
        b2dr.dispose();
        world.dispose();
    }

}