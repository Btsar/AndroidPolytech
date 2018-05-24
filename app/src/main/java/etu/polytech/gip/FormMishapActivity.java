package etu.polytech.gip;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;


public class FormMishapActivity extends AppCompatActivity  {

    ImageView typePic;
    Button sendMishap;
    EditText locationMishap;
    EditText descriptionMishap;
    TextView typeMishap;
    RadioGroup severityMishap;
    EditText titleMishap;
    EditText buildingMishap;
    Date dateAndroid = new Date();

    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mishap);

       //Intent i = getIntent();
        mFirestore = FirebaseFirestore.getInstance();

        typePic = (ImageView) findViewById(R.id.imageOfTheTypeOfIncident);
        sendMishap = (Button) findViewById(R.id.sendMishap);
        locationMishap = (EditText) findViewById(R.id.locationMishap);
        descriptionMishap = (EditText) findViewById(R.id.descriptionBox);
        typeMishap = (TextView) findViewById(R.id.typeOfTheIncidentText);
        severityMishap = (RadioGroup) findViewById(R.id.severityChoice);
        titleMishap = (EditText) findViewById(R.id.titleMishap);
        buildingMishap = (EditText) findViewById(R.id.buildingMishap);
        typeMishap.setText(getIntent().getExtras().getString("txt"));
        typePic.setImageResource(getIntent().getExtras().getInt("photo"));

        sendMishap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = typeMishap.getText().toString();
                String location = locationMishap.getText().toString();
                String description = descriptionMishap.getText().toString();

                String severity = onRadioButtonClicked(findViewById(severityMishap.getCheckedRadioButtonId()));
                String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndroid);
                String date = DateFormat.getDateInstance(DateFormat.SHORT).format(dateAndroid);
                String title = titleMishap.getText().toString();
                String building = buildingMishap.getText().toString();

                /*
                Location localisation =  gps2Activity.getPosition();
                Double doubleLatitude = localisation.getLatitude();
                Double doubleLongitude = localisation.getLongitude();

                String latitude = doubleLatitude.toString();
                String longitude = doubleLongitude.toString();
                */

                Map<String,String> mishapForFirestore = new HashMap<>();
                mishapForFirestore.put("location",location);
                mishapForFirestore.put("description",description);
                mishapForFirestore.put("type",type);
                mishapForFirestore.put("severity",severity);
                mishapForFirestore.put("time",time);
                mishapForFirestore.put("date",date);
                mishapForFirestore.put("title",title);
                mishapForFirestore.put("building",building);

                //mishapForFirestore.put("latitude", latitude);
                //mishapForFirestore.put("longitude", longitude);

                mFirestore.collection("mishaps").add(mishapForFirestore).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        toastTxt("Votre icident a bien été prise en compte !");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        toastTxt("Votre icident n'a pas pu être envoyé ...");
                    }
                });
                Intent intent = new Intent(v.getContext(), ListMishapActivity.class);
                startActivity(intent);
            }
        });


    }

    public String onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.severityH:
                if (checked)
                    return "Haute";
                    break;
            case R.id.severityN:
                if (checked)
                    return "Moyenne";
                    break;
            case R.id.severityL:
                if (checked)
                    return "Basse";
                break;
        }
        return null;
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

    private void toastTxt(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}
