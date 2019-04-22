package com.example.azhar.lppm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.azhar.lppm.Activity.FormPengabdianActivity;
import com.example.azhar.lppm.Adapter.ViewPagerAdapter;
import com.example.azhar.lppm.R;

public class PengabdianActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penelitian_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.pager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FormPengabdianActivity.class);
                i.putExtra("layout","pengabdian");
                i.putExtra("tahun",getIntent().getStringExtra("tahun"));
                startActivity(i);
                finish();
            }
        });
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//    //Code Program pada Method dibawah ini akan Berjalan saat Option Menu Dibuat
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_bar, menu);
//        MenuItem searchIem = menu.findItem(R.id.search);
//        final SearchView searchView = (SearchView) searchIem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Hasil.setText("Hasil Pencarian: "+query);
//                Toast.makeText(getApplicationContext(),query, Toast.LENGTH_SHORT).show();
//
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        return true;
//    }
}
