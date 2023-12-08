package com.example.projetseg2505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateRequestActivity3 extends AppCompatActivity {
    List<Requirement<String>> requirementList;
    Button btnSendRequest;
    ListView listViewReq;
    DatabaseReference servicesRef;
    DatabaseReference requestsRef;
    String locationName;
    String userName;
    String serviceName;

    InformationList reqListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request3);

        //instantiating all required objects for this class
        userName = getIntent().getStringExtra("username_key");
        locationName = getIntent().getStringExtra("location_name_key");
        serviceName = getIntent().getStringExtra("service_name_key");
        servicesRef = FirebaseDatabase.getInstance().getReference("services");
        requestsRef = FirebaseDatabase.getInstance().getReference("requests");
        Log.d("Prints", userName);
        Log.d("Prints", serviceName);
        Log.d("Prints", locationName);
        listViewReq = findViewById(R.id.listViewInfoList);
        requirementList = new ArrayList<>();
        btnSendRequest = findViewById(R.id.btnSendRequest);

        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //requirements list clear
                requirementList.clear();

                //iterating through all the nodes of the accounts path
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    //get the location
                    Service serv = postSnapshot.getValue(Service.class);
                    if(serv.getServiceName().equals(serviceName)){
                        requirementList = serv.getRequiredInfo();
                    }
                    //create an adapter
                    reqListAdapter = new InformationList(CreateRequestActivity3.this, requirementList);
                    //attach the adapter to the list view for display
                    listViewReq.setAdapter(reqListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set on click listener for the send request button
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create a request object with (string assoc. location name, string assoc. username, list<requirements>)
                List<String> infoBits = reqListAdapter.getInfoBits();
                for(int i = 0; i<requirementList.size(); i++){
                    requirementList.get(i).setInformation(infoBits.get(i));
                    if(infoBits.get(i).isEmpty()){ // test unitaire 1
                        Toast.makeText(CreateRequestActivity3.this, "Make sure to fill out all text fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(containsNonAlphaNum(infoBits.get(i))){ // test unitaire 2
                        Toast.makeText(CreateRequestActivity3.this, "Do not include any non alpha numeric characters.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(infoBits.get(i).length() > 50){ // test unitaire 3
                        Toast.makeText(CreateRequestActivity3.this, "Please reduce the length of your input to the text field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Request newReq = new Request(locationName, userName, serviceName, requirementList);

                //Create a new entry with a unique key
                DatabaseReference newEntryRef = requestsRef.push();
                String uniquekey = newEntryRef.getKey();

                //Set the data for the new entry
                newReq.setId(uniquekey); //set the unique id to the request obj
                newEntryRef.setValue(newReq);

                Toast.makeText(CreateRequestActivity3.this, "Request Sent to " + locationName + " Service Novigrad location.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
                finish(); // finish this activity
            }
        });
    }

    private boolean containsNonAlphaNum(String input){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(input);

        //if any special characters are found, return true
        return matcher.find();
    }
}