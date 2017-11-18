package kom.hikeside;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import kom.hikeside.Atom.UserData;
import kom.hikeside.Game.Objects.GameCharacter;

/**
 * Created by Koma on 14.08.2017.
 */

public class Singleton {
    private static volatile Singleton instance = new Singleton();

    Singleton(){

    }
    public static Singleton getInstance(){
        return instance;
    }

    @Nullable
    public UserData userData = new UserData();//nullable
    @Nullable
    public GameCharacter currentGameCharacter = null;//nullable



    public LatLng myPosition;
    public FirebaseUser user;

    public Context context;
    public void setMyPosition(LatLng pos){
        if(pos.latitude!= 0 && pos.longitude !=0)
            myPosition = pos;
    }



}
