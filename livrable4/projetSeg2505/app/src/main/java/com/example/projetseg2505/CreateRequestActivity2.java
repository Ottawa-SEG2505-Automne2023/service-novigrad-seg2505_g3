package com.example.projetseg2505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class CreateRequestActivity2 extends AppCompatActivity {
    DatabaseReference locationRef;
    ListView listViewServices;
    List<Service> services;
    String username;
    String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request2);
        listViewServices = findViewById(R.id.listViewServices);
        username = getIntent().getStringExtra("username_key");
        locationName = getIntent().getStringExtra("location_name_key");
        locationRef = FirebaseDatabase.getInstance().getReference("locations");
        services = new ArrayList<>();

        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Location dsLocation = ds.getValue(Location.class);
                    if (dsLocation.getName().equals(locationName)) {
                        services = dsLocation.getOfferedServices();
                    }
                }
                //create adapter
                ServiceListAdapter serviceListAdapter = new ServiceListAdapter(CreateRequestActivity2.this, services);
                listViewServices.setAdapter(serviceListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //adding onLongClickListener for the services
        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service serv = services.get(position);
                Intent intent = new Intent(getApplicationContext(), CreateRequestActivity3.class);
                intent.putExtra("username_key", username);
                intent.putExtra("location_name_key", locationName);
                intent.putExtra("service_name_key", serv.getServiceName().toString());
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}