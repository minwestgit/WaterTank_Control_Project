package com.example.minseo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private CheckBox checkBox;
    private SharedPreferences appData;
    private boolean saveIDData;
    private String id;
    private EditText idText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        idText = (EditText) findViewById(R.id.idText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        if (saveIDData) {
            idText.setText(id);
            checkBox.setChecked(saveIDData);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserID userid = (UserID)getApplication();
                UserID userpw = (UserID)getApplication();
                //String userID = userid.getUserID();
                //userID = idText.getText().toString();
                String userID = idText.getText().toString();
                userid.setUserID(userID);

                String userPW = passwordText.getText().toString();
                userpw.setUserPW(userPW);

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success == true) {
                                save();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("???????????? ??????????????????.")
                                        .setPositiveButton("??????", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("????????? ?????? ???????????????.")
                                        .setNegativeButton("?????? ??????", null)
                                        .create();
                                dialog.show();

                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();;
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void save() {
        // SharedPreferences ??????????????? ?????? ????????? Editor ??????
        SharedPreferences.Editor editor = appData.edit();

        // ???????????????.put??????( ???????????? ??????, ???????????? ??? )
        // ???????????? ????????? ?????? ???????????? ????????????
        editor.putBoolean("SAVE_ID_DATA", checkBox.isChecked());
        editor.putString("ID", idText.getText().toString().trim());

        // apply, commit ??? ????????? ????????? ????????? ???????????? ??????
        editor.apply();
    }

    private void load() {
        // SharedPreferences ??????.get??????( ????????? ??????, ????????? )
        // ????????? ????????? ???????????? ?????? ??? ?????????
        saveIDData = appData.getBoolean("SAVE_ID_DATA", false);
        id = appData.getString("ID", "");
    }

}