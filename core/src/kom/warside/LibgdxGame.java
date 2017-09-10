package kom.warside;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LibgdxGame extends ApplicationAdapter {


	public static Content res;

	private SpriteBatch batch;
	private OrthographicCamera maincamera;
	private OrthographicCamera camera_hud;


	private boolean DEBUG = false;

	private final float SCALE = 2.0f;// раньше было 2f
	private GameStateManagement gsm;

	public static final int GAME_WIDTH = Gdx.graphics.getWidth();

	public static final int GAME_HEIGHT = Gdx.graphics.getHeight();

	@Override
	public void create () {
		res = new Content();
		loadAssets();

		batch = new SpriteBatch();
		maincamera = new OrthographicCamera();
		maincamera.setToOrtho(false, GAME_WIDTH/SCALE, GAME_HEIGHT/SCALE);

		gsm = new GameStateManagement(this);
	}

	private void loadAssets(){
		res.loadTexture("assets/badlogic.jpg", "splash");
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
