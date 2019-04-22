package com.example.azhar.lppm.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azhar.lppm.R;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPassword extends Activity {
    LppmInterface lppmInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_password);
        final EditText etNip = (EditText) findViewById(R.id.etNip);
        Button btnSimpan = (Button) findViewById(R.id.btnSimpan);
        lppmInterface = CombineApi.getApiService();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(LupaPassword.this);
                alertdialog.setTitle("Anda yakin ingin Mengubah Password ?");
                alertdialog.setMessage("Klik Ya untuk Mengubah")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lppmInterface.lupaPassword(etNip.getText().toString())
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                Log.d("rabick",""+response.isSuccessful());
                                                if (response.isSuccessful()) {
                                                    Log.d("rabick1","a");
                                                    try {
                                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                        if (jsonRESULTS.getString("pesan").toLowerCase().equals("password baru terkirim di email")) {
                                                            Toast.makeText(LupaPassword.this, "Password baru terkirim di email", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(LupaPassword.this,LoginActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                            Log.d("rabick2","ea");
                                                        } else {
                                                            Toast.makeText(LupaPassword.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(LupaPassword.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
                                            }
                                        });
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
}
