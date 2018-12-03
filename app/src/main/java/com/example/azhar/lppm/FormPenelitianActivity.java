package com.example.azhar.lppm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPenelitianActivity extends Activity {
    @BindView(R.id.etKetua)
    EditText etKetua;
    @BindView(R.id.etAnggota)
    EditText etAnggota;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.etLokasi)
    EditText etLokasi;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.etInstansi)
    EditText etInstansi;
    @BindView(R.id.etKabupaten)
    EditText etKabupaten;
    @BindView(R.id.etKtp)
    EditText etKtp;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.etWhatsApp)
    EditText etWhatsApp;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.etEmail)
    EditText etEmail;
    private int length = 0;
    ArrayList<String> arrayList = new ArrayList();
    public JSONArray jsonObject =new JSONArray();
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    public JSONObject angotaJson = new JSONObject();
    HashMap <Integer,String> anggota=new HashMap<Integer,String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_penelitian);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(FormPenelitianActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();

        lppmInterface = CombineApi.getApiService();

        etEmail.setText(String.valueOf(map.get(sessionManager.KEY_EMAIL)));

        etKetua.setText(String.valueOf(map.get(sessionManager.KEY_NAMA)));

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

        btTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAnggota.getText().toString().equals("")){
                    Toast.makeText(FormPenelitianActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    TextView textView = (TextView)addView.findViewById(R.id.textout);
                    Button remove = (Button)addView.findViewById(R.id.remove);
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    sizeData(0);
                    Log.d("anggota",""+length);
                    insertAnggota(textView.getText().toString());

                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)addView.getParent()).removeView(addView);
                            sizeData(1);
                        }
                    });
                    lyDinamic.addView(addView);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("anggota",""+String.valueOf(anggota));
  //              Log.d("anggota",""+String.valueOf(angotaJson));
                Log.d("anggotaarray",String.valueOf(arrayList));
                lppmInterface.insertPenelitian(map.get(sessionManager.KEY_EMAIL),
                        map.get(sessionManager.KEY_NAMA),
                        arrayList.toString(),
                        etJudul.getText().toString(),
                        tvTanggal.getText().toString(),
                        etLokasi.getText().toString(),
                        etInstansi.getText().toString(),
                        etKtp.getText().toString(),
                        "ayam",
                        etWhatsApp.getText().toString(),
                        map.get(sessionManager.KEY_NIP))
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                            Toast.makeText(FormPenelitianActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                            Log.d("kumbang",""+jsonRESULTS.getString("ayam"));
                                            Intent i = new Intent(FormPenelitianActivity.this,MainActivity.class);
                                            finish();
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(FormPenelitianActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(FormPenelitianActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.toString());

                            }
                        });
            }
        });
    }

    private void insertAnggota(String s) {
        this.arrayList.add(String.valueOf(s));
        Log.d("anggota",""+arrayList);

    }

    private int sizeData(int a) {

        if(a == 0){
            return this.length +=1;
        }else {
            return this.length -=1;
        }

    }


    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvTanggal.setText(""+simpleDateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
