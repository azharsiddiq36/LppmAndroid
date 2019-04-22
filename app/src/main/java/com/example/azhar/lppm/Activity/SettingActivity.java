package com.example.azhar.lppm.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends Activity {
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.img_profil)
    ImageView imgProfil;
    @BindView(R.id.listView)
    ListView listView;
    SessionManager sessionManager;
    String image_url = "http://192.168.43.201/lppm/public/img/foto_user/logouin.png";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);

        Picasso.get()
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(imgProfil);

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
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                    finish();
                    //Toast.makeText(SettingActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                else if(items[position].toLowerCase().equals("about")){
                    WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;
                    LayoutInflater inflater = (LayoutInflater)
                            getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popupView = inflater.inflate(R.layout.about, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView);
                    popupWindow.setWidth(width);
                    popupWindow.setHeight(height-200);
                    popupView.setFocusable(true);
                    popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
                    ImageView btnClose = (ImageView) popupView
                            .findViewById(R.id.btnClose);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    LinearLayout lyAbout = (LinearLayout) popupView.findViewById(R.id.lyAbout);
                    lyAbout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }

                else{
                    Toast.makeText(SettingActivity.this, ""+items[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
