package kom.hikeside;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import kom.hikeside.Atom.UserData;
import kom.hikeside.Game.Objects.GameClasses.GameCharacter;

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

    public UserData userData = new UserData();
    public GameCharacter currentGameCharacter = null;

    public LatLng myPosition;
    public FirebaseUser user;
    public DatabaseReference myRef;

    public void setMyPosition(LatLng pos){
        if(pos.latitude!= 0 && pos.longitude !=0)
            myPosition = pos;
    }



}
