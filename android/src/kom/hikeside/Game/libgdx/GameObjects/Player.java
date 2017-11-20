package kom.hikeside.Game.libgdx.GameObjects;

import android.media.effect.Effect;

import java.util.concurrent.TimeUnit;

import kom.hikeside.Game.Mechanic.Randomizer;
import kom.hikeside.Game.Objects.GameCharacter;
import kom.hikeside.Game.Objects.Inventory.ItemTypesClassess.Armour;
import kom.hikeside.Game.libgdx.Entities.TexturedBody;
import kom.hikeside.Game.libgdx.GameMechanics.AttackModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Player extends GameObject{

    public GameCharacter gameCharacter;

    Armour armour = new Armour();


    public Player(TexturedBody playerView, GameCharacter gameCharacter, AttackModel attackModel){
        this.view = playerView;
        this.gameCharacter = gameCharacter;
        this.attackModel = attackModel;
        setCurrentHp(gameCharacter.getMaxHp());
        setCurrentMp(gameCharacter.getMaxMp());
        setCurrentStamina(gameCharacter.getMaxStamina());

        setMaxHp(gameCharacter.getMaxHp());
        setMaxMp(gameCharacter.getMaxMp());
        setMaxStamina(gameCharacter.getMaxStamina());
    }


    @Override
    public int getAttackValue(){
        int value = Randomizer.getAttackValue(attackModel);
        int add = 0;

        switch(gameCharacter.getGameClass()){
            case Archer:
                add = gameCharacter.buildStats.getAgility();
                break;
            case Warrior:
            case Knight:
                add = gameCharacter.buildStats.getStrength();
                break;
            case Mage:
            case Priest:
                add = gameCharacter.buildStats.getIntelligence();
                break;
        }

        if(value != 0)
            return value + add;
        else
            return 0;
    }
    public Effect getAttackEffect(){

        return new Effect() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public void apply(int inputTexId, int width, int height, int outputTexId) {

            }

            @Override
            public void setParameter(String parameterKey, Object value) {

            }

            @Override
            public void release() {

            }
        };

    }

    @Override
    public void ActionMove(){
        final float speed = 100;
        final int delay = 300;
        new Thread()
        {
            public void run() {
                view.getBody().setLinearVelocity(speed, 0);
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }catch(InterruptedException e){

                }
                view.getBody().setLinearVelocity(-speed, 0);
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                }catch(InterruptedException e){

                }
                view.getBody().setLinearVelocity(0,0);
                this.interrupt();
            }
        }.start();
    }

    public void getAttacked(AttackModel attackModel){
        int valueOfAttack = Randomizer.getAttackValue(attackModel);
        valueOfAttack -= armour.absorbDamage;

        this.stunned = Randomizer.getStun(attackModel);

        if(valueOfAttack > 0)
            this.setCurrentHp(this.getCurrentHp() - valueOfAttack);
        else{


        }
    }


}
