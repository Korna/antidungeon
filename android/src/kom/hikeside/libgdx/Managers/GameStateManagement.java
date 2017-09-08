package kom.hikeside.libgdx.Managers;

/**
 * Created by Koma on 09.01.2017.
 */


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Stack;

import kom.hikeside.libgdx.Game;
import kom.hikeside.libgdx.GameStates.GameState;
import kom.hikeside.libgdx.GameStates.SimpleBattleState;


public class GameStateManagement {
    private final Game app;

    public World world = new World(new Vector2(0, 0), false);



    private Stack<GameState> states;
    public enum State{
        SPLASH,
        MAINMENU,
        PLAY,
        HUB
    }

    public GameStateManagement(final Game app){
        this.app = app;
        this.states = new Stack<>();


        this.setState(State.SPLASH);

    }
    public Game application(){
        return app;
    }

    public void update(float delta){
        states.peek().update(delta);

    }

    public void render(){
        states.peek().render();
    }

    public void dispose(){
        for(GameState gs : states){
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

    private GameState getState(State state){
        switch(state){

            case SPLASH: return new SimpleBattleState(this);
           // case MAINMENU: return new MenuState(this);
           // case PLAY: return new PlayState(this);
          //  case HUB: return new HubState(this);
        }
        return null;
    }

}
