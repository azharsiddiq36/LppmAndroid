package com.example.azhar.lppm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPengabdianActivity extends Activity {
    @BindView(R.id.etKetua)
    EditText etKetua;
    @BindView(R.id.etAnggota)
    EditText etAnggota;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.etLokasi)
    EditText etLokasi;
    @BindView(R.id.etInstansi)
    EditText etInstansi;
    @BindView(R.id.etKabupaten)
    EditText etKabupaten;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    SimpleDateFormat simpleDateFormat;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pengabdian);
        ButterKnife.bind(this);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sessionManager = new SessionManager(FormPengabdianActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        btTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        etKetua.setText(String.valueOf(map.get(sessionManager.KEY_NAMA)));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAnggota.getText().toString().equals("")) {
                    Toast.makeText(FormPengabdianActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    TextView textView = (TextView) addView.findViewById(R.id.textout);
                    Button remove = (Button) addView.findViewById(R.id.remove);
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
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
                lppmInterface.insertPengabdian(map.get(sessionManager.KEY_NIP),
                        etAnggota.getText().toString(),
                        map.get(sessionManager.KEY_NAMA),
                        etJudul.getText().toString(),
                        tvTanggal.getText().toString(),
                        etLokasi.getText().toString(),
                        etInstansi.getText().toString(),
                        etKabupaten.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                            Toast.makeText(FormPengabdianActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(FormPengabdianActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(FormPengabdianActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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
