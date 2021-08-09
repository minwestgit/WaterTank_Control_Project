package com.example.minseo.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StatisticActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("데이터 통계");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button b1 = (Button)findViewById(R.id.levelButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), LevelActivity.class);
                startActivity(intent);
            }
        });

        Button b2 = (Button)findViewById(R.id.tempButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), test.class);
                startActivity(intent);
            }
        });

        Button b3 = (Button)findViewById(R.id.qualityButton);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), LevelActivity.class);
                startActivity(intent);
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
                        //Toast.makeText(StatisticActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_real:
                        //Toast.makeText(StatisticActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), Monitoring.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item_statistic:
                        //Toast.makeText(StatisticActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), LevelActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_item_control:
                        //Toast.makeText(StatisticActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent( getApplicationContext(), ControlActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(StatisticActivity.this);
                        dialog = builder.setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(StatisticActivity.this, LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .show();
                }

                return true;
            }
        });

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
