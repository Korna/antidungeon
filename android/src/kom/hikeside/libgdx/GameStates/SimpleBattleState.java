package kom.hikeside.libgdx.GameStates;

import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.style.LineBackgroundSpan;
import android.util.Log;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.Game.Objects.GameClasses.GameClass;
import kom.hikeside.libgdx.BundleToLib;
import kom.hikeside.libgdx.Entities.Status;
import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.Game;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.libgdx.GameMechanics.EnemyModel;
import kom.hikeside.libgdx.GameObjects.Enemy;
import kom.hikeside.libgdx.GameObjects.GameObject;
import kom.hikeside.libgdx.GameObjects.Player;
import kom.hikeside.libgdx.HPHUD;
import kom.hikeside.libgdx.LibraryObjects;
import kom.hikeside.libgdx.Managers.GameStateManagement;

import static kom.hikeside.Constants.OBJECT_ATTACK;
import static kom.hikeside.Constants.OBJECT_DEFENCE;
import static kom.hikeside.Constants.OBJECT_HEAL;
import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;
import static kom.warside.LibgdxGame.res;

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
    ArrayList<TexturedBody> texturedBodyArrayList = new ArrayList<>();

    HPHUD hpplayerhud;
    HPHUD hpenemyhud;
    Texture texture_ground;
    Texture texture_background;

    private ArrayList<Status> statusArrayList = new ArrayList<>();

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
            gameCharacter = LibraryObjects.getGameCharacter(GameClass.priest);
            Log.e("erroe", e.toString());
        }


        TexturedBody playerView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), gameCharacter.getGameClass().name());

        playerSelection = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), "selection_green");



        player = new Player(playerView, gameCharacter, new AttackModel(10,15, false, 0.8f));
        player.setSelectionTexture(Game.res.getTexture("selection_green"));
        hpplayerhud = new HPHUD(player);
    }

    private void loadEnemies(BundleToLib bundle, BodyBuilder bodyBuilder){

        String monsterId;
        try {
            monsterId = bundle.enemyNames.get(0);
            bundle.enemyNames.clear();
            Log.w("loaded name is", monsterId);
        }catch(Exception e){
            monsterId = Randomizer.simpleMonster();
            Log.e("erroe", e.toString());
        }

        enemy = LibraryObjects.getEnemy(monsterId);

        TexturedBody enemyView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), monsterId);

        enemySelection = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, GAME_HEIGHT /  (2 * 2f)), "selection_red");

        enemy.setGameObjectView(enemyView);
        enemy.setSelectionTexture(Game.res.getTexture("selection_red"));

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

        if(isGotTouch(player))
            player.setSelectedByTouch(!player.selectedByTouch());
        if(isGotTouch(enemy))
            enemy.setSelectedByTouch(!enemy.selectedByTouch());

        player.view.update(delta);
        enemy.view.update(delta);

        enemySelection.update(delta);
        playerSelection.update(delta);

        stage.act(delta);

        doWorldStep(delta);
    }

    private boolean isGotTouch(GameObject object){
        boolean gotTouch = false;
        if(Gdx.input.justTouched()){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f);
            maincamera.unproject(mousePos); // mousePos is now in world coordinates
            Vector2 enemyPot = object.view.getBody().getPosition();


            float enemyX = object.view.getWidth();
            float enemyY = object.view.getWidth();

            if(enemyPot.x - enemyX/2 < mousePos.x && enemyPot.x + enemyX/2 > mousePos.x)
                if(enemyPot.y - enemyY/2 < mousePos.y && enemyPot.y + enemyY/2 > mousePos.y){
                    Log.d("Hit", mousePos.x + " y:" +  mousePos.y);
                    gotTouch = true;
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


        table.add(addButton(OBJECT_ATTACK));
        table.row().pad(100);
        table.add(addButton(OBJECT_DEFENCE));
        table.row().pad(50);
        table.add(addButton(OBJECT_HEAL));


        stage.addActor(table);
    }

    TextButton.TextButtonStyle textButtonStyle;
    final int BUTTON_HEIGHT = GAME_WIDTH/10;
    final int BUTTON_WIDTH = GAME_WIDTH/10;



    private void makeAction(String action, GameObject from, GameObject to){
        float NATURAL_HP_AMOUNT = 0.1f;
        int NATURAL_MP_AMOUNT = 5;
       // from = player;
      //  to = enemy;

        switch(action){
            case OBJECT_ATTACK:
                from.ActionMove();

                if(to.isBlocking()) {
                    if (from.getAttackValue() != 0)
                        to.setCurrentHp(to.getCurrentHp() - from.getAttackValue());
                }else{
                    to.setCurrentHp(to.getCurrentHp() - from.getAttackValue());

                    //stun
                    int j = 1;
                    for(int i = 1; i <= 20; ++i){
                        if (from.getAttackValue() != 0)
                            ++j;
                    }
                    boolean makeStun = false;
                    if(j>=20)
                        makeStun = true;

                    if(makeStun){
                        Log.d("player", "is stunned");
                        to.setStunned(makeStun);
                        //TODO добавить шанс 2 атаки
                    }else
                        to.setStunned(makeStun);

                }
                statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));
                break;
            case OBJECT_DEFENCE:
                from.setBlocking(true);
                statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_DEFENCE)));
                break;
            case OBJECT_HEAL://можно ввести разные системы хила. мертвые всегда от максимума хиляют.а живые - от процента своего здоровья
                if(from.getCurrentHp() + from.getMaxHp()* NATURAL_HP_AMOUNT >= from.getMaxHp())
                    from.setCurrentHp(from.getMaxHp());
                else
                    from.setCurrentHp((int) (from.getCurrentHp() + from.getMaxHp() * NATURAL_HP_AMOUNT));
                statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_HEAL)));
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



        if(enemy.getCurrentHp() > 0){

            makeAction(Randomizer.action(), enemy, player);
        } else{
            boolean enemyIsDead = true;
          //  Gdx.app.exit();
        }

        if(player.getCurrentHp() <= 0){
            boolean playerIsDead = true;
            Gdx.app.exit();
            Log.w("you", "are dead");
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
            player.render(batch);
            hpplayerhud.render(batch, player.view.getPosition().x - player.view.getWidth() / 3, player.view.getPosition().y + player.view.getHeight() / 1.5f);
        }

        if(enemy.getCurrentHp() > 0){
            enemy.render(batch);
            hpenemyhud.render(batch, enemy.view.getPosition().x - enemy.view.getWidth() / 3, enemy.view.getPosition().y + enemy.view.getHeight() / 1.5f);
        }


        for(int i = 0; i < statusArrayList.size(); ++i){
            Status status = statusArrayList.get(i);
            if(status.isShowing())
                status.render(batch);
            else{
               // status.dispose();
                statusArrayList.remove(i);
            }

        }



    }

    private TexturedBody createTextured(Body body, String texture){
        Texture tex = Game.res.getTexture(texture);
        TexturedBody s = new TexturedBody(body, tex);
        return s;
    }



    private TextButton addButton(final String text){



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

                    makeAction(text, player, enemy);
                    turn();


                    notifyPlayers();
                }else{
                    Log.d("onLick", "wait for other player to attack!");
                }

            }
        });

        return buttonPlay;
    }



    @Override
    public void dispose() {
        texture_background.dispose();
        texture_ground.dispose();
        b2dr.dispose();
        world.dispose();
    }


}