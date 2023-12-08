package com.example.projetseg2505;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientActivity extends AppCompatActivity {

    Button btnLogout;

    Button btnCreateReq;
    Button btnSearchLocation;

    TextView helloUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //assign the buttons and stuff
        helloUser = findViewById(R.id.userDetails);
        String username = getIntent().getStringExtra("name_key");
        helloUser.setText("Welcome, " + username + '!');
        btnLogout = findViewById(R.id.btnLogout);
        btnCreateReq = findViewById(R.id.btnCreateRequest);
        btnSearchLocation = findViewById(R.id.btnSearchLocation);

        btnCreateReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                intent.putExtra("username_key", username);
                startActivity(intent);
            }
        });

        btnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}