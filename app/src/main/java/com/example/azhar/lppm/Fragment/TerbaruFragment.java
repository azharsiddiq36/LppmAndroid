package com.example.azhar.lppm.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.azhar.lppm.Adapter.FgdAdapter;
import com.example.azhar.lppm.Adapter.PenelitianAdapter;
import com.example.azhar.lppm.Adapter.PengabdianAdapter;
import com.example.azhar.lppm.Adapter.PerjalananAdapter;
import com.example.azhar.lppm.Controller.SessionManager;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
    PenelitianAdapter adapter;
    PengabdianAdapter pengabdianAdapter;
    FgdAdapter fgdAdapter;
    PerjalananAdapter perjalananAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    ArrayList<Penelitian> penelitians = new ArrayList<>();
    ArrayList<Pengabdian> pengabdians = new ArrayList<>();
    ArrayList<Fgd> fgds = new ArrayList<>();
    ArrayList<Perjalanan> perjalanans = new ArrayList<>();
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terbaru, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_bar,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem !=null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView!=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    Log.i("onQueryTextSubmit", query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (getActivity().getIntent().getStringExtra("layout").equals("penelitian")){
                        adapter.getFilter().filter(newText);
                    }
                    else if (getActivity().getIntent().getStringExtra("layout").equals("pengabdian")){
                        pengabdianAdapter.getFilter().filter(newText);
                    }

                    else if (getActivity().getIntent().getStringExtra("layout").equals("fgd")){
                        fgdAdapter.getFilter().filter(newText);
                    }
                    else {
                        perjalananAdapter.getFilter().filter(newText);
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu,menuInflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        checkLayout(akses,getActivity().getIntent().getStringExtra("layout"),
                map.get(sessionManager.KEY_NIP),
                nip);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

    }


    private void checkLayout(String akses, String layout, String s,String nip) {
        if (akses.toLowerCase().equals("dosen")) {
            if (layout.toLowerCase().equals("penelitian")) {
                listpenelitian(akses, s, getActivity().getIntent().getStringExtra("tahun"));
            } else if (layout.toLowerCase().equals("fgd")) {
                listFgd(akses, s, getActivity().getIntent().getStringExtra("tahun"));
            } else if (layout.toLowerCase().equals("perjalanan")) {
                listPerjalanan(akses, s, getActivity().getIntent().getStringExtra("tahun"));
            } else {
                listpengabdian(akses, s, getActivity().getIntent().getStringExtra("tahun"));
            }
        }
    }

    private void listFgd(final String akses, String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelFgd> getData = lppmInterface.getDataFgd(""+s,tahun);
        getData.enqueue(new Callback<ModelFgd>() {
            @Override
            public void onResponse(Call<ModelFgd> call, Response<ModelFgd> response) {
                progressDialog.hide();
                fgdAdapter = null;
                fgds = (ArrayList<Fgd>) response.body().getData();
                Collections.sort(fgds, new Comparator<Fgd>() {
                    @Override
                    public int compare(Fgd lhs, Fgd rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getFgdId() > rhs.getFgdId() ? -1 : (lhs.getFgdId() < rhs.getFgdId() ) ? 1 : 0;
                    }
                });
                fgdAdapter = new FgdAdapter(getActivity(), fgds,akses,tahun);
                rvHistoty.setAdapter(fgdAdapter);
                fgdAdapter.notifyDataSetChanged();
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
//                            Toast.makeText(getContext(), "ini adalah "+fgds.get(index).getFgdId(), Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getContext(),DetailFgdActivity.class);
//                            i.putExtra("posisi",fgds.get(index).getFgdId().toString());
//                            i.putExtra("jenis","fgd");
//                            i.putExtra("moderator",fgds.get(position).getFgdModNam());
//                            i.putExtra("jenis",fgds.get(position).getFgdJen());
//                            i.putExtra("judul",fgds.get(position).getFgdJud());
//                            i.putExtra("narasumber",fgds.get(position).getFgdNarNam());
//                            //i.putExtra("peserta",fgds.get(position).getFgdPesNam());
//                            i.putExtra("lokasi",fgds.get(position).getFgdLok());
//                            i.putExtra("tglAcc",fgds.get(position).getFgdTglAcc());
//                            i.putExtra("tglPel",fgds.get(position).getFgdTglPel());
//                            i.putExtra("tglSur",fgds.get(position).getFgdTglSurat());
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
            public void onFailure(Call<ModelFgd> call, Throwable t) {

            }
        });

    }

    private void listpengabdian(final String akses, String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelPengabdian> getData = lppmInterface.getDataPengabdian("" + s,tahun);
        getData.enqueue(new Callback<ModelPengabdian>() {
            @Override
            public void onResponse(Call<ModelPengabdian> call, Response<ModelPengabdian> response) {
                progressDialog.hide();
                pengabdianAdapter = null;
                pengabdians = (ArrayList<Pengabdian>) response.body().getData();
                Collections.sort(pengabdians, new Comparator<Pengabdian>() {
                    @Override
                    public int compare(Pengabdian lhs, Pengabdian rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getSipId() > rhs.getSipId() ? -1 : (lhs.getSipId() < rhs.getSipId() ) ? 1 : 0;
                    }
                });
                pengabdianAdapter = new PengabdianAdapter(getActivity(), pengabdians,akses,tahun);
                rvHistoty.setAdapter(pengabdianAdapter);
                pengabdianAdapter.notifyDataSetChanged();
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
//                            Toast.makeText(getContext(), "ini adalah "+pengabdians.get(position).getSipId(), Toast.LENGTH_SHORT).show();
////                            Intent i = new Intent(getContext(),DetailsLetter.class);
//
//                            Intent i = new Intent(getContext(),DetailPengabdianActivity.class);
//                            i.putExtra("posisi",pengabdians.get(position).getSipId());
//                            i.putExtra("jenis","surat pengabdian");
//                          //  i.putExtra("anggota",pengabdians.get(position).getSipAngNam());
//                            i.putExtra("instansi",pengabdians.get(position).getSipInsTuj());
//                            i.putExtra("judul",pengabdians.get(position).getSipJud());
//                            i.putExtra("kabupaten",pengabdians.get(position).getSipKabKot());
//                            i.putExtra("ketua",pengabdians.get(position).getSipKetNam());
//                            i.putExtra("lokasi",pengabdians.get(position).getSipLok());
//                            i.putExtra("tglAcc",pengabdians.get(position).getSipTglAcc());
//                            i.putExtra("tglKeg",pengabdians.get(position).getSipTglKeg());
//                            i.putExtra("tglSur",pengabdians.get(position).getSipTglSratKel());
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
            public void onFailure(Call<ModelPengabdian> call, Throwable t) {

            }
        });

    }
    private void listPerjalanan(final String akses, final String s,final String tahun) {

        lppmInterface = CombineApi.getApiService();
        Call<ModelPerjalanan> getData = lppmInterface.getDataPerjalanan(""+s,tahun);
        getData.enqueue(new Callback<ModelPerjalanan>() {
            @Override
            public void onResponse(Call<ModelPerjalanan> call, Response<ModelPerjalanan> response) {
                progressDialog.hide();
                perjalananAdapter = null;
                perjalanans = (ArrayList<Perjalanan>) response.body().getData();
                Collections.sort(perjalanans, new Comparator<Perjalanan>() {
                    @Override
                    public int compare(Perjalanan lhs, Perjalanan rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getStpId() > rhs.getStpId() ? -1 : (lhs.getStpId() < rhs.getStpId() ) ? 1 : 0;
                    }
                });
                perjalananAdapter = new PerjalananAdapter(getActivity(), perjalanans,akses,tahun);
                rvHistoty.setAdapter(perjalananAdapter);
                perjalananAdapter.notifyDataSetChanged();
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
//                           // i.putExtra("anggota",perjalanans.get(position).getStpAngNam());
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
            }

            @Override
            public void onFailure(Call<ModelPerjalanan> call, Throwable t) {

            }
        });

    }


    private void listpenelitian(final String akses,final String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelRespon> getData = lppmInterface.getDataPenelitian("" + s,tahun);
        getData.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {

                progressDialog.hide();
                adapter = null;
                penelitians = (ArrayList<Penelitian>) response.body().getData();
                Collections.sort(penelitians, new Comparator<Penelitian>() {
                    @Override
                    public int compare(Penelitian lhs, Penelitian rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getSitId() > rhs.getSitId() ? -1 : (lhs.getSitId() < rhs.getSitId() ) ? 1 : 0;
                    }
                });
                adapter = new PenelitianAdapter(getActivity(), penelitians,akses,tahun);
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
//                            i.putExtra("nama",penelitians.get(position).getSitKetNam());
//                            i.putExtra("nip",penelitians.get(position).getSitId());
//
//                            i.putExtra("anggota",penelitians.get(position).getSitAngNam().toArray());
//                            i.putExtra("lokasi",penelitians.get(position).getSitLok());
//                            i.putExtra("tanggal",penelitians.get(position).getSitTglKeg());
//                            i.putExtra("judul",penelitians.get(position).getSitJud());
//                            i.putExtra("status",penelitians.get(position).getSitTglAcc());
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

            }
        });
    }


}

