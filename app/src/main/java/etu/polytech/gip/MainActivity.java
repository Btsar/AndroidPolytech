package etu.polytech.gip;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //UI reference
    EditText mEmail, mPassword;
    Button signIn, signOut, signUp;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.email_sign_in_button);
        signUp = (Button) findViewById(R.id.email_sign_up_button);
        signOut = (Button) findViewById(R.id.email_sign_out_button);


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null){
                    //user signed in
                    Log.d(TAG,"onAuthStateChanged:signed_in"+ user.getUid());
                    toastTxt("Connecté en tant que" + user.getEmail());
                }else{
                    //user signed out
                    Log.d(TAG,"onAuthStateChanged:signed_out");
                    toastTxt("Déconnecté");
                }
            }
        };

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmail.getText().toString();
                String pass = mPassword.getText().toString();

                if(!mail.equals("") && !pass.equals("")){
                    mAuth.signInWithEmailAndPassword(mail,pass);
                    toastTxt("Connexion en cours ...");
                }else {
                    toastTxt("Champ vide");
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                toastTxt("Déconnexion en cours ...");
            }
        });


    }

    private void toastTxt(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}

