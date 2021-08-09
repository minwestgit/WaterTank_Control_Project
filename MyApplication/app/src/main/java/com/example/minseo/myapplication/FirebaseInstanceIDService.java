package com.example.minseo.myapplication;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
        private static final String TAG = "InstanceIDService";

        @Override
        public void onTokenRefresh() {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + token);
            sendRegistrationToServer(token);
        }

        private void sendRegistrationToServer(String token) {


            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("Token", token)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://115.68.228.55/register.php")
                    .post(body)
                    .build();

            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}
