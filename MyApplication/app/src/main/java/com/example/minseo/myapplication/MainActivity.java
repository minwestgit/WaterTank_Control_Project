package com.example.minseo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FirebaseMsgServie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseMessaging.getInstance().subscribeToTopic("news");
        //FirebaseInstanceId.getInstance().getToken();

        Button b1 = (Button)findViewById(R.id.monitorButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), Monitoring.class);
                startActivity(intent);
            }
        });

        Button b2 = (Button)findViewById(R.id.statisticButton);
        b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( getApplicationContext(), LevelActivity.class);
                        startActivity(intent);
            }
        });

        Button b3 = (Button)findViewById(R.id.controlButton);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), ControlActivity.class);
                startActivity(intent);
            }
        });

    }
}
