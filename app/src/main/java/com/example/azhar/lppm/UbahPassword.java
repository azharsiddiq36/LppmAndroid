package com.example.azhar.lppm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPassword extends Activity {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRePassword)
    EditText etRePassword;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    SessionManager sessionManager;
    LppmInterface lppmInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubah_password);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(UbahPassword.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        etEmail.setText(""+map.get(sessionManager.KEY_EMAIL));
        lppmInterface = CombineApi.getApiService();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(UbahPassword.this);
                alertdialog.setTitle("Anda yakin ingin Mengubah Password ?");
                alertdialog.setMessage("Klik Ya untuk Mengubah")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (etPassword.getText().toString().equals("") || etRePassword.getText().toString().equals("")) {
                                    Toast.makeText(UbahPassword.this, "Password Lama / Password Baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                                        getChangePassword(map.get(sessionManager.KEY_NIP),etPassword.getText().toString());
                                    }
                                    else {
                                        Toast.makeText(UbahPassword.this, "Password dan RePassword tidak boleh berbeda", Toast.LENGTH_SHORT).show();
                                    }
                                }
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
        });
    }

    private void getChangePassword(String nip, String password) {
        lppmInterface.ubahPassword(nip,convertToMd5(password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("pesan").equals("berhasil mengubah password")) {
                            Toast.makeText(UbahPassword.this, "Berhasil Mengubah Password", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(UbahPassword.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static String convertToMd5(String pass){
        String password = null;
        MessageDigest md5 ;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(pass.getBytes(),0,pass.length());
            pass = new BigInteger(1,md5.digest()).toString(16);
            while (pass.length()<32){
                pass="0"+pass;
            }
            password = pass;
        }catch (NoSuchAlgorithmException e1){
            e1.printStackTrace();
        }
        return password;
    }
}
