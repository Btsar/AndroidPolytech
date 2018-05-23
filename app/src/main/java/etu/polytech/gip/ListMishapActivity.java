package etu.polytech.gip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ListMishapActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mishap);
        findViewById(R.id.floatingActionButton).setOnClickListener(this);
        //findViewById(R.id.sign_out_button_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.floatingActionButton){
            Intent intent = new Intent(this, FormMishapActivity.class);
            startActivity(intent);
        }
    }
}
