package kom.hikeside.layoutCode;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kom.hikeside.R;
import kom.hikeside.Singleton;
import kom.hikeside.FBDBHandler.UserDataFBHandler;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button buttonRegister;
    private Button buttonSignUp;

    private EditText textEmail;
    private EditText textPass;


    UserDataFBHandler FBHandler;

    Singleton instance = Singleton.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                instance.user = firebaseAuth.getCurrentUser();
                if (instance.user != null) {
                    Log.d("init", "onAuthStateChanged:signed_in:" + instance.user.getUid());
                } else {
                    Log.d("init", "onAuthStateChanged:signed_out");
                }

            }
        };


        FBHandler = new UserDataFBHandler(mAuth.getCurrentUser().getUid());

        textEmail = (EditText) findViewById(R.id.editText_email);
        textPass = (EditText) findViewById(R.id.editText_pass);

        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String email = textEmail.getText().toString();
                String pass = textPass.getText().toString();
                textEmail.setText("");
                textPass.setText("");
                createAccount(email, pass);

            }
        });

        buttonSignUp = (Button) findViewById(R.id.button_auth);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String email = textEmail.getText().toString();
                String pass = textPass.getText().toString();
                textEmail.setText("");
                textPass.setText("");
                signUp(email, pass);
                instance.userData = FBHandler.getUserData();



            }
        });
    }

    private void createAccount(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                        }else{
                            instance.userData = FBHandler.createNewUserData();
                            Toast.makeText(LoginActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                            //TODO offerToUpdateAccountData();
                        }

                    }
                });

    }



    private void signUp(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Заход провалена", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Заход прошла успешно", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

    private void getCurrentUser(){
        FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}


