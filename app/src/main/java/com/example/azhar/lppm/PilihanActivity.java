package com.example.azhar.lppm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.azhar.lppm.Controller.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class PilihanActivity extends Activity {
    @BindView(R.id.listView)
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilihan_ketua);
        ButterKnife.bind(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int)(dm.widthPixels * 0.8);
        int height = (int)(dm.heightPixels * 0.6);
        getWindow().setLayout(width, height);
        final String [] items = new String [] {"Surat Dinas Perjalanan","Surat Izin Penelitian","Surat Izin Pengabdian","Surat FGD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = null;
                if(items[position].equals("Surat Dinas Perjalanan")){
                    i = new Intent(PilihanActivity.this,PerjalananActivity.class);
                    i.putExtra("layout","perjalanan");



                }
                else if(items[position].equals("Surat Izin Penelitian")){
                    i = new Intent(PilihanActivity.this,PenelitianActivity.class);
                    i.putExtra("layout","penelitian");
                    startActivity(i);
                }
                else if(items[position].equals("Surat FGD")){
                    i = new Intent(PilihanActivity.this,FgdActivity.class);
                    i.putExtra("layout","fgd");
                    startActivity(i);
                }
                else{
                    i = new Intent(PilihanActivity.this,PengabdianActivity.class);
                    i.putExtra("layout","pengabdian");

                }
                i.putExtra("nip",""+getIntent().getStringExtra("nip"));
                Toast.makeText(PilihanActivity.this, ""+getIntent().getStringExtra("nip"), Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }
}
