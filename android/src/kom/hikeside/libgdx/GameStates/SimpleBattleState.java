package kom.hikeside.libgdx.GameStates;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.Game;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;
import kom.hikeside.libgdx.GameObjects.Enemy;
import kom.hikeside.libgdx.GameObjects.Player;
import kom.hikeside.libgdx.HPHUD;
import kom.hikeside.libgdx.LibraryObjects;
import kom.hikeside.libgdx.Managers.GameStateManagement;

import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;

/**
 * Created by Koma on 17.01.2017.
 */
public class SimpleBattleState extends GameState {//обычная одиночная битва. соло, пвп, пве с разным количеством атакующих со всех сторон


    private float time = 3f;
    //private Texture tex_splash;
    private Stage stage;

    private World world;
    private Box2DDebugRenderer b2dr;


    Player player;
    TexturedBody playerSelection;

    Enemy enemy;
    TexturedBody enemySelection;

    HPHUD hpplayerhud;
    HPHUD hpenemyhud;
    Texture texture_ground;
    Texture texture_background;


    public SimpleBattleState(GameStateManagement gsm){
        super(gsm);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buildTable();
        world = gsm.world;
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();


        BundleToLib bundle = BundleToLib.getInstance();
        BodyBuilder bodyBuilder = new BodyBuilder(GAME_WIDTH/2, GAME_HEIGHT/2, world);

        loadCharacter(bundle, bodyBuilder);
        loadEnemies(bundle, bodyBuilder);


        String[] textureNames = Randomizer.battleFieldTexture();
        texture_ground = Game.res.getTexture(textureNames[0]);
        texture_background = Game.res.getTexture(textureNames[1]);

    }

    private void loadCharacter(BundleToLib bundle, BodyBuilder bodyBuilder){
        GameCharacter gameCharacter;
        try {
            gameCharacter = bundle.gameCharacters.get(0);
        }catch(Exception e){
            gameCharacter = new GameCharacter("Default mate char", 1, GameClass.priest, 0,0,0,0,0,0);
            Log.e("erroe", e.toString());
        }


        TexturedBody playerView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), gameCharacter.getGameClass().name());

        playerSelection = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), "selection_green");



        player = new Player(playerView, gameCharacter, new AttackModel(10,15, false, 0.8f));
        hpplayerhud = new HPHUD(player);
    }

    private void loadEnemies(BundleToLib bundle, BodyBuilder bodyBuilder){

        String monsterId = Randomizer.simpleMonster();

        enemy = LibraryObjects.getEnemy(monsterId);

        bundle.enemyModels.add(LibraryObjects.getEnemyModel("model_1"));

        TexturedBody enemyView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), monsterId);

        enemySelection = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), "selection_red");

        enemy.setGameObjectView(enemyView);

        hpenemyhud = new HPHUD(enemy);
    }


    float acc = 0f;
    private boolean timer(float delta){
        acc += delta;
        if(acc >= time){
            return true;
        }
        return false;
    }


    boolean enemySelected = false;
    @Override
    public void update(float delta) {
        isGotTouch();

        player.view.update(delta);
        enemy.view.update(delta);

        enemySelection.update(delta);
        playerSelection.update(delta);

        stage.act(delta);

        doWorldStep(delta);
    }

    private boolean isGotTouch(){
        boolean gotTouch = false;
        if(Gdx.input.justTouched()){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f);
            maincamera.unproject(mousePos); // mousePos is now in world coordinates
            Vector2 enemyPot = enemy.view.getBody().getPosition();


            float enemyX = enemy.view.getWidth();
            float enemyY = enemy.view.getWidth();

            if(enemyPot.x - enemyX/2 < mousePos.x && enemyPot.x + enemyX/2 > mousePos.x)
                if(enemyPot.y - enemyY/2 < mousePos.y && enemyPot.y + enemyY/2 > mousePos.y){
                    Log.d("Hit", mousePos.x + " y:" +  mousePos.y);
                    gotTouch = true;
                    enemySelected = !enemySelected;
                }


        }
        return gotTouch;
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
        table.row().pad(50);
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
        final String text = "Heal";

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
            case "Heal":
                if(player.getCurrentHp()  + NATURAL_HP_AMOUNT > player.getMaxHp())
                    player.setCurrentHp(player.getMaxHp());
                else
                    player.setCurrentHp(player.getCurrentHp() + NATURAL_HP_AMOUNT);
                break;
            case "Rest":
                break;
            case "Stun":

                break;
            case "Warcry":

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
        }else{
            player.setCurrentHp(player.getCurrentHp() - enemy.getAttackValue());

            //stun
            int j = 1;
            for(int i = 1; i <= 20; ++i){
                if (enemy.getAttackValue() != 0)
                    ++j;
            }
            boolean makeStun = false;
            if(j>=20)
                makeStun = true;
            if(makeStun){
                Log.d("player", "is stunned");
                player.setStunned(true);
                enemyAction();
            }else
                player.setStunned(false);

        }



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
        batch.end();

        if(true)
            b2dr.render(world, maincamera.combined);

        if(player.getCurrentHp() > 0){
            stage.draw();//TODO полное удаление stage, а не удаление отрисовки
            player.view.render(batch);
            hpplayerhud.render(batch, player.view.getPosition().x - player.view.getWidth() / 3, player.view.getPosition().y + player.view.getHeight() / 1.5f);
        }

        if(enemy.getCurrentHp() > 0){
            enemy.view.render(batch);
            hpenemyhud.render(batch, enemy.view.getPosition().x - enemy.view.getWidth() / 3, enemy.view.getPosition().y + enemy.view.getHeight() / 1.5f);
        }


        if(enemySelected)
            enemySelection.render(batch);

        if(false)
            playerSelection.render(batch);






    }



    private TexturedBody createTextured(Body body, String texture){
        Texture tex = Game.res.getTexture(texture);
        TexturedBody s = new TexturedBody(body, tex);
        return s;
    }



    @Override
    public void dispose() {
      //  tex_splash.dispose();
        b2dr.dispose();
        world.dispose();
    }

}