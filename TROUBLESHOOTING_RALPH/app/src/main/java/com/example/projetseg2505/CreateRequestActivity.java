package com.example.projetseg2505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateRequestActivity extends AppCompatActivity {
    DatabaseReference locationsDB;
    ListView listViewLocations;
    List<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        locationsDB = FirebaseDatabase.getInstance().getReference("locations");
        listViewLocations = findViewById(R.id.listViewLocations);
        locations = new ArrayList<>();

        //adding onLongClickListener for the services
        listViewLocations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Location location = locations.get(position);
                Intent intent = new Intent(getApplicationContext(), CreateRequestActivity2.class);
                intent.putExtra("username_key", getIntent().getStringExtra("username_key"));
                intent.putExtra("location_name_key", location.getName().toString());
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //attaching a value event listener to keep location list up to date
        locationsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear the previous location list
                locations.clear();

                //iterating through all the nodes of the locations path
                for(DataSnapshot snap : snapshot.getChildren()){
                    //get location
                    Location loc = snap.getValue(Location.class);
                    //add it to the list
                    locations.add(loc);
                }
                //create an adapter
                LocationListAdapter locationAdapter = new LocationListAdapter(CreateRequestActivity.this, locations);
                //attach the adapter to the list view for display
                listViewLocations.setAdapter(locationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}