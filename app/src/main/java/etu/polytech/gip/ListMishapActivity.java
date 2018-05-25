package etu.polytech.gip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ListMishapActivity extends AppCompatActivity {

    private static final String TAG = "LogMeToDatabase";

    private FirebaseFirestore mFirestore;
    private RecyclerView mMishapList;
    List<MishapModel> mishapList;
    private MishapListAdapter mMishapAdapter;

    //Buttons
    FloatingActionButton addMishap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mishap);

        addMishap = (FloatingActionButton) findViewById(R.id.addMishapButton);
        mMishapList = (RecyclerView) findViewById(R.id.listOfAllMishap);
        //mMishapList.setHasFixedSize(true);

        mFirestore = FirebaseFirestore.getInstance();

        /* Il faut puor chaque élément de la base de donné ajouter un alertReceiver
        Double longitude = Double.parseDouble();
        Double latitude =  Double.parseDouble();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteres = new Criteria();
        criteres.setAccuracy(Criteria.ACCURACY_FINE);
        criteres.setCostAllowed(true);
        criteres.setPowerRequirement(Criteria.POWER_HIGH);
        String fournisseur = locationManager.getBestProvider(criteres, true);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            locationManager.requestLocationUpdates(fournisseur,
                    10000,   // 10 sec
                    10, this);
        }
        catch (SecurityException e) {}
        //Pour chaque element de de la list ajouter alerte + clacul de la distance
        try {
            locationManager.addProximityAlert(longitude, latitude, 10, -1, pending);
        } catch (SecurityException e) {}

        public int getDistance (String latitudeIncident, String longitudeIncident) {
        Location location = this.getPosition();

        double latitude0 = convertRad(Double.parseDouble(latitudeIncident));
        double longitude0 = convertRad(Double.parseDouble(longitudeIncident));

        double latitude1 = convertRad(location.getLatitude());
        double longitude1 = convertRad(location.getLongitude());

        int R = 6378000; //Rayon de la terre en mètre



        int distance = (int) (R * (Math.PI/2 - Math.asin( Math.sin(latitude0) * Math.sin(latitude1) + Math.cos(longitude0 - longitude1) * Math.cos(longitude1) * Math.cos(longitude0))));
        return distance;
        }

        private double convertRad (double input){
            return (Math.PI * input)/180;
        }
        */

        this.mishapList = new ArrayList<>();
        this.mMishapAdapter = new MishapListAdapter(this.mishapList);
        this.mMishapList.setAdapter(mMishapAdapter);
        this.mMishapList.setLayoutManager(new LinearLayoutManager(this));

        mFirestore.collection("mishaps").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG,"Erreur : "+ e.getMessage());
                }

                for (DocumentChange d: queryDocumentSnapshots.getDocumentChanges()){

                    if(d.getType() == DocumentChange.Type.ADDED) {
                        MishapModel mishap = d.getDocument().toObject(MishapModel.class);
                        mishapList.add(mishap);
                        mMishapAdapter.notifyDataSetChanged();
                    }else if(d.getType() == DocumentChange.Type.REMOVED){
                        MishapModel mishap = d.getDocument().toObject(MishapModel.class);
                        mishapList.remove(mishap);
                        mMishapAdapter.notifyDataSetChanged();
                    }


//                        String data = d.getDocument().getString("description");
//                        Log.d(TAG,"Description : "+ data);
//                    }

                }
            }
        });

        addMishap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TypeListActivity.class);
                startActivity(intent);
            }
        });
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