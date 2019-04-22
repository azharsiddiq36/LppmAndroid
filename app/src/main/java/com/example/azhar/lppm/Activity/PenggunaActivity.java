package com.example.azhar.lppm.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.PenggunaAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Model.DataUser;
import com.example.azhar.lppm.Model.Pengguna;
import com.example.azhar.lppm.R;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenggunaActivity extends Activity {
    @BindView(R.id.rv_History)
    RecyclerView rv_History;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    SessionManager sessionManager;
    LppmInterface lppmInterface;
    ProgressDialog progressDialog;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Pengguna> pengguna = new ArrayList<>();
    ArrayList<Integer> jumlahfgd = new ArrayList<>();
    ArrayList<Integer> jumlahpenelitian = new ArrayList<>();
    ArrayList<Integer> jumlahperjalanan = new ArrayList<>();
    ArrayList<Integer> jumlahpengabdian = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengguna);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(PenggunaActivity.this);
        final HashMap<String, String> map = sessionManager.getDetailsLoggin();
        progressDialog = new ProgressDialog(PenggunaActivity.this);
        layoutManager = new LinearLayoutManager(PenggunaActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_History.setLayoutManager(layoutManager);
        String akses = String.valueOf(map.get(sessionManager.KEY_HAK_AKSES));
        //checkLayout(getIntent().getStringExtra("layout"));
        final String jenisLayout = getIntent().getStringExtra("layout");
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jenisLayout.equals("penelitian")){
                    Intent i = new Intent(PenggunaActivity.this,FormPenelitianActivity.class);
                    startActivity(i);
                }
                else if (jenisLayout.equals("pengabdian")){
                    Intent i = new Intent(PenggunaActivity.this,FormPengabdianActivity.class);
                    startActivity(i);
                }
                else if (jenisLayout.equals("perjalanan")){
                    Intent i = new Intent(PenggunaActivity.this,FormPerjalananActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(PenggunaActivity.this,FormFgdActivity.class);
                    startActivity(i);

                }
            }
        });

        checkLayout(jenisLayout);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
    }

    private void checkLayout(final String layout) {
        lppmInterface = CombineApi.getApiService();
        Call<DataUser> getData = lppmInterface.listpengguna();
        getData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PenggunaActivity.this, "Halo", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
                pengguna = (ArrayList<Pengguna>) response.body().getUser();
                jumlahpenelitian = (ArrayList<Integer>) response.body().getPeneliti();
                jumlahpengabdian = (ArrayList<Integer>) response.body().getPengabdi();
                jumlahperjalanan = (ArrayList<Integer>) response.body().getPerjalanan();
                jumlahfgd = (ArrayList<Integer>) response.body().getFgd();
                ArrayList<Pengguna> baru;

                baru = new ArrayList<>();
                int a = 0;
                ArrayList<Integer> teliti = new ArrayList<>();
                ArrayList<Integer> abdi = new ArrayList<>();
                ArrayList<Integer> jalan = new ArrayList<>();
                ArrayList<Integer> efgede = new ArrayList<>();
                for (Object o : pengguna) {
                    if (pengguna.get(a).getNip().equals(String.valueOf(sessionManager.getDetailsLoggin().get(sessionManager.KEY_NIP)))) {
                        baru.add(pengguna.get(a));
                        teliti.add(jumlahpenelitian.get(a));
//                        if (jumlahpengabdian.get(a)==0){
//
//                        }
//                        else{
                            abdi.add(jumlahpengabdian.get(a));
//                        }
                        jalan.add(jumlahperjalanan.get(a));
                        efgede.add(jumlahfgd.get(a));
                    }
                    a++;
                }

                //Log.d("Rubick", "" + abdi.size());
                adapter = new PenggunaAdapter(PenggunaActivity.this,
                        baru,
                        efgede,
                        teliti,
                        jalan,
                        abdi, layout);
                rv_History.setAdapter(adapter);
                adapter.notifyDataSetChanged();

//                rv_History.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                    GestureDetector gestureDetector = new GestureDetector(PenggunaActivity.this,new GestureDetector.SimpleOnGestureListener(){
//                        public boolean onSingleTapUp(MotionEvent e){
//                            return true;
//                        }
//                    });
//                    @Override
//                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                        View child = rv.findChildViewUnder(e.getX(),e.getY());
//                        if (gestureDetector.onTouchEvent(e)){
//                            int position = rv.getChildAdapterPosition(child);
//                            Toast.makeText(PenggunaActivity.this, "ini adalah "+String.valueOf(pengguna.get(position).getId_pengguna()), Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(PenggunaActivity.this,PilihanActivity.class);
//                            i.putExtra("nip",""+pengguna.get(position).getNip());
//                            startActivity(i);
//                        }
//                        return false;
//                    }
//                    @Override
//                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//                    }
//                    @Override
//                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                    }
//                });
            }

            //
            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {

            }
        });

    }
}
