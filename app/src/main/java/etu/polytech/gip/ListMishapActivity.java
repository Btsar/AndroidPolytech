package etu.polytech.gip;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    FirebaseAuth mAuth = FirebaseAuth.getInstance();//TODO Déconnexion plus tard

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
                Intent intent = new Intent(v.getContext(), FormMishapActivity.class);
                startActivity(intent);
            }
        });
    }

}
