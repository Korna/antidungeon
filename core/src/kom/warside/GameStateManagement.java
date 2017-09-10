package kom.warside;

/**
 * Created by Koma on 09.01.2017.
 */


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Stack;


public class GameStateManagement {
    private final LibgdxGame app;


    public World world = new World(new Vector2(0, 0), false);;



    private Stack<BasicBattleState> states;
    public enum State{
        BASIC_BATTLE
    }

    public GameStateManagement(final LibgdxGame app){
        this.app = app;
        this.states = new Stack<>();


        this.setState(State.BASIC_BATTLE);

    }
    public LibgdxGame application(){
        return app;
    }

    public void update(float delta){
        states.peek().update(delta);

    }

    public void render(){
        states.peek().render();
    }

    public void dispose(){
        for(BasicBattleState gs : states){
            gs.dispose();
        }
        states.clear();
    }

    public void resize(int w, int h){
        states.peek().resize(w, h);

    }

    public void setState(State state){
        if(states.size() >= 1){
            states.pop().dispose();
        }
        states.push(getState(state));

    }

    private BasicBattleState getState(State state){
        switch(state){
            case BASIC_BATTLE: return new SplashState(this);
           // case MAINMENU: return new MenuState(this);
           // case PLAY: return new PlayState(this);
          //  case HUB: return new HubState(this);
        }
        return null;
    }

}
