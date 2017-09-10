package kom.hikeside.libgdx.GameObjects;

import kom.hikeside.Game.Objects.GameClasses.GameCharacter;
import kom.hikeside.libgdx.Entities.TexturedBody;
import kom.hikeside.libgdx.GameMechanics.AttackModel;

/**
 * Created by Koma on 08.09.2017.
 */

public class Player extends GameObject{

    public GameCharacter gameCharacter;




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


}
