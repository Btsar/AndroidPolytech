package etu.polytech.gip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TypeListActivity extends AppCompatActivity implements View.OnClickListener{

    //Button
    Button buttonOrdi;
    Button buttonChauff;
    Button buttonImpr;
    Button buttonProbl;
    Button buttonCafe;
    Button buttonAutres;
    Button buttonElec;
    Button buttonChat;
    String extra;
    Integer photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        findViewById(R.id.buttonOrdi).setOnClickListener(this);
        findViewById(R.id.buttonChauff).setOnClickListener(this);
        findViewById(R.id.buttonImpr).setOnClickListener(this);
        findViewById(R.id.buttonProbl).setOnClickListener(this);
        findViewById(R.id.buttonCafe).setOnClickListener(this);
        findViewById(R.id.buttonAutres).setOnClickListener(this);
        findViewById(R.id.buttonElec).setOnClickListener(this);
        findViewById(R.id.buttonChat).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.buttonOrdi:{
                photo = R.drawable.ordi;
                extra = "Ordinateur";}
                break;
            case R.id.buttonChauff:{
                photo =  R.drawable.chauff;
                extra = "Chauffage";}
                break;
            case R.id.buttonImpr:{
                photo = R.drawable.impr;
                extra = "Imprimante";}
                break;
            case R.id.buttonProbl:{
                photo = R.drawable.probl;
                extra = "Entretien";}
                break;
            case R.id.buttonCafe:{
                photo = R.drawable.cafe;
                extra = "Café";}
                break;
            case R.id.buttonAutres:{
                photo = R.drawable.autres;
                extra = "Autre";}
                break;
            case R.id.buttonElec:{
                photo = R.drawable.elec;
                extra = "Electricité";}
                break;
            case R.id.buttonChat:{
                photo = R.drawable.chats;
                extra = "Animaux";}
                break;
        }

        Intent intent = new Intent(v.getContext(), FormMishapActivity.class);
        intent.putExtra("txt",extra);
        intent.putExtra("photo",photo);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_out_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
