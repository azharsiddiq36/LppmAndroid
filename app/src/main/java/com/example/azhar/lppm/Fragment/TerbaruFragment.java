package com.example.azhar.lppm.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Adapter.FgdAdapter;
import com.example.azhar.lppm.Adapter.PenelitianAdapter;
import com.example.azhar.lppm.Adapter.PengabdianAdapter;
import com.example.azhar.lppm.Adapter.PerjalananAdapter;
import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.DetailFgdActivity;
import com.example.azhar.lppm.DetailPenelitianActivity;
import com.example.azhar.lppm.DetailPengabdianActivity;
import com.example.azhar.lppm.DetailPerjalananActivity;
import com.example.azhar.lppm.Model.Fgd;
import com.example.azhar.lppm.Model.ModelFgd;
import com.example.azhar.lppm.Model.ModelPengabdian;
import com.example.azhar.lppm.Model.ModelPerjalanan;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.Model.Pengabdian;
import com.example.azhar.lppm.Model.Perjalanan;
import com.example.azhar.lppm.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TerbaruFragment extends Fragment {
    @BindView(R.id.rv_History)
    RecyclerView rvHistoty;
    private String pilih[] = {"Surat Penelitian", "Surat Pengabdian", "Surat FGD"};
    SessionManager sessionManager;
    LppmInterface lppmInterface;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    List<Penelitian> penelitians = new ArrayList<>();
    List<Pengabdian> pengabdians = new ArrayList<>();
    List<Fgd> fgds = new ArrayList<>();
    List<Perjalanan> perjalanans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terbaru, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        final HashMap<String, String> map = sessionManager.getDetailsLoggin();
        progressDialog = new ProgressDialog(getActivity());
        String nip = getActivity().getIntent().getStringExtra("nip");

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvHistoty.setLayoutManager(layoutManager);
        String akses = String.valueOf(map.get(sessionManager.KEY_HAK_AKSES));
        checkLayout(akses,getActivity().getIntent().getStringExtra("layout"),map.get(sessionManager.KEY_NIP),nip);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
    }

    private void checkLayout(String akses, String layout, String s,String nip) {
        if(akses.toLowerCase().equals("dosen")){
            if (layout.toLowerCase().equals("penelitian")) {
                listpenelitian(akses,s);
            }
            else if(layout.toLowerCase().equals("fgd")){
                listFgd(s);
            }
            else if (layout.toLowerCase().equals("perjalanan")){
                listPerjalanan(s);
            }
            else {
                listpengabdian(s);
            }
        }
        else if (akses.toLowerCase().equals("kepala")){
            if (layout.toLowerCase().equals("penelitian")) {
                diposisiPenelitian("kepala",nip);
            }
            else if(layout.toLowerCase().equals("fgd")){
                disposisiFgd(s);
            }
            else if (layout.toLowerCase().equals("perjalanan")){
                disposisiPerjalanan(s);
            }
            else {
                disposisiPengabdian(s);
            }
        }
        else {
            Toast.makeText(getContext(), "Maaf Anda tidak dibolehkan mengakses ini", Toast.LENGTH_SHORT).show();
        }
    }

    private void disposisiPerjalanan(String s) {

    }
    private void disposisiPengabdian(String s) {

    }
    private void disposisiFgd(String s) {

    }

    private void diposisiPenelitian(final String s, final String nip) {
        lppmInterface = CombineApi.getApiService();
        Toast.makeText(getActivity(), "nip" +nip, Toast.LENGTH_SHORT).show();
        Call<ModelRespon> getData = lppmInterface.getDataPenelitian("" + nip);
        getData.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                adapter = null;
                penelitians = response.body().getData();
                adapter = new PenelitianAdapter(getActivity(), penelitians,s);
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                Log.d("penelitians",""+penelitians.toString());
//                rvHistoty.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//                        public boolean onSingleTapUp(MotionEvent e) {
//                            return true;
//                        }
//                    });
//
//                    @Override
//                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                        View child = rv.findChildViewUnder(e.getX(), e.getY());
//                        if (gestureDetector.onTouchEvent(e)) {
//                            int position = rv.getChildAdapterPosition(child);
//                            int index = position-1;
//                            Toast.makeText(getContext(), "ini adalah " + position, Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getContext(), DetailPenelitianActivity.class);
//                            i.putExtra("posisi",penelitians.get(position).getSitId());
//                            i.putExtra("judul",penelitians.get(position).getSitJud());
//                            i.putExtra("lokasi",penelitians.get(position).getSitLok());
//                            i.putExtra("instansi",penelitians.get(position).getSitInsTuj());
//                            i.putExtra("noktp",penelitians.get(position).getSitId());
//                            i.putExtra("nowa",penelitians.get(position).getSitId());
//                            i.putExtra("tanggal",penelitians.get(position).getSitId());
//                            getActivity().startActivity(i);
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                    }
//
//                    @Override
//                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//                    }
//                });
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Log.d("kepala",""+t.toString());
            }
        });
    }


    private void listFgd(String s) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelFgd> getData = lppmInterface.getDataFgd(""+s);

        getData.enqueue(new Callback<ModelFgd>() {
            @Override
            public void onResponse(Call<ModelFgd> call, Response<ModelFgd> response) {
                progressDialog.hide();
                adapter =null;
                fgds = response.body().getData();
                adapter = new FgdAdapter(getActivity(),fgds);

                rvHistoty.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                rvHistoty.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
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
                            Toast.makeText(getContext(), "ini adalah "+fgds.get(index).getFgdId(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getContext(),DetailFgdActivity.class);
                            i.putExtra("posisi",fgds.get(index).getFgdId().toString());
                            i.putExtra("jenis","fgd");
                            i.putExtra("moderator",fgds.get(position).getFgdModNam());
                            i.putExtra("jenis",fgds.get(position).getFgdJen());
                            i.putExtra("judul",fgds.get(position).getFgdJud());
                            i.putExtra("narasumber",fgds.get(position).getFgdNarNam());
                            //i.putExtra("peserta",fgds.get(position).getFgdPesNam());
                            i.putExtra("lokasi",fgds.get(position).getFgdLok());
                            i.putExtra("tglAcc",fgds.get(position).getFgdTglAcc());
                            i.putExtra("tglPel",fgds.get(position).getFgdTglPel());
                            i.putExtra("tglSur",fgds.get(position).getFgdTglSurat());
                            getActivity().startActivity(i);
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

            @Override
            public void onFailure(Call<ModelFgd> call, Throwable t) {

            }
        });

    }

    private void listpengabdian(String s) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelPengabdian> getData = lppmInterface.getDataPengabdian(""+s);
        getData.enqueue(new Callback<ModelPengabdian>() {
            @Override
            public void onResponse(Call<ModelPengabdian> call, Response<ModelPengabdian> response) {
                progressDialog.hide();
                adapter = null;
                pengabdians = response.body().getData();
                adapter = new PengabdianAdapter(getActivity(),pengabdians);
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rvHistoty.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
                        public boolean onSingleTapUp(MotionEvent e){
                            return true;
                        }
                    });
                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(),e.getY());
                        if (gestureDetector.onTouchEvent(e)){
                            int position = rv.getChildAdapterPosition(child);
                            Toast.makeText(getContext(), "ini adalah "+pengabdians.get(position).getSipId(), Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getContext(),DetailsLetter.class);

                            Intent i = new Intent(getContext(),DetailPengabdianActivity.class);
                            i.putExtra("posisi",pengabdians.get(position).getSipId());
                            i.putExtra("jenis","surat pengabdian");
                          //  i.putExtra("anggota",pengabdians.get(position).getSipAngNam());
                            i.putExtra("instansi",pengabdians.get(position).getSipInsTuj());
                            i.putExtra("judul",pengabdians.get(position).getSipJud());
                            i.putExtra("kabupaten",pengabdians.get(position).getSipKabKot());
                            i.putExtra("ketua",pengabdians.get(position).getSipKetNam());
                            i.putExtra("lokasi",pengabdians.get(position).getSipLok());
                            i.putExtra("tglAcc",pengabdians.get(position).getSipTglAcc());
                            i.putExtra("tglKeg",pengabdians.get(position).getSipTglKeg());
                            i.putExtra("tglSur",pengabdians.get(position).getSipTglSratKel());
                            getActivity().startActivity(i);
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

            @Override
            public void onFailure(Call<ModelPengabdian> call, Throwable t) {

            }
        });

    }
    private void listPerjalanan(final String s) {

        lppmInterface = CombineApi.getApiService();
        Call<ModelPerjalanan> getData = lppmInterface.getDataPerjalanan(""+s);
        getData.enqueue(new Callback<ModelPerjalanan>() {
            @Override
            public void onResponse(Call<ModelPerjalanan> call, Response<ModelPerjalanan> response) {
                Toast.makeText(getContext(), "Kambing", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                adapter = null;
                perjalanans = response.body().getData();
                adapter = new PerjalananAdapter(getActivity(), perjalanans );
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ModelPerjalanan> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Mbing", Toast.LENGTH_SHORT).show();
            }
        });
//        getData.enqueue(new Callback<ModelPerjalanan>() {
//            @Override
//            public void onResponse(Call<ModelPerjalanan> call, Response<ModelPerjalanan> response) {
//                progressDialog.hide();
//                adapter =null;
//                perjalanans = response.body().getData();
//                adapter = new PerjalananAdapter(getActivity(),perjalanans);
//                rvHistoty.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                rvHistoty.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                    GestureDetector gestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
//                        public boolean onSingleTapUp(MotionEvent e){
//                            return true;
//                        }
//                    });
//                    @Override
//                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                        View child = rv.findChildViewUnder(e.getX(),e.getY());
//                        if (gestureDetector.onTouchEvent(e)){
//                            int position = rv.getChildAdapterPosition(child);
//                            int index = position-1;
//                            Toast.makeText(getContext(), "ini adalah "+perjalanans.get(index).getStpId(), Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getContext(),DetailPerjalananActivity.class);
//                            i.putExtra("posisi",perjalanans.get(index).getStpId());
//                            i.putExtra("jenis","perjalanan");
//                            i.putExtra("ketua",perjalanans.get(position).getStpKetNam());
//                            i.putExtra("anggota",perjalanans.get(position).getStpAngNam());
//                            i.putExtra("golongan",perjalanans.get(position).getStpGol());
//                            i.putExtra("jenis",perjalanans.get(position).getStpJen());
//                            i.putExtra("judul",perjalanans.get(position).getStpJud());
//                            i.putExtra("kluster",perjalanans.get(position).getStpKlu());
//                            i.putExtra("lokasi",perjalanans.get(position).getStpLok());
//                            i.putExtra("nomor",perjalanans.get(position).getStpNom());
//                            i.putExtra("tglAcc",perjalanans.get(position).getStpTglAcc());
//                            getActivity().startActivity(i);
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                    }
//
//                    @Override
//                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ModelPerjalanan> call, Throwable t) {
//
//            }
//        });

    }


    private void listpenelitian(final String akses,final String s) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelRespon> getData = lppmInterface.getDataPenelitian("" + s);
        getData.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                adapter = null;
                penelitians = response.body().getData();
                adapter = new PenelitianAdapter(getActivity(), penelitians,akses);
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("penelitians",""+penelitians.toString());
                rvHistoty.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }
                    });

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        if (gestureDetector.onTouchEvent(e)) {
                            int position = rv.getChildAdapterPosition(child);
                            int index = position-1;
                            Toast.makeText(getContext(), "ini adalah " + position, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getContext(), DetailPenelitianActivity.class);
                            i.putExtra("posisi",penelitians.get(position).getSitId());
                            i.putExtra("judul",penelitians.get(position).getSitJud());
                            i.putExtra("lokasi",penelitians.get(position).getSitLok());
                            i.putExtra("instansi",penelitians.get(position).getSitInsTuj());
                            i.putExtra("noktp",penelitians.get(position).getSitId());
                            i.putExtra("nowa",penelitians.get(position).getSitId());
                            i.putExtra("tanggal",penelitians.get(position).getSitId());
                            getActivity().startActivity(i);
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

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {

            }
        });
    }
}

