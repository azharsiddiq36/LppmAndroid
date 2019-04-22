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

public class FormPengabdianActivity extends Activity {
    @BindView(R.id.etKetua)
    EditText etKetua;
    @BindView(R.id.etAnggota)
    AutoCompleteTextView etAnggota;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.tvTanggalSelesai)
    TextView tvTanggalSelesai;
    @BindView(R.id.btTanggalSelesai)
    Button btTanggalSelesai;
    @BindView(R.id.etInstansi)
    EditText etInstansi;
    @BindView(R.id.actvProvinsi)
    AutoCompleteTextView actvProvinsi;
    @BindView(R.id.actvKabupaten)
    AutoCompleteTextView actvKabupaten;
    @BindView(R.id.actvKecamatan)
    AutoCompleteTextView actvKecamatan;
    @BindView(R.id.actvDesa)
    AutoCompleteTextView actvDesa;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    ArrayList<String> arrayList = new ArrayList();
    private int length = 0;
    String tanggal1;
    String tanggal2;
    ProvInterface provInterface;
    List<Semuaprovinsi> provinsiArrayList = new ArrayList<>();
    List<Kabupaten> kabupatenArrayList = new ArrayList<>();
    List<Kecamatan> kecamatanArrayList = new ArrayList<>();

    List<Pegawai> pegawaiArrayList = new ArrayList<>();
    List<Desa> desaArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pengabdian);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(FormPengabdianActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        loadProv();
        loadPengguna();
        lppmInterface = CombineApi.getApiService();
        if (getIntent().getStringExtra("edit") == null){
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
                    if(checkForm(etInstansi.getText().toString()) == false){
                        a+="Instansi, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (checkForm(etKetua.getText().toString())==false){
                        a+="Ketua, ";
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
                    if (tvTanggalSelesai.getText().toString().equals("Tanggal Selesai")){
                        a+="Tanggal Selesai, ";
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
                    if (jumlah==10){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPengabdianActivity.this);
                        alertdialog.setTitle("Anda Yakin Ingin Mengajukan Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lppmInterface.insertPengabdian(map.get(sessionManager.KEY_NIP),
                                                etKetua.getText().toString(),
                                                arrayList.toString(),
                                                etJudul.getText().toString(),
                                                tanggal1+" - " +tanggal2,
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                etInstansi.getText().toString())
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                                                    Toast.makeText(FormPengabdianActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormPengabdianActivity.this,MainActivity.class);
                                                                    startActivity(i);
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
            String anggota []= getIntent().getStringExtra("anggota").split("-");
            Toast.makeText(this, ""+anggota[0], Toast.LENGTH_SHORT).show();
            for (int i = 0;i<anggota.length;i++) {
                tambahAnggota(anggota[i]);
            }
            String tanggal [] = getIntent().getStringExtra("tanggal").split("-");
            tvTanggal.setText(tanggal[0]);
            tvTanggalSelesai.setText(tanggal[1]);
            etJudul.setText(getIntent().getStringExtra("judul"));;
            actvProvinsi.setText(getIntent().getStringExtra("prov"));
            actvDesa.setText(getIntent().getStringExtra("des"));
            actvKabupaten.setText(getIntent().getStringExtra("kot"));
            actvKecamatan.setText(getIntent().getStringExtra("kec"));
            etInstansi.setText(getIntent().getStringExtra("instansi"));
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
                    if(checkForm(etInstansi.getText().toString()) == false){
                        a+="Instansi, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (checkForm(etKetua.getText().toString())==false){
                        a+="Ketua, ";
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
                    if (tvTanggalSelesai.getText().toString().equals("Tanggal Selesai")){
                        a+="Tanggal Selesai, ";
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
                    if (jumlah==10){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPengabdianActivity.this);
                        alertdialog.setTitle("Anda Yakin Ingin Mengubah Surat ?");
                        Log.d("kerok",""+getIntent().getStringExtra("tahun"));
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lppmInterface.editPengabdian(map.get(sessionManager.KEY_NIP),
                                                id_surat,
                                                getIntent().getStringExtra("tahun"),
                                                etKetua.getText().toString(),
                                                arrayList.toString(),
                                                etJudul.getText().toString(),
                                                tvTanggal.getText().toString()+" - " +tvTanggalSelesai.getText().toString(),
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                etInstansi.getText().toString())
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        Log.d("Layar","a");
                                                        if (response.isSuccessful()) {
                                                            Log.d("Layar","b");
                                                            try {
                                                                JSONObject Jobject = new JSONObject(response.body().string());
                                                                Log.d("Layar","d"+Jobject);
                                                                if (Jobject.getString("pesan").equals("berhasil memperbarui data")) {
                                                                    Toast.makeText(FormPengabdianActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormPengabdianActivity.this,MainActivity.class);
                                                                    startActivity(i);
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
                                                            Log.d("Layar","e");
                                                            Toast.makeText(FormPengabdianActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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
        etKetua.setText(String.valueOf(map.get(sessionManager.KEY_NAMA)));
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
        tvTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(1);
            }
        });
        btTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(1);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAnggota.getText().toString().equals("")){
                    Toast.makeText(FormPengabdianActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    TextView textView = (TextView)addView.findViewById(R.id.textout);
                    Button remove = (Button)addView.findViewById(R.id.remove);
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    sizeData(0);
                    Log.d("anggota",""+length);
                    insertAnggota(textView.getText().toString());
                    Toast.makeText(FormPengabdianActivity.this, "berhasil memperbarui data", Toast.LENGTH_SHORT).show();
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPengabdianActivity.this);
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
                CACProvinsiAdapter adapter = new CACProvinsiAdapter(FormPengabdianActivity.this,R.layout.form_pengabdian,provinsiArrayList);
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
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(FormPengabdianActivity.this,R.layout.form_pengabdian,kabupatenArrayList);
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
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(FormPengabdianActivity.this,R.layout.form_pengabdian,kecamatanArrayList);
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
                CACPegawaiAdapter adapter = new CACPegawaiAdapter(FormPengabdianActivity.this,
                        R.layout.form_penelitian,
                        pegawaiArrayList);
                etAnggota.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataPegawai> call, Throwable t) {

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
                CACDesaAdapter adapter = new CACDesaAdapter(FormPengabdianActivity.this,R.layout.form_pengabdian,desaArrayList);
                actvDesa.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvDesa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Desa desa : desaArrayList){
                            if (desa.getNama().equals(actvKecamatan.getText().toString())){
                                Toast.makeText(FormPengabdianActivity.this, "Kota yang anda pilih tidak terdaftar", Toast.LENGTH_SHORT).show();
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

                    tvTanggal.setText(""+dayOfMonth+"/"+namabulan+"/"+year);
                    tanggal1 = bulan+"/"+dayOfMonth+"/"+year;
                }
                else {
                    tvTanggalSelesai.setText("" +dayOfMonth+"/"+namabulan+"/"+year);
                    tanggal2 = bulan+"/"+dayOfMonth+"/"+year;
                    Log.d("tanggalKamibng",""+tanggal);
                }

            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void tambahAnggota(String s) {
        LayoutInflater layoutInflater  =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
        Button remove = (Button)addView.findViewById(R.id.remove);
        remove.setVisibility(View.INVISIBLE);
        textView.setText(s);
        Log.d("nyoba = ",textView.getText().toString());
        lyDinamic.addView(addView);
    }
}
