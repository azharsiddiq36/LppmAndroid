package com.example.azhar.lppm.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager sessionManager;
    Dialog myDialog;
    Button penelitian,fgd,pengabdian,perjalanan;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(MainActivity.this);
       // sessionManager.checkLogin();
        setContentView(R.layout.activity_main);
        HashMap<String,String> map = sessionManager.getDetailsLoggin();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getApplicationContext();
        mActivity = MainActivity.this;
        myDialog = new Dialog(this);
        perjalanan = (Button)findViewById(R.id.btnPenerjalanan);
        final RelativeLayout lyDinamic = (RelativeLayout)findViewById(R.id.lyDinamic);
        perjalanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(MainActivity.this,PenggunaActivity.class);
                i.putExtra("layout","perjalanan");
                startActivity(i);
            }
        });
        penelitian = (Button)findViewById(R.id.btnPenelitian);
        penelitian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(MainActivity.this,PenggunaActivity.class);
                i.putExtra("layout","penelitian");
                startActivity(i);
            }
        });
        pengabdian = (Button)findViewById(R.id.btnPengabdian);
        pengabdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(MainActivity.this,PenggunaActivity.class);
                i.putExtra("layout","pengabdian");
                startActivity(i);
            }
        });
        fgd = (Button)findViewById(R.id.btnFgd);
        fgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(MainActivity.this,PenggunaActivity.class);
                i.putExtra("layout","fgd");
                startActivity(i);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myDialog.setContentView(R.layout.detail_surattugas);
//                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                myDialog.show();
                Intent i = new Intent(MainActivity.this,DetailDisposisiActivity.class);
                startActivity(i);
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        sessionManager = new SessionManager(MainActivity.this);
        HashMap<String,String> map = sessionManager.getDetailsLoggin();

        Intent i = null;
        if (id == R.id.nav_penelitian) {
            // Handle the camera action
            i = new Intent(this,PenggunaActivity.class);
            i.putExtra("layout","penelitian");
        } else if (id == R.id.nav_pengabdian) {
            i = new Intent(this,PenggunaActivity.class);
            i.putExtra("layout","pengabdian");
        } else if (id == R.id.nav_fgd) {
            i = new Intent(this,PenggunaActivity.class);
            i.putExtra("layout","fgd");
        } else if (id == R.id.nav_perjalanan) {
            i = new Intent(this,PenggunaActivity.class);
            i.putExtra("layout","perjalanan");
        } else if (id == R.id.nav_visiMisi) {
            i = new Intent(this,VisiMisiActivity.class);
        } else if (id == R.id.nav_pengaturan) {
            i = new Intent(this,SettingActivity.class);
        }
        startActivity(i);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
