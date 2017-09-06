package kom.warside;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

/**
 * Created by Koma on 15.01.2017.
 */
public class Content {

    private HashMap<String, Texture> textures;
    private HashMap<String, Music> music;
    private HashMap<String, Sound> sounds;
    private HashMap<String, BitmapFont>bitmapFonts;

    public Content(){
        textures = new HashMap<String, Texture>();
        music = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();
        bitmapFonts = new HashMap<String, BitmapFont>();

    }

    public void loadTexture(String path, String key){
        Texture tex = new Texture(Gdx.files.internal(path));
        textures.put(key, tex);
    }
    public Texture getTexture(String key){
        return textures.get(key);
    }

    public void disposeTexture(String key){
        Texture tex = textures.get(key);
        if(tex != null) tex.dispose();
    }

    public void loadMusic(String path, String key) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, m);
    }

    public Music getMusic(String key) {
        return (Music)music.get(key);
    }

    public void removeMusic(String key) {
        Music m = (Music)music.get(key);
        if(m != null) {
            music.remove(key);
            m.dispose();
        }
    }

    public void loadBitmapFont(String path, String key){
        BitmapFont font = new BitmapFont(Gdx.files.internal(path));
        bitmapFonts.put(key, font);
    }

    public BitmapFont getBitmapFont(String key){
        return (BitmapFont) bitmapFonts.get(key);
    }

    public void loadSound(String path, String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, sound);
    }

    public Sound getSound(String key) {
        return (Sound)sounds.get(key);
    }

    public void removeSound(String key) {
        Sound sound = (Sound)this.sounds.get(key);
        if(sound != null) {
            this.sounds.remove(key);
            sound.dispose();
        }

    }

}
