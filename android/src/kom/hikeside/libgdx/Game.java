package kom.hikeside.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kom.hikeside.libgdx.Managers.ContentManager;
import kom.hikeside.libgdx.Managers.GameStateManagement;

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
		GAME_WIDTH = Gdx.graphics.getWidth();
		GAME_HEIGHT = Gdx.graphics.getHeight();

		res = new ContentManager();
		loadAssets();

		//загрузка интерфейса


		batch = new SpriteBatch();
		maincamera = new OrthographicCamera();


		maincamera.setToOrtho(false, GAME_WIDTH/SCALE, GAME_HEIGHT/SCALE);

		gsm = new GameStateManagement(this);

	}

	private void loadAssets(){
		res.loadTexture("badlogic.jpg", "splash");
		for(int i = 1; i <=7; ++i)
			res.loadTexture("monsters/monster_" + i + ".png", "monster_" + i);
		int i = 1;
		res.loadTexture("heroes/hero_" + i + ".png", "knight");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "priest");
		++i;
		res.loadTexture("heroes/hero_" + i + ".png", "archer");
		++i;

		res.loadTexture("grass.png", "grass");
		res.loadTexture("wall.png", "wall");

		res.loadTexture("orange_brick.png", "orange_brick");
		res.loadTexture("brown_stone.png", "brown_stone");

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
