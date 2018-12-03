package com.example.azhar.lppm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.etNip)
    EditText etNip;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.imgShow)
    ImageView imgShow;
    @BindView(R.id.lupaPassword)
    TextView lupaPassword;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    ProgressDialog loading;
    LppmInterface lppmInterface;
    //SharedPreferencfes sharedPreferences;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(LoginActivity.this);
        //Klik Lupa
        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (LoginActivity.this, LupaPassword.class);
                startActivity(i);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    imgShow.setVisibility(View.VISIBLE);
                    imgShow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(status.getText().equals("show")){
                                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                imgShow.setImageResource(R.drawable.ic_remove_red_eye_black_30dp);
                                status.setText("hide");
                            }
                            else {
                                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                imgShow.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);

                                status.setText("show");
                            }
                        }
                    });
                }


            }
        });


        lppmInterface = CombineApi.getApiService();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = new ProgressDialog(LoginActivity.this);
                loading.setMax(100);
                loading.setTitle("Harap Tunggu");
                loading.setMessage("Loading...");
                loading.setProgressStyle(loading.STYLE_SPINNER);
                loading.show();
                loading.setCancelable(false);

                if(etNip.getText().toString().equals("")&&etPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Harap Inputkan Nip dan Password", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
                else{
                    requestLogin();
                }
            }
        });
    }

    private void requestLogin() {

        lppmInterface.loginRequest(etNip.getText().toString(),
                convertToMd5(etPassword.getText().toString()))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("pesan").equals("success")){
                                    //Log.d("Pesan", String.valueOf(jsonRESULTS.getJSONObject("data")));
                                    JSONObject data = jsonRESULTS.getJSONObject("data");
                                    Log.d("kambingLogin", ""+data);
                                    String id_pengguna,nip,token_akses,nama,email,foto,hak_akses,password,konfirmasi_email;
                                    id_pengguna = data.getString("id_pengguna");
                                    nip = data.getString("nip");
                                    nama = data.getString("nama");
                                    email = data.getString("email");
                                    hak_akses = data.getString("hak_akses");
                                    password = data.getString("password");
                                    konfirmasi_email = data.getString("konfirmasi_email");
                                    foto = data.getString("foto");
                                    token_akses = data.getString("token_akses");
                                    // Log.d("kambing", ""+nip);
                                    sessionManager.saveLogin(nip,nama,email,foto,id_pengguna,token_akses,hak_akses,konfirmasi_email);
                                    Intent i = null;
                                   // if (hak_akses.toLowerCase().equals("kepala")){
                                    //    Toast.makeText(LoginActivity.this, "ini Kepala", Toast.LENGTH_SHORT).show();
                                    //    i = new Intent(LoginActivity.this,FormPenelitianActivity.class);
                                   // }
                                   // else {
                                         i = new Intent(LoginActivity.this, MainActivity.class);
                                  //  }
                                    Toast.makeText(LoginActivity.this, "Selamat Datang", Toast.LENGTH_LONG).show();
                                    i.putExtra("nip",""+etNip.getText().toString());
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getApplicationContext().startActivity(i);
                                    finish();
                                }
                                else if(jsonRESULTS.getString("pesan").equals("unknownuser")){
                                    Toast.makeText(LoginActivity.this, "Nip ini tidak terdaftar", Toast.LENGTH_SHORT).show();
                                }
                                else if(jsonRESULTS.getString("pesan").equals("faill")){
                                    Toast.makeText(LoginActivity.this, "Harap Periksa Kembali Nip/Password Anda", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Maaf NIP/Password Salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
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
