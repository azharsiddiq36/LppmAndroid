package com.example.azhar.lppm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.FgdAdapter;
import com.example.azhar.lppm.Adapter.PenggunaAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Model.DataUser;
import com.example.azhar.lppm.Model.DataUser;
import com.example.azhar.lppm.Model.Pengguna;
import com.example.azhar.lppm.Rest.CombineApi;
import com.example.azhar.lppm.Rest.LppmInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenggunaActivity extends Activity {
//    @BindView(R.id.tvNama)
//    TextView tvNama;
//    @BindView(R.id.tvNip)
//    TextView tvNip;
    @BindView(R.id.rv_History)
    RecyclerView rv_History;
//    @BindView(R.id.tvJabatan)
//    TextView tvJabatan;
//    @BindView(R.id.foto)
//    ImageView foto;
//    @BindView(R.id.cvLetter)
//    CardView cvLetter;
    SessionManager sessionManager;
    LppmInterface lppmInterface;
    ProgressDialog progressDialog;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<Pengguna> pengguna = new ArrayList<>();
    List<Integer> jumlahfgd = new ArrayList<>();
    List<Integer> jumlahpenelitian = new ArrayList<>();
    List<Integer> jumlahperjalanan = new ArrayList<>();
    List<Integer> jumlahpengabdian = new ArrayList<>();
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
        checkLayout();
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
    }

    private void checkLayout() {
        lppmInterface = CombineApi.getApiService();
        Call<DataUser> getData = lppmInterface.listpengguna();
        getData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if (response.isSuccessful()){
                    Toast.makeText(PenggunaActivity.this, "Halo", Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
                String a = response.body().getPesan();
                String x = response.body().getPeneliti().toString();
                pengguna = response.body().getUser();
                jumlahpenelitian = response.body().getPeneliti();
                jumlahpengabdian = response.body().getPengabdi();
                jumlahperjalanan = response.body().getPerjalanan();
                jumlahfgd = response.body().getFgd();
                Log.d("kambing",""+a);
                Log.d("kambing",""+x);
                adapter = new PenggunaAdapter(PenggunaActivity.this,
                        pengguna,
                        jumlahfgd,
                        jumlahpenelitian,
                        jumlahperjalanan,
                        jumlahpengabdian);
                rv_History.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rv_History.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(PenggunaActivity.this,new GestureDetector.SimpleOnGestureListener(){
                        public boolean onSingleTapUp(MotionEvent e){
                            return true;
                        }
                    });
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(),e.getY());
                        if (gestureDetector.onTouchEvent(e)){
                            int position = rv.getChildAdapterPosition(child);
                            int index = position-1;
                            Toast.makeText(PenggunaActivity.this, "ini adalah "+pengguna.get(position).getId_pengguna(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PenggunaActivity.this,PilihanActivity.class);
                            i.putExtra("nip",""+pengguna.get(position).getNip());
                            startActivity(i);
                        }
                        return false;
                    }
                    @Override
                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                    }
                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                    }
                });
            }
//
            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {

            }
        });
    
}
}
