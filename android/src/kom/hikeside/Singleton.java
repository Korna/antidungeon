package kom.hikeside;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Koma on 14.08.2017.
 */

public class Singleton {
    public String example = "Hello from source";
    public LatLng myPosition;
    public FirebaseAuth auth;
    public FirebaseUser user;
    public DatabaseReference myRef;

    public void setMyPosition(LatLng pos){
        if(pos.latitude!= 0 && pos.longitude !=0)
            myPosition = pos;
    }

    private static volatile Singleton instance = new Singleton();

    public static Singleton getInstance(){
        return instance;
    }

}
