package com.example.azhar.lppm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.PenelitianAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;
import com.google.gson.JsonObject;

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

public class DetailPenelitianActivity extends Activity {
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
    @BindView(R.id.btnEdit)
    Button btnEdit;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    List<Penelitian> penelitians = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_penelitian);
        ButterKnife.bind(this);
        btnSimpan.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.VISIBLE);
        sessionManager = new SessionManager(DetailPenelitianActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSimpan.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
            }
        });


//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//        getWindow().setLayout((int)(width*.9),(int)(height*.8));

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
                    Toast.makeText(DetailPenelitianActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    TextView textView = (TextView)addView.findViewById(R.id.textout);
                    Button remove = (Button)addView.findViewById(R.id.remove);
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)addView.getParent()).removeView(addView);
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
                String a = getIntent().getStringExtra("posisi");
                Toast.makeText(DetailPenelitianActivity.this, "a", Toast.LENGTH_SHORT).show();
                lppmInterface.editPenelitian(map.get(sessionManager.KEY_NIP),
                        a,
                        map.get(sessionManager.KEY_EMAIL),
                        map.get(sessionManager.KEY_NAMA),
                        etAnggota.getText().toString(),
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
                                            Toast.makeText(DetailPenelitianActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DetailPenelitianActivity.this,MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(DetailPenelitianActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(DetailPenelitianActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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

    private void setFocusTrue() {
        etInstansi.setFocusable(true);
        etJudul.setFocusable(true);
        etLokasi.setFocusable(true);
        etKabupaten.setFocusable(true);
        etKtp.setFocusable(true);
        etWhatsApp.setFocusable(true);
    }

    private void setFocusFalse() {
        etInstansi.setFocusable(false);
        etJudul.setFocusable(false);
        etLokasi.setFocusable(false);
        etKabupaten.setFocusable(false);
        etKtp.setFocusable(false);
        etWhatsApp.setFocusable(false);
    }

    private void setvalue() {
        etWhatsApp.setText(getIntent().getStringExtra("nowa"));
        etInstansi.setText(getIntent().getStringExtra("instansi"));
        etJudul.setText(getIntent().getStringExtra("judul"));
        etLokasi.setText(getIntent().getStringExtra("lokasi"));
        etKtp.setText(getIntent().getStringExtra("noktp"));
        tvTanggal.setText(getIntent().getStringExtra("tanggal"));
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

    @Override
    protected void onPause() {
        super.onPause();
        setvalue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setvalue();
    }
}


