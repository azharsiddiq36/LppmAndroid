package com.example.azhar.lppm.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.CACDesaAdapter;
import com.example.azhar.lppm.Adapter.CACKabupatenAdapter;
import com.example.azhar.lppm.Adapter.CACKecamatanAdapter;
import com.example.azhar.lppm.Adapter.CACPegawaiAdapter;
import com.example.azhar.lppm.Adapter.CACProvinsiAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Controller.Utility;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPenelitianActivity extends Activity{
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    protected boolean keputusan = false;
    Uri outputFileUri;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.img_ktp)
    ImageView imgKtp;
    @BindView(R.id.etKetua)
    EditText etKetua;
    @BindView(R.id.etAnggota)
    AutoCompleteTextView etAnggota;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.lyDinamic)
    LinearLayout lyDinamic;
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
    @BindView(R.id.etKtp)
    EditText etKtp;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.etWhatsApp)
    EditText etWhatsApp;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.lyUpload)
    LinearLayout lyUpload;
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
    private String mImageUrl = "";
    private static final int RESULT_LOAD_IMG = 1;
    private static int TAKE_PICTURE = 100;
    String imgDecodableString;
    private static final String TAG = "CapturePicture";
    String tanggal1 = null;
    String tanggal2 = null;
    private String pictureFilePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_penelitian);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(FormPenelitianActivity.this);
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
                    if (checkForm(etEmail.getText().toString())==false){
                        a+="Email, ";
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
                    if(checkForm(etKtp.getText().toString())==false){
                        a+="Ktp, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(etWhatsApp.getText().toString())==false){
                        a+="No WhatsApp, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (jumlah==13){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPenelitianActivity.this);
                        alertdialog.setTitle("Anda Yakin Mengajukan Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //uploadFile();
                                        Log.d("rebikc",""+arrayList.get(0).toString());
                                        lppmInterface.insertPenelitian(map.get(sessionManager.KEY_EMAIL),
                                                map.get(sessionManager.KEY_NAMA),
                                                arrayList.toString(),
                                                etJudul.getText().toString(),
                                                tanggal1+" - " +tanggal2,
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
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
                                                                    Intent i = new Intent(FormPenelitianActivity.this,MainActivity.class);
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
            Toast.makeText(this, ""+anggota[0], Toast.LENGTH_SHORT).show();
            for (int i = 0;i<anggota.length;i++) {
                tambahAnggota(anggota[i]);
            }
            String tanggal [] = getIntent().getStringExtra("tanggal").split("-");
            tvTanggal.setText(tanggal[0]);
            tvTanggalSelesai.setText(tanggal[1]);
            etJudul.setText(getIntent().getStringExtra("judul"));
            etKtp.setText(getIntent().getStringExtra("ktp"));
            etWhatsApp.setText(getIntent().getStringExtra("wa"));
            etInstansi.setText(getIntent().getStringExtra("instansi"));
            actvProvinsi.setText(getIntent().getStringExtra("prov"));
            actvDesa.setText(getIntent().getStringExtra("des"));
            actvKabupaten.setText(getIntent().getStringExtra("kot"));
            actvKecamatan.setText(getIntent().getStringExtra("kec"));
            int index = Integer.parseInt(getIntent().getStringExtra("edit"))-1;
            final String id_surat = String.valueOf(index);
            Log.d("kepuyuk",""+id_surat);
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
                    if (checkForm(etEmail.getText().toString())==false){
                        a+="Email, ";
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
                    if(checkForm(etKtp.getText().toString())==false){
                        a+="Ktp, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if(checkForm(etWhatsApp.getText().toString())==false){
                        a+="No WhatsApp, ";
                    }
                    else {
                        jumlah+=1;
                    }
                    if (jumlah==13){
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPenelitianActivity.this);
                        alertdialog.setTitle("Anda Yakin Memperbaiki Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lppmInterface.editPenelitian(map.get(sessionManager.KEY_NIP),
                                                id_surat,
                                                getIntent().getStringExtra("tahun"),
                                                map.get(sessionManager.KEY_EMAIL),
                                                map.get(sessionManager.KEY_NAMA),
                                                arrayList.toString(),
                                                etJudul.getText().toString(),
                                                tvTanggal.getText().toString()+" - " +tvTanggalSelesai.getText().toString(),
                                                actvProvinsi.getText().toString(),
                                                actvKabupaten.getText().toString(),
                                                actvKecamatan.getText().toString(),
                                                actvDesa.getText().toString(),
                                                etInstansi.getText().toString(),
                                                etKtp.getText().toString(),
                                                "ayam",
                                                etWhatsApp.getText().toString())
                                                .enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccessful()) {
                                                            try {
                                                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                                                if (jsonRESULTS.getString("pesan").equals("berhasil memperbarui data")) {
                                                                    Toast.makeText(FormPenelitianActivity.this, "Berhasil Memperbarui Data Data", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(FormPenelitianActivity.this,MainActivity.class);
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
        etEmail.setText(String.valueOf(map.get(sessionManager.KEY_EMAIL)));
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
                    Toast.makeText(FormPenelitianActivity.this, "Berhasil menambahkan Anggota", Toast.LENGTH_SHORT).show();
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(FormPenelitianActivity.this);
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
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();


/*
                WindowManager wm = (WindowManager) getApplicationContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = 900;
                Log.d("rebick","+"+String.valueOf(size.y));
                LayoutInflater inflater = (LayoutInflater)
                        getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.upload_gambar, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setHeight(height);
                popupWindow.showAtLocation(v,Gravity.CENTER,0,0);
                popupView.setFocusable(true);
                CardView cvGallery,cvPhoto;
                Button btnKembali;
                btnKembali = (Button)popupView.findViewById(R.id.btnKembali);
                btnKembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                cvGallery = (CardView)popupView.findViewById(R.id.cvGallery);
                cvPhoto = (CardView)popupView.findViewById(R.id.cvPhoto);
                cvGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        popupWindow.dismiss();
                        keputusan = true;
                    }
                });
                cvPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(FormPenelitianActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            btnUpload.setEnabled(false);
                            ActivityCompat.requestPermissions(FormPenelitianActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                        }
                        else {
                            sendTakePictureIntent();
                            addToGallery();
                            popupWindow.dismiss();
                        }
                    }
                });
*/

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(FormPenelitianActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(FormPenelitianActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result) {

                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgKtp.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgKtp.setImageBitmap(bm);
    }
/*    private void setPic() {
        int targetW = imgKtp.getWidth();
        int targetH = imgKtp.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pictureFilePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(pictureFilePath, bmOptions);
        imgKtp.setImageBitmap(bitmap);
    }
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "Lppm_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    private void sendTakePictureIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            keputusan = false;
            startActivityForResult(cameraIntent, RESULT_LOAD_IMG);

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.azhar.lppm.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, RESULT_LOAD_IMG);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            try {
                if (keputusan == true){
                if (null!=data && requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn
                            ,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    imgKtp.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    Log.d("rebick",""+imgKtp.toString());
                    Toast.makeText(this, "Berhasil Memilih gambar", Toast.LENGTH_SHORT).show();
                    Picasso.get()
                            .load(selectedImage)
                            .resize(600, 400)
                            .centerInside()
                            .into((ImageView) findViewById(R.id.img_ktp));
                }
//               }
                 else {
                    Toast.makeText(this, "Gambar belum dipilih", Toast.LENGTH_SHORT).show();
                }
            }else {
                    if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imgKtp.setImageBitmap(imageBitmap);

//                        File imgFile = new  File(pictureFilePath);
//                        if(imgFile.exists())            {
//                            imgKtp.setImageURI(Uri.fromFile(imgFile));

                        }
                    }



            }
            catch (Exception e){
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }


    }

    private void addToGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void uploadImage(byte[] imageBytes) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        Call<Pengguna> call = lppmInterface.uploadImage(body);
        call.enqueue(new Callback<Pengguna>() {
            @Override
            public void onResponse(Call<Pengguna> call, Response<Pengguna> response) {
                if (response.isSuccessful()) {

                    Pengguna responseBody = response.body();
                   // mBtImageShow.setVisibility(View.VISIBLE);
                    mImageUrl = URL + responseBody.getTahun();
                   // Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {

                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                     //   Snackbar.make(findViewById(R.id.content), errorResponse.getMessage(), Snackbar.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pengguna> call, Throwable t) {

            }
        });
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
*/
    private void tambahAnggota(String s) {
        LayoutInflater layoutInflater  =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        Button remove = (Button)addView.findViewById(R.id.remove);
        this.arrayList.add(String.valueOf(s.replaceAll(",","@")));
        remove.setVisibility(View.INVISIBLE);
        textView.setText(s);
        Log.d("nyoba = ",textView.getText().toString());
        lyDinamic.addView(addView);
    }

    private boolean checkForm(String text) {
        if (text.length() > 0){
            return true;
        }
        else {
            return false;
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
                CACPegawaiAdapter adapter = new CACPegawaiAdapter(FormPenelitianActivity.this,
                        R.layout.form_penelitian,
                        pegawaiArrayList);
                etAnggota.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataPegawai> call, Throwable t) {

            }
        });

    }
    private void loadProv() {
        provInterface = CombineApi.getApiProv();
        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
            @Override
            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
                provinsiArrayList = response.body().getSemuaprovinsi();
                CACProvinsiAdapter adapter = new CACProvinsiAdapter(FormPenelitianActivity.this,R.layout.form_penelitian,provinsiArrayList);
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
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(FormPenelitianActivity.this,R.layout.form_penelitian,kabupatenArrayList);
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
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(FormPenelitianActivity.this,R.layout.form_penelitian,kecamatanArrayList);
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
                CACDesaAdapter adapter = new CACDesaAdapter(FormPenelitianActivity.this,R.layout.form_penelitian,desaArrayList);
                actvDesa.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                actvDesa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Desa desa : desaArrayList){
                            if (desa.getNama().equals(actvKecamatan.getText().toString())){
                                Toast.makeText(FormPenelitianActivity.this, "Kota yang anda pilih tidak terdaftar", Toast.LENGTH_SHORT).show();
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
        Log.d("rebikc",""+arrayList);
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
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                btnUpload.setEnabled(true);
//            }
//        }
//    }
//    private void uploadFile(){
//        File file = new File(pictureFilePath);
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse("*/*"),
//                        file
//                );
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("picture", sessionManager.KEY_NIP, requestFile);
//
//        /*RequestBody description =
//
//                RequestBody.create(
//                        okhttp3.MultipartBody.FORM, String.valueOf(sessionManager.KEY_NIP));
//        */
//        RequestBody description = toRequestBody(""+sessionManager.KEY_NAMA);
//  //   RequestBody description = RequestBody.create(MultipartBody.FORM,sessionManager.KEY_NIP);
//        Call<ResponseBody> call = lppmInterface.uploadImage(body,
//                                                            description);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.v("Upload", ""+t.getMessage());
//            }
//        });
//    }
//    public static RequestBody toRequestBody (String value) {
//        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
//        return body ;
//    }

}
