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
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BelumSetujuFragment extends Fragment {
    @BindView(R.id.rv_History)
    RecyclerView rvHistoty;
    private String pilih[] = {"Surat Penelitian", "Surat Pengabdian", "Surat FGD"};
    SessionManager sessionManager;
    LppmInterface lppmInterface;
    PenelitianAdapter adapter;
    PerjalananAdapter perjalananAdapter;
    PengabdianAdapter pengabdianAdapter;
    FgdAdapter fgdAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    ArrayList<Penelitian> penelitians = new ArrayList<>();
    List<Pengabdian> pengabdians = new ArrayList<>();
    List<Fgd> fgds = new ArrayList<>();
    List<Perjalanan> perjalanans = new ArrayList<>();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
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
                    adapter.getFilter().filter(newText);
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
        checkLayout(akses,getActivity().getIntent().getStringExtra("layout"),map.get(sessionManager.KEY_NIP),nip);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

    }


    private void checkLayout(String akses, String layout, String s,String nip) {
        if(akses.toLowerCase().equals("dosen")){
            if (layout.toLowerCase().equals("penelitian")) {
                listpenelitian(akses,s,getActivity().getIntent().getStringExtra("tahun"));
            }
            else if(layout.toLowerCase().equals("fgd")){
                listFgd(akses,s,getActivity().getIntent().getStringExtra("tahun"));
            }
            else if (layout.toLowerCase().equals("perjalanan")){
                listPerjalanan(akses,s,getActivity().getIntent().getStringExtra("tahun"));
            }
            else {
                listpengabdian(akses,s,getActivity().getIntent().getStringExtra("tahun"));
            }
        }
    }

    private void listpengabdian(final String akses, String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelPengabdian> getData = lppmInterface.getDataPengabdian("" + s,tahun);
        getData.enqueue(new Callback<ModelPengabdian>() {
            @Override
            public void onResponse(Call<ModelPengabdian> call, Response<ModelPengabdian> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                pengabdianAdapter = null;
                pengabdians = (ArrayList<Pengabdian>) response.body().getData();
                ArrayList<Pengabdian> listsetuju;
                listsetuju = new ArrayList<>();
                int a = 0;
                for(Object o : pengabdians){
                    if(pengabdians.get(a).getSipTglAcc().equals("")){
                        listsetuju.add(pengabdians.get(a));
                    }
                    a++;
                }
                pengabdianAdapter = new PengabdianAdapter(getActivity(), listsetuju,akses,tahun);
                pengabdianAdapter.getFilter().filter("");
                rvHistoty.setAdapter(pengabdianAdapter);
                pengabdianAdapter.notifyDataSetChanged();

                Log.d("penelitians",""+pengabdians.toString());
            }

            @Override
            public void onFailure(Call<ModelPengabdian> call, Throwable t) {

            }
        });
    }

    private void listPerjalanan(final String akses, String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelPerjalanan> getData = lppmInterface.getDataPerjalanan("" + s,tahun);
        getData.enqueue(new Callback<ModelPerjalanan>() {
            @Override
            public void onResponse(Call<ModelPerjalanan> call, Response<ModelPerjalanan> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                perjalananAdapter = null;
                perjalanans = (ArrayList<Perjalanan>) response.body().getData();
                ArrayList<Perjalanan> listsetuju;
                listsetuju = new ArrayList<>();
                int a = 0;
                for(Object o : perjalanans){
                    if(perjalanans.get(a).getStpTglAcc().equals("")){
                        listsetuju.add(perjalanans.get(a));
                    }
                    a++;
                }
                perjalananAdapter = new PerjalananAdapter(getActivity(), listsetuju,akses,tahun);
                perjalananAdapter.getFilter().filter("");
                rvHistoty.setAdapter(perjalananAdapter);
                perjalananAdapter.notifyDataSetChanged();
                Log.d("penelitians",""+perjalanans.toString());
            }

            @Override
            public void onFailure(Call<ModelPerjalanan> call, Throwable t) {

            }
        });
    }

    private void listFgd(final String akses, String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelFgd> getData = lppmInterface.getDataFgd("" + s,tahun);
        getData.enqueue(new Callback<ModelFgd>() {
            @Override
            public void onResponse(Call<ModelFgd> call, Response<ModelFgd> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                fgdAdapter = null;
                fgds = (ArrayList<Fgd>) response.body().getData();
                ArrayList<Fgd> listsetuju;
                listsetuju = new ArrayList<>();
                int a = 0;
                for(Object o : fgds){
                    if(fgds.get(a).getFgdTglAcc().equals("")){
                        listsetuju.add(fgds.get(a));
                    }
                    a++;
                }
                fgdAdapter = new FgdAdapter(getActivity(), listsetuju,akses,tahun);
                fgdAdapter.getFilter().filter("");
                rvHistoty.setAdapter(fgdAdapter);
                fgdAdapter.notifyDataSetChanged();

                Log.d("penelitians",""+fgds.toString());
            }

            @Override
            public void onFailure(Call<ModelFgd> call, Throwable t) {

            }
        });
    }

    private void listpenelitian(final String akses,final String s,final String tahun) {
        lppmInterface = CombineApi.getApiService();
        Call<ModelRespon> getData = lppmInterface.getDataPenelitian("" + s,tahun);
        getData.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                Log.d("kepala",""+response);
                progressDialog.hide();
                adapter = null;
                penelitians = (ArrayList<Penelitian>) response.body().getData();
                ArrayList<Penelitian> listsetuju;
                listsetuju = new ArrayList<>();
                int a = 0;
                for(Object o : penelitians){
                    if(penelitians.get(a).getSitTglAcc().equals("")){
                        listsetuju.add(penelitians.get(a));
                    }
                    a++;
                }
                adapter = new PenelitianAdapter(getActivity(), listsetuju,akses,tahun);
                adapter.getFilter().filter("");
                rvHistoty.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                Log.d("penelitians",""+penelitians.toString());
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {

            }
        });
    }
}
