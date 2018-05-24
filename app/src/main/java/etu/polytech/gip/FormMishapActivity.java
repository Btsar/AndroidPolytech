package etu.polytech.gip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FormMishapActivity extends AppCompatActivity  {

    Button sendMishap;
    EditText locationMishap;
    EditText descriptionMishap;
    TextView typeMishap;
    RadioGroup severityMishap;
    Date dateAndroid = new Date();

    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mishap);

        mFirestore = FirebaseFirestore.getInstance();

        sendMishap = (Button) findViewById(R.id.sendMishap);
        locationMishap = (EditText) findViewById(R.id.locationMishap);
        descriptionMishap = (EditText) findViewById(R.id.descriptionBox);
        typeMishap = (TextView) findViewById(R.id.typeOfTheIncidentText);
        severityMishap = (RadioGroup) findViewById(R.id.severityChoice);

        sendMishap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationMishap.getText().toString();
                String description = descriptionMishap.getText().toString();
                String type = typeMishap.getText().toString();
                String severity = onRadioButtonClicked(findViewById(severityMishap.getCheckedRadioButtonId()));
                String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(dateAndroid);
                String date = DateFormat.getDateInstance(DateFormat.SHORT).format(dateAndroid);

                Map<String,String> mishapForFirestore = new HashMap<>();
                mishapForFirestore.put("location",location);
                mishapForFirestore.put("description",description);
                mishapForFirestore.put("type",type);
                mishapForFirestore.put("severity",severity);
                mishapForFirestore.put("time",time);
                mishapForFirestore.put("date",date);

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
                    return "Élevée";
                    break;
            case R.id.severityN:
                if (checked)
                    return "Moyenen";
                    break;
            case R.id.severityL:
                if (checked)
                    return "Basse";
                break;
        }
        return null;
    }

    private void toastTxt(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if(i == R.id.sendMishap){
//            Intent intent = new Intent(this, ListMishapActivity.class);
//            startActivity(intent);
//        }
//    }
}
