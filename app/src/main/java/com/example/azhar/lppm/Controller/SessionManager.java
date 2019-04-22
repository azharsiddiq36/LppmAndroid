package com.example.azhar.lppm.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.azhar.lppm.Activity.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String KEY_ID = "id";
    public static final String KEY_NIP = "nip";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_HAK_AKSES = "akses";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_KONFIRMASI_EMAIL = "Y";
    public static final String LOGGIN_STATUS = "sudahlogin";
    public static final String SHARE_NAME = "logginsession";
    private Context context;
    private final int MODE_PRIVATE = 0;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLogin(String nip,
                          String nama,
                          String email,
                          String foto,
                          String id,
                          String token,
                          String akses,
                          String konf_email){
        editor.putBoolean(LOGGIN_STATUS,true);
        editor.putString(KEY_NIP,nip);
        editor.putString(KEY_ID,id);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_FOTO,foto);
        editor.putString(KEY_HAK_AKSES,akses);
        editor.putString(KEY_KONFIRMASI_EMAIL,konf_email);
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_NAMA,nama);
        editor.commit();
    }
    public HashMap getDetailsLoggin(){
        HashMap<String,String> map = new HashMap<>();
        map.put(KEY_NAMA,sharedPreferences.getString(KEY_NAMA,null));
        map.put(KEY_FOTO,sharedPreferences.getString(KEY_FOTO,null));
        map.put(KEY_HAK_AKSES,sharedPreferences.getString(KEY_HAK_AKSES,null));
        map.put(KEY_TOKEN,sharedPreferences.getString(KEY_TOKEN,null));
        map.put(KEY_KONFIRMASI_EMAIL,sharedPreferences.getString(KEY_KONFIRMASI_EMAIL,null));
        map.put(KEY_ID,sharedPreferences.getString(KEY_ID,null));
        map.put(KEY_EMAIL,sharedPreferences.getString(KEY_EMAIL,null));
        map.put(KEY_NIP,sharedPreferences.getString(KEY_NIP,null));
        return map;
    }
    public void checkLogin(){
        if (!this.isLogin()){
            Intent i = new Intent(context,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    public void logout(){
        editor.clear();
        editor.commit();
    }
    public Boolean isLogin(){
        return sharedPreferences.getBoolean(LOGGIN_STATUS,false);
    }

}
