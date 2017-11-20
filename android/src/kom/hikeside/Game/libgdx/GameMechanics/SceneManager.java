package kom.hikeside.Game.libgdx.GameMechanics;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import kom.hikeside.Game.libgdx.Entities.StatusText;
import kom.hikeside.Game.libgdx.Game;
import kom.hikeside.Game.libgdx.GameObjects.GameObject;

import static com.badlogic.gdx.math.MathUtils.random;
import static kom.hikeside.Constants.AMOUNT_BODIES;
import static kom.hikeside.Constants.OBJECT_ATTACK;
import static kom.hikeside.Constants.OBJECT_DEFENCE;
import static kom.hikeside.Constants.OBJECT_HEAL;

/**
 * Created by Koma on 10.10.2017.
 */

public class SceneManager {
    private ArrayList<Action> journal = new ArrayList<>();
    public ArrayList<StatusText> statusArrayList = new ArrayList<>();





    public void makeAction(final String action, @NonNull GameObject from, @NonNull GameObject to){
        if(from == null || to == null)
            return;

        final float NATURAL_HP_AMOUNT = 0.1f;

        switch(action){
            case OBJECT_ATTACK:
                from.wasteStaminaForAttack();
                from.ActionMove();
                int value = from.getAttackValue();
                if (value != 0)
                    if(to.isBlocking()) {
                        value = from.getAttackValue();
                        if (value != 0){
                            to.setCurrentHp(to.getCurrentHp() - value);
                            statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));
                        }else
                            statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_miss")));

                    }else{
                        to.setCurrentHp(to.getCurrentHp() - value);
                        //stun
                        boolean makeStun = false;
                        int j = 1;

                        for(int i = 1; i <= 20; ++i){
                            if (from.getAttackValue() != 0)
                                ++j;
                        }
                        if(j>=20)
                            makeStun = true;

                        if(makeStun){
                            Log.d("player", "is stunned");
                            to.setStunned(true);
                            //TODO добавить шанс 2 атаки
                            statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));//stunned 'to'
                        }else{
                            to.setStunned(false);
                            statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_ATTACK)));
                        }


                    }
                else
                    statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_miss")));


                if(!to.hasHp()){//делаем врага мертвым
                    to.setDead(true);
                    to.setDeadTexture(Game.res.getTexture("dead_body_" + (random.nextInt(AMOUNT_BODIES)+1)));
                    to.setSelectedByTouch(false);

                }



                break;
            case OBJECT_DEFENCE:
                from.wasteStaminaForDefence();
                from.setBlocking(true);
                statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_DEFENCE)));
                break;
            case OBJECT_HEAL://можно ввести разные системы хила. мертвые всегда от максимума хиляют.а живые - от процента своего здоровья
                if(from.getCurrentHp() + from.getMaxHp()* NATURAL_HP_AMOUNT >= from.getMaxHp())
                    from.setCurrentHp(from.getMaxHp());
                else
                    from.setCurrentHp((int) (from.getCurrentHp() + from.getMaxHp() * NATURAL_HP_AMOUNT));
                statusArrayList.add(new StatusText(from.view.getBody(), Game.res.getTexture("status_" + OBJECT_HEAL)));
                break;
            case "Rest":
                break;
            case "Stun":

                break;
            case "Warcry":

                break;
        }
    }

}
