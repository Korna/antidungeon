package kom.hikeside.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kom.hikeside.Content.LibraryMonsters;
import kom.hikeside.Content.LibraryObjects;
import kom.hikeside.libgdx.Managers.ContentManager;
import kom.hikeside.libgdx.Managers.GameStateManagement;

import static kom.hikeside.Constants.AMOUNT_BODIES;
import static kom.hikeside.Constants.OBJECT_ATTACK;
import static kom.hikeside.Constants.OBJECT_DEFENCE;
import static kom.hikeside.Constants.OBJECT_HEAL;

public class Game extends ApplicationAdapter {
	public static ContentManager res;

	private SpriteBatch batch;
	private OrthographicCamera maincamera;
	private OrthographicCamera camera_hud;


	private boolean DEBUG = false;

	private final float SCALE = 2.0f;// раньше было 2f
	private GameStateManagement gsm;

	public static int GAME_WIDTH;

	public static int GAME_HEIGHT;

	@Override
	public void create () {
		GAME_WIDTH = (int)(Gdx.graphics.getWidth() / SCALE);
		GAME_HEIGHT = (int)(Gdx.graphics.getHeight() / SCALE);

		res = new ContentManager();
		loadAssets();

		//загрузка интерфейса


		batch = new SpriteBatch();
		maincamera = new OrthographicCamera();


		maincamera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);

		gsm = new GameStateManagement(this);

	}

	private void loadAssets(){
		int length = LibraryMonsters.values().length;
		for(int i = 0; i < length; ++i){
			res.loadTexture("monsters/" + LibraryMonsters.values()[i] + ".png", LibraryMonsters.values()[i].name());
		}

		int i = 1;
		res.loadTexture("heroes/hero_" + i + ".png", "Knight");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "Priest");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "Archer");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "Warrior");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "Mage");


		res.loadTexture("battle_backgrounds/grass.png", "grass_1");
		res.loadTexture("battle_backgrounds/grass_2.png", "grass_2");

		res.loadTexture("battle_backgrounds/forest.png", "forest");
		res.loadTexture("battle_backgrounds/dungeon.png", "dungeon");
		res.loadTexture("battle_backgrounds/castle.png", "castle");
		res.loadTexture("battle_backgrounds/wall.png", "wall");

		res.loadTexture("battle_backgrounds/village_1_up.png", "village_1_up");
		res.loadTexture("battle_backgrounds/village_1_down.png", "village_1_down");
		res.loadTexture("battle_backgrounds/village_2_up.png", "village_2_up");
		res.loadTexture("battle_backgrounds/village_2_down.png", "village_2_down");
		res.loadTexture("battle_backgrounds/mountains_1_up.png", "mountains_1_up");
		res.loadTexture("battle_backgrounds/mountains_1_down.png", "mountains_1_down");

		res.loadTexture("battle_backgrounds/orange_brick.png", "orange_brick");
		res.loadTexture("battle_backgrounds/brown_stone.png", "brown_stone");

		res.loadTexture("selection_red.png", "selection_red");
		res.loadTexture("selection_green.png", "selection_green");
		for(int j = 1; j <= AMOUNT_BODIES; ++j)
			res.loadTexture("dead_body_" + j + ".png", "dead_body_" + j);

		res.loadTexture("status/attack.png", "status_" + OBJECT_ATTACK);
		res.loadTexture("status/heal.png", "status_" + OBJECT_HEAL);
		res.loadTexture("status/defence.png", "status_" + OBJECT_DEFENCE);
		res.loadTexture("status/miss.png", "status_miss");

		res.loadBitmapFont("white16.fnt", "white_font");
		res.loadTextureAtlas("ui/ui.pack", "ui_buttons");

	}





	@Override
	public void render () {

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();

	}

	@Override
	public void dispose () {
		batch.dispose();
		gsm.dispose();
	}

	@Override
	public void resize (int width, int height) {
		gsm.resize((int)(width / SCALE), (int)(height / SCALE));//ресайзит всё. важный элемент
	}


	public OrthographicCamera getCamera(){
		return maincamera;
	}
	public OrthographicCamera getCameraHud(){
		return camera_hud;
	}
	public SpriteBatch getBatch(){
		return batch;
	}

}
