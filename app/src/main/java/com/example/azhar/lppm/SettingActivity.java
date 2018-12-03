package com.example.azhar.lppm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends Activity {
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.listView)
    ListView listView;
    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(SettingActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        String nip = map.get(sessionManager.KEY_NIP);
        tvNama.setText(String.valueOf(map.get(sessionManager.KEY_NAMA)));
        final String [] items = new String [] {"Email\n"+map.get(sessionManager.KEY_EMAIL),"Nip\n"+nip,"Ganti Password","About","Logout"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items[position].equals("Logout")){
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(SettingActivity.this);
                    alertdialog.setTitle("Anda yakin ingin keluar ?");
                    alertdialog.setMessage("Klik Ya untuk keluar")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SessionManager sessionManager = new SessionManager(SettingActivity.this);
                                    sessionManager.logout();
                                    Intent i = new Intent(SettingActivity.this,LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertdialog.create();
                    alertDialog.show();

                }
                else if(items[position].equals("Ganti Password")){
                    Intent i = new Intent(SettingActivity.this,UbahPassword.class);
                    startActivity(i);
                }

                else{
                    Toast.makeText(SettingActivity.this, ""+items[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
