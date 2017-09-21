package kom.hikeside.layoutCode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import kom.hikeside.Atom.UserData;
import kom.hikeside.FBDBHandler.UserDataFBHandler;
import kom.hikeside.FirebaseMultiQuery;
import kom.hikeside.MyValueEventListener;
import kom.hikeside.R;
import kom.hikeside.Singleton;

import static kom.hikeside.Constants.FB_DIRECTORY_MARKS;
import static kom.hikeside.Constants.FB_DIRECTORY_USERS;
import static kom.hikeside.Constants.FB_DIRECTORY_USER_DATA;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Singleton instance = Singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        instance.context = getApplicationContext();


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                instance.user = firebaseAuth.getCurrentUser();
                if (instance.user != null) {
                    Log.d("init", "onAuthStateChanged:signed_in:" + instance.user.getDisplayName());
                } else {
                    Log.d("init", "onAuthStateChanged:signed_out");
                }

            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        Task task = accountDataLoader().getTask();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Log.i("yay", "completed");

                instance.userData = (UserData) task.getResult();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    UserData userData = null;
    private TaskCompletionSource accountDataLoader(){
        String uid = "";
        try {
            uid = mAuth.getCurrentUser().getUid();
        }catch(NullPointerException e){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FB_DIRECTORY_USERS).child(uid).child(FB_DIRECTORY_USER_DATA);


        final TaskCompletionSource s = new TaskCompletionSource();

        ref.addListenerForSingleValueEvent(//глобальный и постоянный прослушиватель всех данных marks
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            userData = dataSnapshot.getValue(UserData.class);
                        }catch(DatabaseException e){
                            Log.e("cantTransfer", e.toString());
                            userData = null;
                        }

                        // TODO !fix this shit
                        // TODO fix this shit
                        if(userData != null) {
                            s.setResult(userData);

                            Singleton instance = Singleton.getInstance();
                            instance.userData = userData;

                            Log.i("onDataChange", "current game Char Loaded:" + userData.getCurrentCharacter());
                        }
                        else{
                            s.setResult(userData);
                            Log.e("onDataChange", "character not set");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        s.setException(databaseError.toException());
                    }
                });



       // MyValueEventListener heh = new MyValueEventListener(ref, s);

            return s;
        }



}
