package com.example.azhar.lppm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.Activity.FormPengabdianActivity;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Pengabdian;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PengabdianAdapter extends RecyclerView.Adapter<PengabdianAdapter.ViewHolder> implements Filterable {
    private ArrayList<Pengabdian> rvData;
    Context context;
    Dialog myDialog;
    SessionManager sessionManager;
    String akses;
    String tahun;
    private ArrayList<Pengabdian> rvDataList;
    private List<ModelRespon> data;
    public PengabdianAdapter(Context context, ArrayList<Pengabdian> inputData, String akses,String tahun){
        this.context = context;
        this.tahun = tahun;
        rvData=inputData;
        this.akses = akses;
        rvDataList = new ArrayList<>(rvData);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvLetter,tvTanggal,tvTanggalAcc,tvPengaju,coba;
        public CardView cvLetter;
        public ImageView ivAccept,tvAccept;
        public ViewHolder(View v){
            super(v);
            tvAccept = (ImageView)v.findViewById(R.id.tvAccept);
            ivAccept = (ImageView)v.findViewById(R.id.ivAccept);
            tvTanggalAcc = (TextView) v.findViewById(R.id.tvStatus);
            cvLetter=(CardView)v.findViewById(R.id.cvLetter);
            tvTanggal = (TextView)v.findViewById(R.id.tvTanggal);
            tvLetter = (TextView) v.findViewById(R.id.tvLetter);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_letter, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int poSipion) {
            final Pengabdian pengabdian = rvData.get(poSipion);
            if (pengabdian.getSipTglAcc().equals("")){
                holder.tvTanggalAcc.setText("Status : Belum di Setujui");
            }
            else {
                holder.tvTanggalAcc.setText("Disetujui Pada : "+pengabdian.getSipTglAcc());
            }
            holder.tvLetter.setText(pengabdian.getSipJud());
            holder.tvTanggal.setText(pengabdian.getSipTglKeg());
            Log.d("kambingdata",""+rvData.get(poSipion));
            holder.cvLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.detail_pengabdian, null);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                TextView tvJud,tvNama,tvNip,tvTglKeg,tvLok,tvTglPeng,tvStat,tvIns;
                LinearLayout lyDinamic;
                Button btnEdit;
                sessionManager = new SessionManager(context);
                HashMap<String,String> map = sessionManager.getDetailsLoggin();

                lyDinamic = (LinearLayout)popupView.findViewById(R.id.lyDinamic);
                btnEdit = (Button)popupView.findViewById(R.id.btnEdit);
                tvJud = (TextView)popupView.findViewById(R.id.tvJud);
                tvLok = (TextView)popupView.findViewById(R.id.tvLok);
                tvTglKeg = (TextView)popupView.findViewById(R.id.tvTglKeg);
                tvTglPeng = (TextView)popupView.findViewById(R.id.tvTglPeng);
                tvStat = (TextView)popupView.findViewById(R.id.tvStat);
                tvIns = (TextView)popupView.findViewById(R.id.tvIns);
                tvNip = (TextView)popupView.findViewById(R.id.tvNip);
                tvNama = (TextView)popupView.findViewById(R.id.tvNama);
                tvNama.setText(""+map.get(sessionManager.KEY_NAMA));
                tvNip.setText(""+map.get(sessionManager.KEY_NIP));
                tvJud.setText(pengabdian.getSipJud());
                tvIns.setText(pengabdian.getSipInsTuj());
                tvLok.setText(pengabdian.getSipProv()+","+pengabdian.getSipKot()+","
                        +pengabdian.getSipKec()+","+pengabdian.getSipKel());
                if (pengabdian.getSipTglAcc().equals("")){
                    tvStat.setText("Belum Disetujui");
                }
                else {
                    tvStat.setText("Disetujui Pada : "+pengabdian.getSipTglAcc());
                }
                if (pengabdian.getSipAngNam() != null) {
                    for (int a = 0; a < pengabdian.getSipAngNam().size(); a++) {
                        tambahAnggota(pengabdian.getSipAngNam().get(a),lyDinamic);
                    }
                }
                tvTglKeg.setText(pengabdian.getSipTglKeg());
                tvTglPeng.setText(""+pengabdian.getSipTglSratKel());
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setHeight(height-200);
                popupView.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, FormPengabdianActivity.class);
                        i.putExtra("edit",String.valueOf(pengabdian.getSipId()));
                        String anggota = "-";
                        if (pengabdian.getSipAngNam() != null) {
                            for (int a = 0; a < pengabdian.getSipAngNam().size(); a++) {
                                if (anggota.equals("-")) {
                                    anggota = pengabdian.getSipAngNam().get(a);
                                } else {
                                    anggota = pengabdian.getSipAngNam().get(a) + "-" + anggota;
                                }
                            }
                            i.putExtra("anggota", anggota);

                        }
                        else {
                            i.putExtra("anggota", "tidak memiliki anggota");
                        }
                        i.putExtra("tanggal", pengabdian.getSipTglKeg());
                        i.putExtra("judul", pengabdian.getSipJud());
                        i.putExtra("status", pengabdian.getSipTglAcc());
                        i.putExtra("prov",pengabdian.getSipProv());
                        i.putExtra("kot",pengabdian.getSipKot());
                        i.putExtra("kec",pengabdian.getSipKec());
                        i.putExtra("des",pengabdian.getSipKel());
                        i.putExtra("instansi",pengabdian.getSipInsTuj());
                        i.putExtra("tahun",tahun);
                        Log.d("kerok0",tahun);
                        context.startActivity(i);
                    }
                });

            }
        });

//            holder.cvLetter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                Display display = wm.getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                int width = size.x;
//                int height = size.y;
//                LayoutInflater inflater = (LayoutInflater)
//                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View popupView = inflater.inflate(R.layout.detail_pengabdian, null);
//                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
//                TextView tvJud,tvKtp,tvNama,tvNip,tvTglKeg,tvLok,tvNoWa,tvTglPeng,tvStat,tvIns;
//                LinearLayout lyDinamic;
//                Button btnEdit;
//                sessionManager = new SessionManager(context);
//                HashMap<String,String> map = sessionManager.getDetailsLoggin();
//
//                lyDinamic = (LinearLayout)popupView.findViewById(R.id.lyDinamic);
//                btnEdit = (Button)popupView.findViewById(R.id.btnEdit);
//                tvJud = (TextView)popupView.findViewById(R.id.tvJud);
//                tvKtp = (TextView)popupView.findViewById(R.id.tvKtp);
//                tvNoWa = (TextView)popupView.findViewById(R.id.tvWa);
//                tvLok = (TextView)popupView.findViewById(R.id.tvLok);
//                tvTglKeg = (TextView)popupView.findViewById(R.id.tvTglKeg);
//                tvTglPeng = (TextView)popupView.findViewById(R.id.tvTglPeng);
//                tvStat = (TextView)popupView.findViewById(R.id.tvStat);
//                tvIns = (TextView)popupView.findViewById(R.id.tvIns);
//                tvNip = (TextView)popupView.findViewById(R.id.tvNip);
//                tvNama = (TextView)popupView.findViewById(R.id.tvNama);
//                tvNama.setText(""+map.get(sessionManager.KEY_NAMA));
//                tvNip.setText(""+map.get(sessionManager.KEY_NIP));
//                tvJud.setText(Pengabdian.getSipJud());
//                tvIns.setText(Pengabdian.getSipInsTuj());
//                tvLok.setText(Pengabdian.getSipProv()+","+Pengabdian.getSipKot()+","
//                        +Pengabdian.getSipKec()+","+Pengabdian.getSipKel());
//                if (Pengabdian.getSipTglAcc().equals("")){
//                    tvStat.setText("Belum Disetujui");
//                }
//                else {
//                    tvStat.setText("Disetujui Pada : "+Pengabdian.getSipTglAcc());
//                }
//                if (Pengabdian.getSipAngNam() != null) {
//                    for (int a = 0; a < Pengabdian.getSipAngNam().size(); a++) {
//                        tambahAnggota(Pengabdian.getSipAngNam().get(a),lyDinamic);
//                    }
//                }
//                tvTglKeg.setText(Pengabdian.getSipTglKeg());
//                tvTglPeng.setText(Pengabdian.getSipTglSratKel());
//                final PopupWindow popupWindow = new PopupWindow(popupView);
//                popupWindow.setWidth(width);
//                popupWindow.setHeight(height-200);
//                popupView.setFocusable(true);
//                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });
//                btnEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent i = new Intent(context, FormPengabdianActivity.class);
//                        i.putExtra("edit","ya");
//                        String anggota = "-";
//                        if (Pengabdian.getSipAngNam() != null) {
//                            for (int a = 0; a < Pengabdian.getSipAngNam().size(); a++) {
//                                if (anggota.equals("-")) {
//                                    anggota = Pengabdian.getSipAngNam().get(a);
//                                } else {
//                                    anggota = Pengabdian.getSipAngNam().get(a) + "-" + anggota;
//                                }
//                            }
//                            i.putExtra("anggota", anggota);
//
//                        }
//                        else {
//                            i.putExtra("anggota", "tidak memiliki anggota");
//                        }
//                        i.putExtra("tanggal", Pengabdian.getSipTglKeg());
//                        i.putExtra("judul", Pengabdian.getSipJud());
//                        i.putExtra("status", Pengabdian.getSipTglAcc());
//                        i.putExtra("prov",Pengabdian.getSipProv());
//                        i.putExtra("kot",Pengabdian.getSipKot());
//                        i.putExtra("kec",Pengabdian.getSipKec());
//                        i.putExtra("des",Pengabdian.getSipKel());
//                        i.putExtra("instansi",Pengabdian.getSipInsTuj());
//                        context.startActivity(i);
//                    }
//                });
//
//            }
//        });

    }


    private void tambahAnggota(String s,LinearLayout lyDinamic) {
        LayoutInflater layoutInflater  =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        Button remove = (Button)addView.findViewById(R.id.remove);
        remove.setVisibility(View.INVISIBLE);
        textView.setText(s);
        Log.d("nyoba = ",textView.getText().toString());
        lyDinamic.addView(addView);

    }
    @Override
    public int getItemCount() {
        return rvData.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pengabdian> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Pengabdian item:rvDataList ){
                    if (item.getSipJud().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getSipTglAcc().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getSipTglKeg().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if(item.getSipTglAcc().toLowerCase().equals("")){

                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rvData.clear();
            rvData.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

//    ImageViewbg = (ImageView) findViewById(R.id.gambar);
//        Picasso.with(this)
//                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + getIntent().getStringExtra("bg"))
//            .resize(200, 300)
//                .into(bg);
//}
}