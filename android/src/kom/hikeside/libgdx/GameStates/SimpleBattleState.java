package kom.hikeside.libgdx.GameStates;

import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;


import kom.hikeside.Content.LibraryMonsters;
import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Content.GameClass;
import kom.hikeside.libgdx.BundleToLib;
import kom.hikeside.libgdx.Entities.Status;
import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.Game;
import kom.hikeside.libgdx.GameMechanics.AttackModel;
import kom.hikeside.libgdx.GameMechanics.BodyBuilder;
import kom.hikeside.libgdx.GameObjects.Enemy;
import kom.hikeside.libgdx.GameObjects.GameObject;
import kom.hikeside.libgdx.GameObjects.Player;
import kom.hikeside.Content.LibraryObjects;
import kom.hikeside.libgdx.Managers.GameStateManagement;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.AMOUNT_BODIES;
import static kom.hikeside.Constants.OBJECT_ATTACK;
import static kom.hikeside.Constants.OBJECT_DEFENCE;
import static kom.hikeside.Constants.OBJECT_HEAL;
import static kom.warside.LibgdxGame.GAME_HEIGHT;
import static kom.warside.LibgdxGame.GAME_WIDTH;


/**
 * Created by Koma on 17.01.2017.
 */
public class SimpleBattleState extends GameState {//обычная одиночная битва. соло, пвп, пве с разным количеством атакующих со всех сторон


    private float time = 3f;
    private Stage stage;

    private World world;
    private Box2DDebugRenderer b2dr;


    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private ArrayList<Enemy> enemyArrayList = new ArrayList<>();

    private LinkedHashSet<Enemy> selectedEnemyHashSet = new LinkedHashSet<>();//хранит элементы в порядке вставки


    Texture texture_ground;
    Texture texture_background;

    private ArrayList<Status> statusArrayList = new ArrayList<>();

    public SimpleBattleState(GameStateManagement gsm){
        super(gsm);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(buildActionsTable());
        //stage.addActor(buildInfoTable());
        world = gsm.world;
        b2dr = new Box2DDebugRenderer();
        batch = new SpriteBatch();


        BundleToLib bundle = BundleToLib.getInstance();
        BodyBuilder bodyBuilder = new BodyBuilder(GAME_WIDTH/2, GAME_HEIGHT/2, world);


        Enemy enemy = loadEnemies(bundle, bodyBuilder);
        enemyArrayList.add(enemy);

        for(int i = 0; i < random.nextInt(3); ++i){
            enemyArrayList.add(loadEnemies(bundle, bodyBuilder));
        }

        Player player = loadCharacter(bundle, bodyBuilder);
        playerArrayList.add(player);



        String[] textureNames = Randomizer.battleFieldTexture();
        texture_ground = Game.res.getTexture(textureNames[0]);
        texture_background = Game.res.getTexture(textureNames[1]);



    }

    private Player loadCharacter(BundleToLib bundle, BodyBuilder bodyBuilder){
        GameCharacter gameCharacter;
        try {
            gameCharacter = bundle.gameCharacters.get(0);
        }catch(Exception e){
            gameCharacter = LibraryObjects.getGameCharacter(GameClass.Priest);
            Log.e("erroe", e.toString());
        }
        //просчет позиции героя, если он один
        float position = coordinateManager(0);
        int enemies = enemyArrayList.size();
        if(playerArrayList.size() + 1 == 1) {//добавляем единицу т.к только будет добавлены чары
            if (enemies == 2)
                position = coordinateManager(1) - (coordinateManager(1) - coordinateManager(0)) / 2;
            if (enemies == 3)
                position = coordinateManager(1);
        }else
            position = coordinateManager(playerArrayList.size() - 1);



        TexturedBody playerView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (8 * 2f) + 50, position), gameCharacter.getGameClass().name(), 4.5f);



        Player player = new Player(playerView, gameCharacter, new AttackModel(10,15, false, 0.8f));

        player.setSelectionTexture(Game.res.getTexture("selection_green"));
        return player;
    }

    private Enemy loadEnemies(BundleToLib bundle, BodyBuilder bodyBuilder){

        LibraryMonsters monsterId;
        try {
            monsterId = bundle.enemyNames.get(0);
            bundle.enemyNames.clear();
            Log.w("loaded name is", monsterId.name());
        }catch(Exception e){
            monsterId = Randomizer.simpleMonster();
            Log.e("error", e.toString());
        }

        Enemy enemy = LibraryObjects.getEnemy(monsterId);
        float textureScale = 4.5f;

        if(LibraryObjects.isBoss(monsterId))
            textureScale = 6.5f;

        TexturedBody enemyView = createTextured(bodyBuilder.createPlayerBody(GAME_WIDTH  / (1.5f * 2f) + 50, coordinateManager(enemyArrayList.size())), monsterId.name(), textureScale);


        enemy.setGameObjectView(enemyView);
        enemy.setSelectionTexture(Game.res.getTexture("selection_red"));

        return enemy;
    }

    private float coordinateManager(int amount){
        float positionChange = amount * -150;

        float position = GAME_HEIGHT /  (2 * 2f);
        return position + positionChange;

    }


    private float acc = 0f;
    private boolean timer(float delta){
        acc += delta;
        if(acc >= time){
            return true;
        }
        return false;
    }



    @Override
    public void update(float delta) {


        for(Player player : playerArrayList)
            if(isGotTouch(player) && player.hasHp())//TODO здесь условие сменить тогда, когда понадобится возможность что то делать с трупами
                player.setSelectedByTouch(!player.selectedByTouch());

        for(Enemy enemy : enemyArrayList)
            if(isGotTouch(enemy) && enemy.hasHp()){//TODO здесь условие сменить тогда, когда понадобится возможность что то делать с трупами(воскрешать, уничтожать итд)
                Log.d("touched", " enemy");
                enemy.setSelectedByTouch(!enemy.selectedByTouch());

                if(enemy.selectedByTouch())
                    selectedEnemyHashSet.add(enemy);
                else
                    selectedEnemyHashSet.remove(enemy);

            }


      /*  player.view.update(delta);
        enemy.view.update(delta);*//**апдейт body */



        stage.act(delta);

        doWorldStep(delta);
    }

    private boolean isGotTouch(GameObject object){//TODO: если 2+ выделены и один из них умирает, то последующие удары будут наноситься по мертвому
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

    private void doWorldStep(final float deltaTime){
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while(accumulator >= STEP){
            world.step(STEP, 6, 2);
            accumulator -= STEP;
        }

    }




    private void makeAction(final String action, @NonNull GameObject from, @NonNull GameObject to){
        if(from == null || to == null)
            return;

        final float NATURAL_HP_AMOUNT = 0.1f;

        switch(action){
            case OBJECT_ATTACK:
                from.wasteStaminaForAttack();
                from.ActionMove();
                int value = from.getAttackValue();
                if (value != 0)
                    if(to.isBlocking()) {
                        value = from.getAttackValue();
                        if (value != 0){
                            to.setCurrentHp(to.getCurrentHp() - value);
                            statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));
                        }else
                            statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_miss")));

                    }else{
                        to.setCurrentHp(to.getCurrentHp() - value);
                        //stun
                        boolean makeStun = false;
                        int j = 1;

                        for(int i = 1; i <= 20; ++i){
                            if (from.getAttackValue() != 0)
                                ++j;
                        }
                        if(j>=20)
                            makeStun = true;

                        if(makeStun){
                            Log.d("player", "is stunned");
                            to.setStunned(true);
                            //TODO добавить шанс 2 атаки
                            statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));//stunned 'to'
                        }else{
                            to.setStunned(false);
                            statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));
                        }


                    }
                else
                    statusArrayList.add(new Status(from.view.getBody(), Game.res.getTexture("status_miss")));


                if(!to.hasHp()){//делаем врага мертвым
                    to.setDead(true);
                    to.setDeadTexture(Game.res.getTexture("dead_body_" + (random.nextInt(AMOUNT_BODIES)+1)));
                    to.setSelectedByTouch(false);

                }



                break;
            case OBJECT_DEFENCE:
                from.wasteStaminaForDefence();
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

        for(Enemy enemy : enemyArrayList)
            if(enemy.hasHp())
                makeAction(Randomizer.action(), enemy, playerArrayList.get(random.nextInt(playerArrayList.size())));//TODO сделать другую систему определения


        for(Player player : playerArrayList)
            if(!player.hasHp()){
                Log.w("you", "are dead");
                Gdx.app.exit();
            }



            //clearing status effects
        for(Player player : playerArrayList)
            player.setBlocking(false);
        for(Enemy enemy : enemyArrayList)
            enemy.setBlocking(false);
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


        for(Player player : playerArrayList)
            if(player.hasHp()){
                player.render(batch);
            }

        for(Enemy enemy : enemyArrayList)
          // if(enemy.hasHp()){
                enemy.render(batch);
           // }


        for(int i = 0; i < statusArrayList.size(); ++i){
            Status status = statusArrayList.get(i);
            if(status.isShowing())
                status.render(batch);
            else{
                statusArrayList.remove(status);
            }

        }

        stage.draw();//TODO полное удаление stage, а не удаление отрисовки

    }

    private TexturedBody createTextured(Body body, String texture, float scale){
        Texture tex = Game.res.getTexture(texture);
        TexturedBody s = new TexturedBody(body, tex, scale);
        return s;
    }




    TextButton.TextButtonStyle textButtonStyle;
    private Table buildActionsTable() {

        //creating font
        BitmapFont white = Game.res.getBitmapFont("white_font");

        TextureAtlas mainMenuAtlas = Game.res.getTextureAtlas("ui_buttons");
        Skin skin = new Skin(mainMenuAtlas);

        Table table = new Table(skin);
        table.setBounds(0, 0, GAME_WIDTH/6, GAME_HEIGHT/5);


        // creating buttons
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.normal.up");
        textButtonStyle.down = skin.getDrawable("button.normal.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;

        final int BUTTON_HEIGHT = GAME_WIDTH/20;
        final int BUTTON_WIDTH = GAME_WIDTH/10;

        table.add(addButton(OBJECT_ATTACK)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        table.row().pad(100);
        table.add(addButton(OBJECT_DEFENCE)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        table.row().pad(1);
        table.add(addButton(OBJECT_HEAL)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);


        return table;
    }
    private Table buildInfoTable(){
        BitmapFont white = Game.res.getBitmapFont("white_font");

        TextureAtlas mainMenuAtlas = Game.res.getTextureAtlas("ui_buttons");
        Skin skin = new Skin(mainMenuAtlas);

        Table table = new Table(skin);
        table.setBounds(GAME_WIDTH*0.05f, (GAME_HEIGHT*0.9f)/2, GAME_WIDTH/2, GAME_HEIGHT/2);

        final int BUTTON_HEIGHT = GAME_WIDTH/20;
        final int BUTTON_WIDTH = GAME_WIDTH/10;

        table.add(addButton(OBJECT_ATTACK)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        table.row().pad(100);
        table.add(addButton(OBJECT_DEFENCE)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);
        table.row().pad(1);
        table.add(addButton(OBJECT_HEAL)).height(BUTTON_HEIGHT).width(BUTTON_WIDTH);

        return table;
    }
    private TextButton addButton(final String text){


        TextButton buttonPlay = new TextButton(text, textButtonStyle);


        buttonPlay.setTransform(true);
        buttonPlay.setScale(3.0f);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(playerArrayList.get(0).turn) {//TODO определенный игрок ходит
                    super.clicked(event, x, y);



                    //TODO улучшение системы
                    Enemy selectedEnemy = null;
                    for (Iterator<Enemy> it = selectedEnemyHashSet.iterator(); it.hasNext(); Log.d("iterator", "selected enemy count")){
                        selectedEnemy = it.next();
                    }

                    if(selectedEnemy == null) {
                        Log.e("error", "enemy not selected");
                        //берем по очереди всех врагов c последнего
                        for(Enemy enemyy : enemyArrayList)
                            if(enemyy.hasHp())
                                selectedEnemy = enemyy;
                    }

                    makeAction(text, playerArrayList.get(0), selectedEnemy);
                    turn();


                    notifyPlayers();
                }else{
                    Log.d("onCLick", "wait for other player to attack!");
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
        stage.dispose();
    }


}