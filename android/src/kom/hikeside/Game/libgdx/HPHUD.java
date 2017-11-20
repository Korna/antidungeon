package kom.hikeside.Game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kom.hikeside.Game.libgdx.GameObjects.GameObject;

/**
 * Created by Koma on 09.09.2017.
 */
@Deprecated
public class HPHUD {


    private GameObject killable;

    private BitmapFont font;
    public HPHUD (GameObject killable) {

            this.killable = killable;

            font = new BitmapFont();
            font.setColor(Color.WHITE);

        }

        public void render(SpriteBatch sb){
            sb.begin();


            font.draw(sb, String.valueOf("HP" + killable.getCurrentHp()) + " / " + String.valueOf(killable.getMaxHp() ),
                    Gdx.graphics.getWidth() / 16, Gdx.graphics.getHeight() / 4);

            font.draw(sb, String.valueOf("MP" + killable.getCurrentMp()), Gdx.graphics.getWidth() / 6,
                    Gdx.graphics.getHeight()/4);


            sb.end();

        }

        public void render(SpriteBatch sb, float x, float y){

            sb.begin();
            font.draw(sb, "HP" + String.valueOf(killable.getCurrentHp()) + " / " + String.valueOf(killable.getMaxHp() ),
                    x, y);

            font.draw(sb, String.valueOf("MP" + killable.getCurrentMp()), x +  + Gdx.graphics.getWidth()/10,
                    y);

            sb.end();
        }




}
