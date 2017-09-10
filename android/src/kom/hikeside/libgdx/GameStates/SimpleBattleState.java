package kom.hikeside.libgdx.GameStates;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.Game.Objects.GameClasses.GameClass;
import kom.hikeside.libgdx.BundleToLib;
import kom.hikeside.libgdx.Game;
import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;
import kom.hikeside.libgdx.GameObjects.Enemy;
import kom.hikeside.libgdx.GameObjects.Player;
import kom.hikeside.libgdx.HPHUD;
import kom.hikeside.libgdx.Managers.GameStateManagement;

import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;

/**
 * Created by Koma on 17.01.2017.
 */
public class SimpleBattleState extends GameState {


    private float time = 3f;
    //private Texture tex_splash;
    private Stage stage;

    private World world;
    private Box2DDebugRenderer b2dr;

    //GameObjectView player;
    Player player;

    Enemy enemy;

    HPHUD hpplayerhud;
    HPHUD hpenemyhud;
    Texture texture_ground;
    Texture texture_background;
    public SimpleBattleState(GameStateManagement gsm){
        super(gsm);
        BundleToLib bundle = BundleToLib.getInstance();

        bundle.enemyModels.add(new EnemyModel(90,1,90, "EnemyName", "Description", null));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buildTable();

        world = gsm.world;
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();


        GameCharacter gameCharacter;
        try {
            gameCharacter = bundle.gameCharacters.get(0);
        }catch(Exception e){
            gameCharacter = new GameCharacter("Default mate char", 1, GameClass.priest, 0,0,0,0,0,0);
            Log.e("erroe", e.toString());
        }
        BodyBuilder bodyBuilder = new BodyBuilder(GAME_WIDTH/2, GAME_HEIGHT/2, world);

        GameObjectView playerView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), gameCharacter.getGameClass().name());
        GameObjectView enemyView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), Randomizer.simpleMonster());

        player = new Player(playerView, gameCharacter, new AttackModel(10,15, false, 0.8f));
        enemy = new Enemy(bundle.enemyModels.get(0), enemyView, new AttackModel(5,10, false, 0.5f));


        hpplayerhud = new HPHUD(player);
        hpenemyhud = new HPHUD(enemy);

        String[] textureNames = Randomizer.battleFieldTexture();
        texture_ground = Game.res.getTexture(textureNames[0]);
        texture_background = Game.res.getTexture(textureNames[1]);

    }

    float acc = 0f;
    private boolean timer(float delta){
        acc += delta;
        if(acc >= time){
            return true;
        }
        return false;
    }


    @Override
    public void update(float delta) {


        player.view.update(delta);
        enemy.view.update(delta);

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
        table.setBounds(0, 0, (GAME_WIDTH/2)/4, (GAME_HEIGHT/2)/4);


        // creating buttons
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.normal.up");
        textButtonStyle.down = skin.getDrawable("button.normal.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;




        table.add(addAttackButton());
        table.row().pad(100);
        table.add(addRestButton());
        table.row().pad(100);
        table.add(addDefenceButton());




        stage.addActor(table);
    }

    TextButton.TextButtonStyle textButtonStyle;
    final int BUTTON_HEIGHT = GAME_WIDTH/10;
    final int BUTTON_WIDTH = GAME_WIDTH/10;


    private TextButton addAttackButton(){

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
                if(player.turn) {
                    super.clicked(event, x, y);

                    makeAction(text);
                    turn();


                    notifyPlayers();
                }else{
                    Log.d("onLick", "wait for other player to attack!");
                }

            }
        });

        return buttonPlay;
    }

    private TextButton addRestButton(){
        final String text = "Rest";

        TextButton buttonPlay = new TextButton(text, textButtonStyle);
        buttonPlay.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        //buttonPlay.setHeight(BUTTON_HEIGHT);
        //buttonPlay.setWidth(BUTTON_WIDTH);

        buttonPlay.setTransform(true);
        buttonPlay.setScale(6.0f);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.turn) {
                    super.clicked(event, x, y);

                    makeAction(text);
                    turn();


                    notifyPlayers();
                }else{
                    Log.d("onLick", "wait for other player to attack!");
                }

            }
        });

        return buttonPlay;
    }

    private TextButton addDefenceButton(){
        final String text = "Defence";

        TextButton buttonPlay = new TextButton(text, textButtonStyle);
        buttonPlay.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        //buttonPlay.setHeight(BUTTON_HEIGHT);
        //buttonPlay.setWidth(BUTTON_WIDTH);

        buttonPlay.setTransform(true);
        buttonPlay.setScale(6.0f);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.turn) {
                    super.clicked(event, x, y);

                    makeAction(text);
                    turn();


                    notifyPlayers();
                }else{
                    Log.d("onLick", "wait for other player to attack!");
                }

            }
        });

        return buttonPlay;
    }

    private void makeAction(String action){
        int NATURAL_HP_AMOUNT = 10;
        int NATURAL_MP_AMOUNT = 5;

        switch(action){
            case "Attack":
                enemy.setCurrentHp(enemy.getCurrentHp() - player.getAttackValue());
                break;
            case "Defence":
                player.setBlocking(true);
                break;
            case "Rest":
                if(player.getCurrentHp()  + NATURAL_HP_AMOUNT > player.getMaxHp())
                    player.setCurrentHp(player.getMaxHp());
                else
                    player.setCurrentHp(player.getCurrentHp() + NATURAL_HP_AMOUNT);

                break;
        }
    }

    private void turn(){
        enemyAction();
        player.setBlocking(false);
        if(player.getCurrentHp() <= 0)
            Log.w("you", "are dead");

    }
    private void enemyAction(){
        if(player.isBlocking()) {
            if (enemy.getAttackValue() != 0)
                player.setCurrentHp(player.getCurrentHp() - enemy.getAttackValue());
        }else
            player.setCurrentHp(player.getCurrentHp() - enemy.getAttackValue());

    }

    private void notifyPlayers(){

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.81f, 0.933f,  0.933f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(maincamera.combined);
        batch.begin();
        batch.draw(texture_ground, 0, 0, (32*4)*5, (32*3)*5);
        batch.draw(texture_background, 0, (32*3)*5, (32*4)*5, (32*3)*5);
      //  batch.draw(tex_splash, GAME_WIDTH / 4 - tex_splash.getWidth() / 2, GAME_HEIGHT / 4 - tex_splash.getHeight() / 2);
        batch.end();

        if(true)
            b2dr.render(world, maincamera.combined);

        player.view.render(batch);
        enemy.view.render(batch);

        stage.draw();
        hpplayerhud.render(batch, player.view.getPosition().x - player.view.getWidth() / 3, player.view.getPosition().y + player.view.getHeight() / 1.5f);
        hpenemyhud.render(batch, enemy.view.getPosition().x - enemy.view.getWidth() / 3, enemy.view.getPosition().y + enemy.view.getHeight() / 1.5f);

    }



    private GameObjectView createTextured(Body body, String texture){
        GameObjectView s = new GameObjectView(body, texture);
        return s;
    }



    @Override
    public void dispose() {
      //  tex_splash.dispose();
        b2dr.dispose();
        world.dispose();
    }

}