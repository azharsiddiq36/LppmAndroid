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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
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
public class FormPerjalananActivity extends Activity {
    @BindView(R.id.etKetua)
    EditText etKetua;
    @BindView(R.id.spJabatan)
    Spinner spJabatan;
    @BindView(R.id.spGolongan)
    Spinner spGolongan;
    @BindView(R.id.etAnggota)
    AutoCompleteTextView etAnggota;
    @BindView(R.id.etJudul)
    EditText etJudul;
    @BindView(R.id.etKluster)
    EditText etKluster;
    @BindView(R.id.tvTanggal)
    TextView tvTanggal;
    @BindView(R.id.btTanggal)
    Button btTanggal;
    @BindView(R.id.tvTanggalKluster)
    TextView tvTanggalKluster;
    @BindView(R.id.btTanggalKluster)
    Button btTanggalKluster;
    @BindView(R.id.tvTanggalSelesai)
    TextView tvTanggalSelesai;
    @BindView(R.id.btTanggalSelesai)
    Button btTanggalSelesai;
    @BindView(R.id.etNomor)
    EditText etNomor;
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
    @BindView(R.id.rbPenelitian)
    RadioButton rbPenelitian;
    @BindView(R.id.rbPengabdian)
    RadioButton rbPengabdian;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
    DatePickerDialog datePickerDialog;
    LppmInterface lppmInterface;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    ArrayList<String> arrayList = new ArrayList();
    ArrayList<String> arrayJab = new ArrayList();
    ArrayList<String> arrayPang = new ArrayList();
    private int length = 0;
    ProvInterface provInterface;
    List<Semuaprovinsi> provinsiArrayList = new ArrayList<>();
    List<Kabupaten> kabupatenArrayList = new ArrayList<>();
    List<Kecamatan> kecamatanArrayList = new ArrayList<>();
    List<Desa> desaArrayList = new ArrayList<>();
    String tanggal1,tanggal2,tanggal3;
    List<Pegawai> pegawaiArrayList = new ArrayList<>();
    String Jab = null;
    boolean benar=false;
    String Gol = null;
    String Jabatan[]={"Asisten Ahli","Lektor","Lektor Kepala","Guru Besar"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_perjalanan);
        ButterKnife.bind(this);
        final ArrayAdapter<String> jabatanAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,Jabatan);

        spJabatan.setAdapter(jabatanAdapter);
        sessionManager = new SessionManager(FormPerjalananActivity.this);
        final HashMap<String,String> map = sessionManager.getDetailsLoggin();
        loadProv();
        loadPengguna();
        lppmInterface = CombineApi.getApiService();
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
        tvTanggalKluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(2);
            }
        });
        btTanggalKluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(2);
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
        spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setJab(jabatanAdapter.getItem(position));
                Jab = ""+jabatanAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (spGolongan.toString().equals("")){
//                    Toast.makeText(FormPerjalananActivity.this, "Harap Mengisi Pangkat Terlebih dahulu", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    setJab("asisten ahli");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAnggota.getText().toString().equals("")){
                    Toast.makeText(FormPerjalananActivity.this, "Nama Anggota Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row_jab_gol, null);
                    final TextView textView = (TextView)addView.findViewById(R.id.textout);
                    Spinner spJabAng = (Spinner) addView.findViewById(R.id.spJabAng);
                    final Spinner spGolAng = (Spinner) addView.findViewById(R.id.spGolAng);
                    Button remove = (Button)addView.findViewById(R.id.remove);
                    final ArrayAdapter<String> JabAng = new ArrayAdapter<>(getBaseContext(),
                            R.layout.spinner_item,Jabatan);
                    spJabAng.setAdapter(JabAng);
                    ArrayAdapter adapter1 = null;
                    textView.setText(String.valueOf(etAnggota.getText().toString()));
                    spJabAng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final String Jbtn = JabAng.getItem(position);
                            final ArrayAdapter<String> adapter;
                            if (JabAng.getItem(position).toLowerCase().equals("asisten ahli")){
                                String [] list = {"Penata Muda Tk.I. III/b"};
                                adapter = new ArrayAdapter<>(getBaseContext(),
                                        R.layout.spinner_item,list);

                            }
                            else if(JabAng.getItem(position).toLowerCase().equals("lektor")){
                                String [] list = {"Penata. III/c",
                                        "Penata Tk.I. III/d"};
                                adapter = new ArrayAdapter<>(getBaseContext(),
                                        R.layout.spinner_item,list);
                            }
                            else if(JabAng.getItem(position).toLowerCase().equals("lektor kepala")){
                                String [] list = {"Pembina. IV/a",
                                        "Pembina Tk.I. IV/b",
                                        "Pembina Utama Muda. IV/c"};
                                adapter = new ArrayAdapter<>(getBaseContext(),
                                        R.layout.spinner_item,list);
                            }
                            else {
                                String [] list = {"Pembina Utama Madya. IV/d",
                                        "Pembina Utama. IV/e"};
                                adapter = new ArrayAdapter<>(getBaseContext(),
                                        R.layout.spinner_item,list);
                            }
                            spGolAng.setAdapter(adapter);
                            spGolAng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    insertAnggota(textView.getText().toString(),Jbtn,adapter.getItem(position));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    sizeData(0);
                    Log.d("anggota",""+length);
                    Toast.makeText(FormPerjalananActivity.this, "Berhasil menambahkan Anggota", Toast.LENGTH_SHORT).show();
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPerjalananActivity.this);
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
                    if(checkForm(etNomor.getText().toString()) == false){
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

                    if (tvTanggalKluster.getText().toString().equals("Tanggal Kluster")){
                        a+="Tanggal Kluster, ";
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

                    if(checkForm(etKluster.getText().toString())==false){
                        a+="Kluster, ";
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
                    if (jumlah==12){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPerjalananActivity.this);
                        alertdialog.setTitle("Anda Yakin Ingin Mengajukan Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String jen = "";
                                        if (rbPenelitian.isChecked()){
                                            jen = "Penelitian";
                                        }

                                        lppmInterface.insertPerjalanan(etKetua.getText().toString(),
                                                Gol,
                                                Jab,
                                                arrayList.toString(),
                                                arrayJab.toString(),
                                                arrayPang.toString(),
                                                etJudul.getText().toString(),
                                                tanggal1+" - " +tanggal2,
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                etKluster.getText().toString(),
                                                tanggal3,
                                                getJenis(jen),
                                                etNomor.getText().toString(),
                                                map.get(sessionManager.KEY_NIP))
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                if (jsonRESULTS.getString("pesan").equals("Berhasil Menyimpan Data")) {
                                                                    Toast.makeText(FormPerjalananActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormPerjalananActivity.this,MainActivity.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(FormPerjalananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            Toast.makeText(FormPerjalananActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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
        }else {
            String anggota []= getIntent().getStringExtra("anggota").split("-");
            String jbtnang []=getIntent().getStringExtra("jabang").split("-");
            String gollang [] = getIntent().getStringExtra("golang").split("-");
            Toast.makeText(this, ""+anggota[0], Toast.LENGTH_SHORT).show();
            for (int i = 0;i<anggota.length;i++) {
                tambahAnggota(anggota[i],jbtnang[i],gollang[i]);
                Log.d("kepiting",""+anggota.length);
                Log.d("kepiting",""+jbtnang[0]);
                Log.d("kepiting",""+gollang[0]);
            }
            tvTanggalKluster.setText(getIntent().getStringExtra("tglklu"));
            etKluster.setText(getIntent().getStringExtra("klu"));
            etNomor.setText(getIntent().getStringExtra("no"));
            ArrayAdapter adapter1 = null;
            if (getIntent().getStringExtra("ketjab").equals("Asisten Ahli")){
                spJabatan.setSelection(0);
                String [] list = {"Penata Muda Tk.I. III/b"};
                adapter1 = new ArrayAdapter<>(getBaseContext(),
                        R.layout.spinner_item,list);
                spGolongan.setAdapter(adapter1);
                spGolongan.setSelection(0);
            }
            else if(getIntent().getStringExtra("ketjab").equals("Lektor")){
                spJabatan.setSelection(1);
                String[]list = {"Penata. III/c",
                        "Penata Tk.I. III/d"};
                adapter1 = new ArrayAdapter<>(getBaseContext(),
                        R.layout.spinner_item,list);
                spGolongan.setAdapter(adapter1);
                if (getIntent().getStringExtra("ketpang").toLowerCase().equals("Penata. III/c")){
                    spGolongan.setSelection(0);
                }
                else {
                    spGolongan.setSelection(1);
                }
            }
            else if(getIntent().getStringExtra("ketjab").equals("Lektor Kepala")){
                spJabatan.setSelection(2);
                String [] list = {"Pembina. IV/a",
                        "Pembina Tk.I. IV/b",
                        "Pembina Utama Muda. IV/c"};
                adapter1= new ArrayAdapter<>(getBaseContext(),
                        R.layout.spinner_item,list);
                spGolongan.setAdapter(adapter1);
                if (getIntent().getStringExtra("ketpang").toLowerCase().equals("Pembina. IV/a")){
                    spGolongan.setSelection(0);
                }
                else if(getIntent().getStringExtra("ketpang").toLowerCase().equals("Pembina Tk.I. IV/b")){
                    spGolongan.setSelection(1);
                }
                else {
                    spGolongan.setSelection(2);
                }
            }
            else{
                spJabatan.setSelection(3);
                String [] list = {"Pembina Utama Madya. IV/d",
                        "Pembina Utama. IV/e"};
                adapter1 = new ArrayAdapter<>(getBaseContext(),
                        R.layout.spinner_item,list);
                spGolongan.setAdapter(adapter1);
                if (getIntent().getStringExtra("ketpang").toLowerCase().equals("Pembina Utama Madya. IV/d")){
                    spGolongan.setSelection(0);
                }
                else {
                    spGolongan.setSelection(1);
                }
            }
//                case "Asisten Ahli":
//                    spJabatan.setSelection(0);
//                    break;
//                case "Lektor":
//                    spJabatan.setSelection(1);
//                    break;
//                case "Lektor Kepala":
//                    spJabatan.setSelection(2);
//                    break;
//                case "Guru Besar":
//                    spJabatan.setSelection(3);
//                    break;
            String tanggal [] = getIntent().getStringExtra("tanggal").split("-");
            tvTanggal.setText(tanggal[0]);
            tvTanggalSelesai.setText(tanggal[1]);
            etJudul.setText(getIntent().getStringExtra("judul"));;
            actvProvinsi.setText(getIntent().getStringExtra("prov"));
            actvDesa.setText(getIntent().getStringExtra("des"));
            actvKabupaten.setText(getIntent().getStringExtra("kot"));
            actvKecamatan.setText(getIntent().getStringExtra("kec"));
            if (getIntent().getStringExtra("jen").toLowerCase().equals("penelitian")){
                rbPenelitian.setChecked(true);
            }
            else {
                rbPengabdian.setChecked(true);
            }
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
                    if(checkForm(etNomor.getText().toString()) == false){
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

                    if (tvTanggalKluster.getText().toString().equals("Tanggal Kluster")){
                        a+="Tanggal Kluster, ";
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

                    if(checkForm(etKluster.getText().toString())==false){
                        a+="Kluster, ";
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
                    if (jumlah==12){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPerjalananActivity.this);
                        alertdialog.setTitle("Anda Yakin Ingin Mengajukan Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String jen = "";
                                        if (rbPenelitian.isChecked()){
                                            jen = "Penelitian";
                                        }

                                        lppmInterface.editPerjalanan(map.get(sessionManager.KEY_NIP),
                                                id_surat,
                                                getIntent().getStringExtra("tahun"),
                                                etKetua.getText().toString(),
                                                Gol,
                                                Jab,
                                                arrayList.toString(),
                                                arrayJab.toString(),
                                                arrayPang.toString(),
                                                etJudul.getText().toString(),
                                                tvTanggal.getText().toString()+" - " +tvTanggalSelesai.getText().toString(),
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                etKluster.getText().toString(),
                                                tvTanggalKluster.getText().toString(),
                                                getJenis(jen),
                                                etNomor.getText().toString())
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                if (jsonRESULTS.getString("pesan").equals("berhasil memperbarui data")) {
                                                                    Toast.makeText(FormPerjalananActivity.this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormPerjalananActivity.this,MainActivity.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(FormPerjalananActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            Toast.makeText(FormPerjalananActivity.this, "Harap Periksa Jaringan Anda", Toast.LENGTH_SHORT).show();

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
                CACPegawaiAdapter adapter = new CACPegawaiAdapter(FormPerjalananActivity.this,
                        R.layout.form_penelitian,
                        pegawaiArrayList);
                etAnggota.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataPegawai> call, Throwable t) {

            }
        });

    }
    private void setJab(String s) {
        final ArrayAdapter<String> adapter;
        if (s.toLowerCase().equals("asisten ahli")){
            String [] list = {"Penata Muda Tk.I. III/b"};
            adapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item,list);

        }
        else if(s.toLowerCase().equals("lektor")){
            String [] list = {"Penata. III/c",
                                "Penata Tk.I. III/d"};
            adapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item,list);
        }
        else if(s.toLowerCase().equals("lektor kepala")){
            String [] list = {"Pembina. IV/a",
                                "Pembina Tk.I. IV/b",
                                "Pembina Utama Muda. IV/c"};
            adapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item,list);
        }
        else {
            String [] list = {"Pembina Utama Madya. IV/d",
                                "Pembina Utama. IV/e"};
            adapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item,list);
        }
        spGolongan.setAdapter(adapter);
        spGolongan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gol = ""+adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private String getJenis(String jenis) {
            if (jenis.equals("Penelitian")){
                jenis = "penelitian";

            }
            else {
                jenis = "pengabdian";
            }
            return jenis;

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
                CACProvinsiAdapter adapter = new CACProvinsiAdapter(FormPerjalananActivity.this,R.layout.form_pengabdian,provinsiArrayList);
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
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(FormPerjalananActivity.this,R.layout.form_pengabdian,kabupatenArrayList);
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
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(FormPerjalananActivity.this,R.layout.form_pengabdian,kecamatanArrayList);
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
                CACDesaAdapter adapter = new CACDesaAdapter(FormPerjalananActivity.this,R.layout.form_pengabdian,desaArrayList);
                actvDesa.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvDesa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Desa desa : desaArrayList){
                            if (desa.getNama().equals(actvKecamatan.getText().toString())){
                                Toast.makeText(FormPerjalananActivity.this, "Kota yang anda pilih tidak terdaftar", Toast.LENGTH_SHORT).show();
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
    private void tambahAnggota(String s,String jbtn,String gol) {
        LayoutInflater layoutInflater  =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_jab_gol, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        Button remove = (Button)addView.findViewById(R.id.remove);
        remove.setVisibility(View.INVISIBLE);
        insertAnggota(s,jbtn,gol);
       /* if (jbtn.equals("Asisten ")){

            spJabatan.setSelection(0);
            String [] list = {"Penata Muda Tk.I. III/b"};
            adapter1 = new ArrayAdapter<>(getBaseContext(),
                    R.layout.spinner_item,list);
            spGolongan.setAdapter(adapter1);
            spGolongan.setSelection(0);
        }
        else{

        }*/
        textView.setText(s);
        Log.d("nyoba = ",textView.getText().toString());
        lyDinamic.addView(addView);
    }
    private void insertAnggota(String s,String jbtn,String gol) {
        int x =0;
        if (arrayList.size()==0){
            this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
            Log.d("rebikc",""+s);
            Log.d("rebikc1",""+jbtn);
            Log.d("rebikc2","s"+gol);
            this.arrayJab.add(String.valueOf(jbtn));
            this.arrayPang.add(String.valueOf(gol));
        }else {
        for (int i = 0;i<arrayList.size();i++){
            if (s.equals(arrayList.get(i))){
                this.arrayList.set(i,String.valueOf(s.replaceAll(",","@")));
                this.arrayJab.set(i,jbtn);
                this.arrayPang.set(i,gol);
                x+=1;
                }
            Log.d("kentang",""+arrayList.get(i));
            Log.d("kentang1",""+arrayJab.get(i));
            Log.d("kentang2","s"+arrayPang.get(i));
        }
            if (x==0){
                this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
                Log.d("rebikc",""+s);
                Log.d("rebikc1",""+jbtn);
                Log.d("rebikc2","s"+gol);
                this.arrayJab.add(String.valueOf(jbtn));
                this.arrayPang.add(String.valueOf(gol));
            }
        }



//            if (s.equals(arrayList.get(length-1)))
//            {
////                this.arrayList.set(length,arrayList.get(length-1));
//                Log.d("rebikc",""+s);
//                Log.d("rebikc1",""+jbtn);
//                Log.d("rebikc2","s"+gol);
//                this.arrayJab.set(length,String.valueOf(jbtn));
//                this.arrayPang.set(length,String.valueOf(gol));
//            }
//            else {
//                this.arrayList.add(String.valueOf(s));
//                Log.d("rebikc",""+s);
//                Log.d("rebikc1",""+jbtn);
//                Log.d("rebikc2","s"+gol);
//                this.arrayJab.add(String.valueOf(jbtn));
//                this.arrayPang.add(String.valueOf(gol));
//            }


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
                    tanggal1 = tanggal;
                    Log.d("tanggalKamibng",""+dayOfMonth+"/"+namabulan+"/"+year);
                }
                else if(a==2){
                    tanggal2 = tanggal;
                    tvTanggalKluster.setText(""+dayOfMonth+"/"+namabulan+"/"+year);
                }
                else {
                    tanggal3 = tanggal;
                    tvTanggalSelesai.setText(""+dayOfMonth+"/"+namabulan+"/"+year);
                    Log.d("tanggalKamibng",""+dayOfMonth+"/"+namabulan+"/"+year);
                }

            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
