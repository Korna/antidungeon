package kom.hikeside.libgdx.GameObjects;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.GameMechanics.AttackModel;

/**
 * Created by Koma on 09.09.2017.
 */

public abstract class GameObject {
    public boolean turn = true;

    boolean dead = false;

    boolean blocking = false;
    boolean stunned = false;
    boolean bleeding = false;

    int currentHp;
    int currentMp;
    int currentStamina;

    int maxHp;
    int maxMp;
    int maxStamina;

    AttackModel attackModel;//basic attack

    public TexturedBody view;
    Texture selection;
    boolean selectedByTouch = false;
    public String basicTexture;



    //HUD
    final boolean drawHud = true;
    private BitmapFont font;

    public GameObject(){
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void render(SpriteBatch batch){
        view.render(batch);

        if(this.selectedByTouch){
            batch.begin();
            batch.draw(selection, view.getBody().getPosition().x - view.getWidth() / 2, view.getBody().getPosition().y - view.getHeight() / 2, view.getWidth(), view.getHeight());


            batch.end();
        }

        if(this.drawHud){

            batch.begin();
            float x = this.view.getPosition().x - this.view.getWidth() / 3;
            float y = this.view.getPosition().y + this.view.getHeight() / 1.5f;

            font.draw(batch, "HP" + String.valueOf(this.getCurrentHp()) + " / " + String.valueOf(this.getMaxHp() ),
                    x, y);

            font.draw(batch, String.valueOf("MP" + this.getCurrentMp()),  x  + Gdx.graphics.getWidth()/10,
                    y);
            batch.end();
        }

    }
    public void action(Runnable action){

    }


    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(int currentMp) {
        this.currentMp = currentMp;
    }

    public int getCurrentStamina() {
        return currentStamina;
    }

    public void setCurrentStamina(int currentStamina) {
        this.currentStamina = currentStamina;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public int getAttackValue(){
        return Randomizer.getAttackValue(attackModel);
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public void setSelectionTexture(Texture selection){
        this.selection = selection;
    }

    public boolean selectedByTouch() {
        return selectedByTouch;
    }

    public void setSelectedByTouch(boolean selectedByTouch) {
        this.selectedByTouch = selectedByTouch;
    }

    public boolean isDead() {
        return dead;
    }

    private void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean hasHp(){//checks health value
        if(getCurrentHp() > 0){
            setDead(false);
            return true;
        }
        else{
            setDead(true);
            return false;
        }

    }


    public abstract void ActionMove();
}
