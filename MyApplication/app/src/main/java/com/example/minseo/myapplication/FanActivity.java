package com.example.minseo.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class FanActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private AlertDialog dialog;
    private Button supplybtn;
    private Button stopbtn;
    private Button fanonbtn, fanoffbtn;
    private TextView mTextViewlevel;

    private static final String TAG_LEVEL = "Distance";
    private static final String TAG_JSON ="Data";
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        UserID userid = (UserID)getApplication();
        String userID = userid.getUserID();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("저수조 관리");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        supplybtn = (Button)findViewById(R.id.supplyButton);
        stopbtn = (Button)findViewById(R.id.stopButton);
        fanonbtn = (Button)findViewById(R.id.fanonButton);
        fanoffbtn = (Button)findViewById(R.id.fanoffButton);

        mTextViewlevel = (TextView)findViewById(R.id.textView_Level);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://115.68.228.55/Monitoring.php?userID=" + userID);

        supplybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID userid = (UserID)getApplication();
                String userID = userid.getUserID();
                BackgroundTask task = new BackgroundTask();
                task.execute("http://115.68.228.55/pub.php?MSG=" + userID + "MOTORON");

            }
        });

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID userid = (UserID)getApplication();
                String userID = userid.getUserID();
                BackgroundTask task = new BackgroundTask();
                task.execute("http://115.68.228.55/pub.php?MSG=" + userID + "MOTOROFF");
            }
        });

        fanonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID userid = (UserID)getApplication();
                String userID = userid.getUserID();
                BackgroundTask task = new BackgroundTask();
                task.execute("http://115.68.228.55/pub.php?MSG=" + userID + "FANON");
            }
        });

        fanoffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID userid = (UserID)getApplication();
                String userID = userid.getUserID();
                BackgroundTask task = new BackgroundTask();
                task.execute("http://115.68.228.55/pub.php?MSG=" + userID + "FANOFF");
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        //Toast.makeText(ControlActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_real:
                        //Toast.makeText(ControlActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), Monitoring.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_statistic:
                        //Toast.makeText(ControlActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), LevelActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_control:
                        //Toast.makeText(ControlActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), ControlActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(FanActivity.this);
                        dialog = builder.setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(FanActivity.this, LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) { }
                                })
                                .show();
                }
                return true;
            }
        });


    }

    class BackgroundTask extends AsyncTask<String, String, String>
    {
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.dismiss();
            mJsonString = result;
            showResult();
        }
        @Override
        protected String doInBackground(String... params) {
            String output="";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if(conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        int i = 0;
                        for(;;) {
                            String line = br.readLine();
                            if(line == null) {
                                break;
                            }
                            i++;
                            output += line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }
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

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_LEVEL, level);


                mArrayList.add(hashMap);

                mTextViewlevel.setText(level);

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
    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ControlActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }



        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[0];
            String control = (String)params[1];

            String serverURL = "http://115.68.228.55/Control.php";
            String postParameters = "userID=" + userID + "&control=" + control;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }

        }
    }
    */

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
