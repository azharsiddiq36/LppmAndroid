package com.example.azhar.lppm.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.CACDesaAdapter;
import com.example.azhar.lppm.Adapter.CACKabupatenAdapter;
import com.example.azhar.lppm.Adapter.CACKecamatanAdapter;
import com.example.azhar.lppm.Adapter.CACPegawaiAdapter;
import com.example.azhar.lppm.Adapter.CACProvinsiAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Model.DataPegawai;
import com.example.azhar.lppm.Model.Desa;
import com.example.azhar.lppm.Model.Kabupaten;
import com.example.azhar.lppm.Model.Kecamatan;
import com.example.azhar.lppm.Model.ModelDesa;
import com.example.azhar.lppm.Model.ModelKabupaten;
import com.example.azhar.lppm.Model.ModelKecamatan;
import com.example.azhar.lppm.Model.ModelProvinsi;
import com.example.azhar.lppm.Model.Pegawai;
import com.example.azhar.lppm.Model.Semuaprovinsi;
import com.example.azhar.lppm.R;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;
import com.example.azhar.lppm.Rest.ProvInterface;

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

public class FormFgdActivity extends Activity {
    @BindView(R.id.etModerator)
    EditText etModerator;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.etAnggota)
    AutoCompleteTextView etAnggota;
    @BindView(R.id.etNarasumber)
    EditText etNarasumber;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.rbPenelitian)
    RadioButton rbPenelitian;
    @BindView(R.id.rbPengabdian)
    RadioButton rbPengabdian;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    @BindView(R.id.actvProvinsi)
    AutoCompleteTextView actvProvinsi;
    @BindView(R.id.actvKabupaten)
    AutoCompleteTextView actvKabupaten;
    @BindView(R.id.actvKecamatan)
    AutoCompleteTextView actvKecamatan;
    @BindView(R.id.actvDesa)
    AutoCompleteTextView actvDesa;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    ArrayList<String> arrayList = new ArrayList();
    private int length = 0;
    ProvInterface provInterface;
    List<Semuaprovinsi> provinsiArrayList = new ArrayList<>();
    List<Kabupaten> kabupatenArrayList = new ArrayList<>();
    List<Kecamatan> kecamatanArrayList = new ArrayList<>();
    List<Desa> desaArrayList = new ArrayList<>();
    List<Pegawai> pegawaiArrayList = new ArrayList<>();
    String tanggal1,tanggal2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fgd);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(FormFgdActivity.this);
        loadPengguna();
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        if (getIntent().getStringExtra("edit")==null){
            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a = "";
                    int jumlah = 0;
                    if(checkForm(etJudul.getText().toString()) == false){
                        a += "Judul, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(etNarasumber.getText().toString()) == false){
                        a+="Narasumber, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (checkForm(etModerator.getText().toString())==false){
                        a+="Moderator, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(arrayList.size() ==0){
                        a+="Anggota, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (tvTanggal.getText().toString().equals("Tanggal Mulai")){
                        a+="Tanggal Mulai, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvProvinsi.getText().toString())==false){
                        a+="Provinsi, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvKabupaten.getText().toString())==false){
                        a+="Kabupaten, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvKecamatan.getText().toString())==false){
                        a+="Kecamatan, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvDesa.getText().toString())==false){
                        a+="Desa, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (jumlah==9){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormFgdActivity.this);
                        alertdialog.setTitle("Anda Yakin Mengajukan Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String jen = "";
                                        if (rbPenelitian.isChecked()){
                                            jen = "Penelitian";
                                        }
                                        lppmInterface.insertFGD(map.get(sessionManager.KEY_NIP),
                                                etNarasumber.getText().toString(),
                                                etModerator.getText().toString(),
                                                etJudul.getText().toString(),
                                                getJenis(jen),
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                arrayList.toString(),
                                                tanggal1)
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                                                    Toast.makeText(FormFgdActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormFgdActivity.this,MainActivity.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(FormFgdActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            Toast.makeText(FormFgdActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                        Log.e("debug", "onFailure: ERROR > " + t.toString());

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
                    Snackbar.make(v, a+" Tidak Boleh Kosong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });

        }else{
            etNarasumber.setText(getIntent().getStringExtra("nar"));
            etModerator.setText(getIntent().getStringExtra("mod"));
            etJudul.setText(getIntent().getStringExtra("jud"));
            String anggota []= getIntent().getStringExtra("anggota").split("-");
            Toast.makeText(this, ""+anggota[0], Toast.LENGTH_SHORT).show();
            for (int i = 0;i<anggota.length;i++) {
                tambahAnggota(anggota[i]);
            }
            tvTanggal.setText(getIntent().getStringExtra("tanggal"));
            etJudul.setText(getIntent().getStringExtra("judul"));
            if (getIntent().getStringExtra("jen").toLowerCase().equals("penelitian")){
                rbPenelitian.setChecked(true);
            }
            else {
                rbPengabdian.setChecked(true);
            }
            actvProvinsi.setText(getIntent().getStringExtra("prov"));
            actvDesa.setText(getIntent().getStringExtra("des"));
            actvKabupaten.setText(getIntent().getStringExtra("kot"));
            actvKecamatan.setText(getIntent().getStringExtra("kec"));
            int index = Integer.parseInt(getIntent().getStringExtra("edit"))-1;
            final String id_surat = String.valueOf(index);
            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a = "";
                    int jumlah = 0;
                    if(checkForm(etJudul.getText().toString()) == false){
                        a += "Judul, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(etNarasumber.getText().toString()) == false){
                        a+="Narasumber, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (checkForm(etModerator.getText().toString())==false){
                        a+="Moderator, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(arrayList.size() ==0){
                        a+="Anggota, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (tvTanggal.getText().toString().equals("Tanggal Mulai")){
                        a+="Tanggal Mulai, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvProvinsi.getText().toString())==false){
                        a+="Provinsi, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvKabupaten.getText().toString())==false){
                        a+="Kabupaten, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvKecamatan.getText().toString())==false){
                        a+="Kecamatan, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(actvDesa.getText().toString())==false){
                        a+="Desa, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (jumlah==9){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormFgdActivity.this);
                        alertdialog.setTitle("Anda Yakin Memperbarui Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String jen = "";
                                        if (rbPenelitian.isChecked()){
                                            jen = "Penelitian";
                                        }
                                        lppmInterface.editFGD(map.get(sessionManager.KEY_NIP),
                                                id_surat,
                                                getIntent().getStringExtra("tahun"),
                                                etNarasumber.getText().toString(),
                                                etModerator.getText().toString(),
                                                etJudul.getText().toString(),
                                                getJenis(jen),
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                arrayList.toString(),
                                                tvTanggal.getText().toString())
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                Log.d("kepiting",""+jsonRESULTS.getString("pesan"));
                                                                if (jsonRESULTS.getString("pesan").equals("berhasil memperbarui data")) {
                                                                    Toast.makeText(FormFgdActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormFgdActivity.this,MainActivity.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(FormFgdActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            Toast.makeText(FormFgdActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                        Log.e("debug", "onFailure: ERROR > " + t.toString());

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
                    Snackbar.make(v, a+" Tidak Boleh Kosong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });

        }

        loadProv();
        lppmInterface = CombineApi.getApiService();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        btTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(0);
            }
        });
        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(0);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAnggota.getText().toString().equals("")){
                    Toast.makeText(FormFgdActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    TextView textView = (TextView)addView.findViewById(R.id.textout);
                    Button remove = (Button)addView.findViewById(R.id.remove);
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    sizeData(0);
                    Log.d("anggota",""+length);
                    insertAnggota(textView.getText().toString());
                    Toast.makeText(FormFgdActivity.this, "Berhasil menambahkan Anggota", Toast.LENGTH_SHORT).show();
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormFgdActivity.this);
                            alertdialog.setTitle("Apakah Anda ingin Menghapus anggota ?");
                            alertdialog.setMessage("Klik Ya untuk Hapus")
                                    .setCancelable(false)
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((LinearLayout)addView.getParent()).removeView(addView);
                                            sizeData(1);
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
                    lyDinamic.addView(addView);
                }
            }
        });

    }
    private void tambahAnggota(String s) {
        LayoutInflater layoutInflater  =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        Button remove = (Button)addView.findViewById(R.id.remove);
        remove.setVisibility(View.INVISIBLE);
        textView.setText(s);
        this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
        Log.d("nyoba = ",textView.getText().toString());
        lyDinamic.addView(addView);
    }
    private void loadPengguna(){
        lppmInterface=CombineApi.getApiService();
        Call<DataPegawai> getPegawai = lppmInterface.listPegawai();
        getPegawai.enqueue(new Callback<DataPegawai>() {
            @Override
            public void onResponse(Call<DataPegawai> call, Response<DataPegawai> response) {
                int a = 0;

                pegawaiArrayList = response.body().getPegawai();
//
//                for(Object o : pegawaiArrayList){
//                    Log.d("rebick", "onResponse: "+response.body().getPegawai().get(a).getNama());
//                    a++;
//                }
                CACPegawaiAdapter adapter = new CACPegawaiAdapter(getBaseContext(),
                        R.layout.form_penelitian,
                        pegawaiArrayList);
                etAnggota.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataPegawai> call, Throwable t) {

            }
        });

    }
    private boolean checkForm(String text) {
        if (text.length() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    private void loadProv() {
        provInterface = CombineApi.getApiProv();
        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
            @Override
            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
                provinsiArrayList = response.body().getSemuaprovinsi();
                CACProvinsiAdapter adapter = new CACProvinsiAdapter(FormFgdActivity.this,R.layout.form_fgd,provinsiArrayList);
                actvProvinsi.setAdapter(adapter);
                actvProvinsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Semuaprovinsi semuaprovinsi : provinsiArrayList){
                            if (semuaprovinsi.getNama().equals(actvProvinsi.getText().toString())){
                                loadKabupaten(semuaprovinsi.getId());
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelProvinsi> call, Throwable t) {

            }
        });
    }
    private void loadKabupaten(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKabupaten> getKabupaten = provInterface.getKabupaten(id);
        getKabupaten.enqueue(new Callback<ModelKabupaten>() {
            @Override
            public void onResponse(Call<ModelKabupaten> call, Response<ModelKabupaten> response) {
                kabupatenArrayList = response.body().getDaftarKecamatan();
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(FormFgdActivity.this,R.layout.form_fgd,kabupatenArrayList);
                actvKabupaten.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvKabupaten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kabupaten kabupaten : kabupatenArrayList){
                            if (kabupaten.getNama().equals(actvKabupaten.getText().toString())){
                                loadKecamatan(kabupaten.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKabupaten> call, Throwable t) {

            }
        });
    }
    private void loadKecamatan(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKecamatan> getKecamatan = provInterface.getKecamatan(id);
        getKecamatan.enqueue(new Callback<ModelKecamatan>() {
            @Override
            public void onResponse(Call<ModelKecamatan> call, Response<ModelKecamatan> response) {
                kecamatanArrayList = response.body().getDaftarKecamatan();
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(FormFgdActivity.this,R.layout.form_fgd,kecamatanArrayList);
                actvKecamatan.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvKecamatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kecamatan kecamatan : kecamatanArrayList){
                            if (kecamatan.getNama().equals(actvKecamatan.getText().toString())){
                                loadDesa(kecamatan.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKecamatan> call, Throwable t) {

            }
        });
    }
    private void loadDesa(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelDesa> getDesa = provInterface.getDesa(id);

        getDesa.enqueue(new Callback<ModelDesa>() {
            @Override
            public void onResponse(Call<ModelDesa> call, Response<ModelDesa> response) {
                desaArrayList = response.body().getDaftarDesa();
                CACDesaAdapter adapter = new CACDesaAdapter(FormFgdActivity.this,R.layout.form_fgd,desaArrayList);
                actvDesa.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvDesa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Desa desa : desaArrayList){
                            if (desa.getNama().equals(actvKecamatan.getText().toString())){
                                Toast.makeText(FormFgdActivity.this, "Kota yang anda pilih tidak terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelDesa> call, Throwable t) {

            }
        });
    }
    private void insertAnggota(String s) {
        this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
        Log.d("anggota",""+arrayList);

    }
    private int sizeData(int a) {

        if(a == 0){
            return this.length +=1;
        }else {
            return this.length -=1;
        }

    }
    private void showDateDialog(final int a) {
        Calendar calendar = Calendar.getInstance();
        int year,month,day;
        year =  calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Calendar newDate = Calendar.getInstance();
                //newDate.set(year"/"monthOfYear, dayOfMonth);
                int bulan = monthOfYear+1;
                String tanggal = dayOfMonth+"/"+bulan+"/"+year;
                String namabulan = null;
                switch (bulan){
                    case 1: namabulan = "Januari";
                        break;
                    case 2: namabulan = "Februari";
                        break;
                    case 3: namabulan = "Maret";
                        break;
                    case 4: namabulan = "April";
                        break;
                    case 5: namabulan = "Mei";
                        break;
                    case 6: namabulan = "Juni";
                        break;
                    case 7: namabulan = "Juli";
                        break;
                    case 8: namabulan = "Agustus";
                        break;
                    case 9: namabulan = "September";
                        break;
                    case 10: namabulan = "Oktober";
                        break;
                    case 11: namabulan = "November";
                        break;
                    case 12: namabulan = "Desember";
                        break;

                }
                if (a == 0 ) {
                    tvTanggal.setText("" +dayOfMonth+"/"+namabulan+"/"+year);
                    tanggal1 = tanggal;
                    Log.d("tanggalKamibng",""+dayOfMonth+"/"+namabulan+"/"+year);
                }


            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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
}
