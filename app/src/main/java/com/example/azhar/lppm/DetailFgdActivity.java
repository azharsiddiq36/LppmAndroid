package com.example.azhar.lppm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

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

public class DetailFgdActivity extends Activity {
    @BindView(R.id.etModerator)
    EditText etModerator;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.etAnggota)
    EditText etAnggota;
    @BindView(R.id.etNarasumber)
    EditText etNarasumber;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.etLokasi)
    EditText etLokasi;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.rbPenelitian)
    RadioButton rbPenelitian;
    @BindView(R.id.rbPengabdian)
    RadioButton rbPengabdian;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;
    LppmInterface lppmInterface;
    SessionManager sessionManager;
    List<Penelitian> penelitians = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fgd);
        ButterKnife.bind(this);
        etNarasumber.setFocusable(false);
        btnEdit.setVisibility(View.VISIBLE);
        btnSimpan.setVisibility(View.INVISIBLE);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aturvisible("edit");
            }
        });
        sessionManager = new SessionManager(DetailFgdActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
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
                    Toast.makeText(DetailFgdActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
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

        lppmInterface = CombineApi.getApiService();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jen = "";
                if (rbPenelitian.isChecked()){
                    jen = "Penelitian";
                }
                lppmInterface.insertFGD(map.get(sessionManager.KEY_NIP),
                        etNarasumber.getText().toString(),
                        etModerator.getText().toString(),
                        etJudul.getText().toString(),
                        getJenis(jen),
                        etLokasi.getText().toString(),
                        etAnggota.getText().toString(),
                        tvTanggal.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                            Toast.makeText(DetailFgdActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DetailFgdActivity.this,MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(DetailFgdActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(DetailFgdActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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

    private void aturvisible(String edit) {
        if (edit.equals("edit")){
            btnEdit.setVisibility(View.INVISIBLE);
            btnSimpan.setVisibility(View.VISIBLE);
        }else {
            btnSimpan.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }
    }

    private static String getJenis(String jenis) {
        if (jenis.equals("Penelitian")){
            jenis = "penelitian";

        }
        else {
            jenis = "pengabdian";
        }
        return jenis;
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
