package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.libgdx.Entities.GameObjectView;
import kom.hikeside.libgdx.GameMechanics.AttackModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Player {
    public boolean turn = true;
    public GameObjectView playerView;
    public GameCharacter gameCharacter;

    int currentHp;
    int currentMp;
    int currentStamina;

    AttackModel attackModel;
    public Player(GameObjectView playerView, GameCharacter gameCharacter){
        this.playerView = playerView;
        this.gameCharacter = gameCharacter;
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
}
