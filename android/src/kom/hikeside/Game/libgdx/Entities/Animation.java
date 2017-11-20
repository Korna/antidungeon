package kom.hikeside.Game.libgdx.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Koma on 17.01.2017.
 */
public class Animation {
    private TextureRegion[] frames;
    private float time;
    private float delay;
    private int currentFrame;
    private int timesPlayed;

    public Animation() {}

    public Animation(TextureRegion[] frames){
        this(frames, 1/ 12f);
    }
    public Animation(TextureRegion[] frames, float delay){

    }

    public void setFrames(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void update(float dt){
        if(delay <= 0) return;
        time += dt;
        while(time >= delay){
            step();
        }
    }

    private void step(){
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length){
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame(){return frames[currentFrame];}
    public int getTimesPlayed(){return timesPlayed;}

}
