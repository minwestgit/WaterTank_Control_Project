package com.example.minseo.myapplication;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Monitoring extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private AlertDialog dialog;

    private static final String TAG_LEVEL = "Distance";
    private static final String TAG_TEMPER = "Temperature";
    private static final String TAG_QUALITY ="Waterqulity";
    private static final String TAG_TURBIDITY ="Turbidity";
    private static final String TAG_JSON ="Data";
    private TextView mTextViewResult;

    private TextView mTextViewLevel, mTextViewTemper, mTextViewQuality, mTextViewTurbidity;
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("실시간 모니터링");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        UserID userid = (UserID)getApplication();
        String userID = userid.getUserID();

        mTextViewLevel = (TextView)findViewById(R.id.textView_level);
        mTextViewTemper = (TextView)findViewById(R.id.textView_temper);
        mTextViewQuality = (TextView)findViewById(R.id.textView_quality);
        mTextViewTurbidity = (TextView)findViewById(R.id.textView_turbidity);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://115.68.228.55/Monitoring.php?userID=" + userID);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        //Toast.makeText(Monitoring.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_real:
                        //Toast.makeText(Monitoring.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), Monitoring.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item_statistic:
                        //Toast.makeText(Monitoring.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), LevelActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item_control:
                        //Toast.makeText(Monitoring.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), ControlActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Monitoring.this);
                        dialog = builder.setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(Monitoring.this, LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .show();

                        break;
                }

                return true;
            }
        });

    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.dismiss();
            mJsonString = result;
            showResult();
        }
        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for(int i=0;i<jsonObject.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String level = item.getString(TAG_LEVEL);
                String temper = item.getString(TAG_TEMPER);
                String quality = item.getString(TAG_QUALITY);
                String turbidity = item.getString(TAG_TURBIDITY);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_LEVEL, level);
                hashMap.put(TAG_TEMPER, temper);
                hashMap.put(TAG_QUALITY, quality);
                hashMap.put(TAG_TURBIDITY, turbidity);

                mArrayList.add(hashMap);

                mTextViewLevel.setText(level);
                mTextViewTemper.setText(temper);
                mTextViewQuality.setText(quality);
                mTextViewTurbidity.setText(turbidity);
            }

            /*
            ListAdapter adapter = new SimpleAdapter(
                    Monitoring.this, mArrayList, R.layout.activity_monitoring,
                    new String[]{TAG_LEVEL,TAG_TEMPER, TAG_QUALITY},
                    new int[]{R.id.textView_level, R.id.textView_temper, R.id.textView_quality}
            );
            */
            //mlistView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            //case R.id.action_settings:
            //    return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
