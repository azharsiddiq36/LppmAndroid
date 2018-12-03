package com.example.azhar.lppm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
import com.example.azhar.lppm.Model.Perjalanan;
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

public class FormPerjalananActivity extends Activity {
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
    @BindView(R.id.etWhatsApp)
    EditText etWhatsApp;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.rbPenelitian)
    RadioButton rbPenelitian;
    @BindView(R.id.rbPengabdian)
    RadioButton rbPengabdian;
    @BindView(R.id.etNip)
    EditText etNip;

    @BindView(R.id.etKluster)
    EditText etKluster;
    @BindView(R.id.etJabatan)
            EditText etJabatan;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    List<Perjalanan> perjalanan = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_perjalanan);
        ButterKnife.bind(this);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sessionManager = new SessionManager(FormPerjalananActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        btTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        etKetua.setText(String.valueOf(map.get(sessionManager.KEY_NAMA)));
        etNip.setText(String.valueOf(map.get(sessionManager.KEY_NIP)));
        etEmail.setText(String.valueOf(map.get(sessionManager.KEY_EMAIL)));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAnggota.getText().toString().equals("")) {
                    Toast.makeText(FormPerjalananActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
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
                String jen = "";
                if (rbPenelitian.isChecked()){
                    jen = "Penelitian";
                }
                lppmInterface.insertPerjalanan(map.get(sessionManager.KEY_NAMA),
                        etAnggota.getText().toString(),
                        etJudul.getText().toString(),
                        tvTanggal.getText().toString(),
                        etLokasi.getText().toString(),
                        etJabatan.getText().toString(),
                        etKluster.getText().toString(),
                        getJenis(jen),
                        etWhatsApp.getText().toString(),
                        map.get(sessionManager.KEY_NIP))
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getString("pesan").equals("Berhasil Menyimpan Data")){
                                            Toast.makeText(FormPerjalananActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(FormPerjalananActivity.this,MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(FormPerjalananActivity.this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(FormPerjalananActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

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
