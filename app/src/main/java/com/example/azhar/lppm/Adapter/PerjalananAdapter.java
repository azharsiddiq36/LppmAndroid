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
import com.example.azhar.lppm.Activity.FormPerjalananActivity;
import com.example.azhar.lppm.Model.ModelPerjalanan;
import com.example.azhar.lppm.Model.Perjalanan;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PerjalananAdapter extends RecyclerView.Adapter<PerjalananAdapter.ViewHolder> implements Filterable {
    private ArrayList<Perjalanan> rvData;
    Context context;
    Dialog myDialog;
    String akses;
    private ArrayList<Perjalanan> rvDataList;
    SessionManager sessionManager;
    String tahun;
    private List<ModelPerjalanan> data;
    public PerjalananAdapter(Context context, ArrayList<Perjalanan> inputData, String akses,String tahun){
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
            //    tvPengaju = (TextView)v.findViewById(R.id.tvPengaju);
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
    public void onBindViewHolder(final ViewHolder holder, int poStpion) {
        final Perjalanan Perjalanan = rvData.get(poStpion);
        if (Perjalanan.getStpTglAcc().equals("")){
            holder.tvTanggalAcc.setText("Status : Belum di Setujui");
//                holder.cvLetter.setVisibility(View.INVISIBLE);
        }
        else {
            holder.tvTanggalAcc.setText("Disetujui Pada : "+Perjalanan.getStpTglAcc());
        }
        holder.tvLetter.setText(Perjalanan.getStpJud());
        holder.tvTanggal.setText(Perjalanan.getStpTglKeg());
        Log.d("kambingdata",""+rvData.get(poStpion));



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
                final View popupView = inflater.inflate(R.layout.detail_surattugas, null);
                ImageView close = (ImageView) popupView.findViewById(R.id.btnClose);
                TextView tvJud, tvJab, tvGol,
                        tvNama, tvTglKeg,
                        tvLok, tvTglPeng,
                        tvKlu, tvJen, tvNo,
                        tvTglKlu, tvStat;
                LinearLayout lyDinamic;
                Button btnEdit;
                sessionManager = new SessionManager(context);
                HashMap<String, String> map = sessionManager.getDetailsLoggin();
                lyDinamic = (LinearLayout) popupView.findViewById(R.id.lyDinamic);
                btnEdit = (Button) popupView.findViewById(R.id.btnEdit);
                tvJud = (TextView) popupView.findViewById(R.id.tvJud);
                tvJab = (TextView) popupView.findViewById(R.id.tvJab);
                tvGol = (TextView) popupView.findViewById(R.id.tvGol);
                tvKlu = (TextView) popupView.findViewById(R.id.tvKlu);
                tvTglKlu = (TextView) popupView.findViewById(R.id.tvTglKlu);
                tvNo = (TextView) popupView.findViewById(R.id.tvNo);
                tvJen = (TextView) popupView.findViewById(R.id.tvJen);
                tvLok = (TextView) popupView.findViewById(R.id.tvLok);
                tvTglKeg = (TextView) popupView.findViewById(R.id.tvTglKeg);
                tvTglPeng = (TextView) popupView.findViewById(R.id.tvTglPeng);
                tvStat = (TextView) popupView.findViewById(R.id.tvStat);

                tvNama = (TextView) popupView.findViewById(R.id.tvNama);
                tvNama.setText("" + Perjalanan.getStpKetNam());
                tvJud.setText(Perjalanan.getStpJud());
                tvGol.setText(Perjalanan.getStpKetPang());
                tvJab.setText(Perjalanan.getStpKetJab());
                tvKlu.setText(Perjalanan.getStpKlu());
                tvTglKlu.setText(Perjalanan.getStpTglKlu());
                tvJen.setText(Perjalanan.getStpJen());
                tvNo.setText(Perjalanan.getStpNom());
                tvLok.setText(Perjalanan.getStpProv() + "," + Perjalanan.getStpKot() + ","
                        + Perjalanan.getStpKec() + "," + Perjalanan.getStpKel());
                if (Perjalanan.getStpTglAcc().equals("")) {
                    tvStat.setText("Belum Disetujui");
                } else {
                    tvStat.setText("Disetujui Pada : " + Perjalanan.getStpTglAcc());
                }
                if (Perjalanan.getStpAngNam() != null) {
                    for (int a = 0; a < Perjalanan.getStpAngNam().size(); a++) {
                        tambahAnggota(Perjalanan.getStpAngNam().get(a),
                                Perjalanan.getStpAngJab().get(a),
                                Perjalanan.getStpAngPang().get(a), lyDinamic);
                    }
                }
                tvTglKeg.setText(Perjalanan.getStpTglKeg());
                tvTglPeng.setText(Perjalanan.getStpTglSratKel());
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setHeight(height - 200);
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
                        Intent i = new Intent(context, FormPerjalananActivity.class);
                        i.putExtra("edit",String.valueOf(Perjalanan.getStpId()));
                        String anggota = "-";
                        String jab = "-";
                        String gol = "-";
                        if (Perjalanan.getStpAngNam() != null) {
                            for (int a = 0; a < Perjalanan.getStpAngNam().size(); a++) {
                                if (anggota.equals("-")) {
                                    anggota = Perjalanan.getStpAngNam().get(a);
                                    jab = Perjalanan.getStpAngJab().get(a);
                                    gol = Perjalanan.getStpAngPang().get(a);
                                } else {
                                    anggota = Perjalanan.getStpAngNam().get(a) + "-" + anggota;
                                    jab = Perjalanan.getStpAngJab().get(a)+ "-" + anggota;
                                    gol = Perjalanan.getStpAngPang().get(a)+ "-" + anggota;
                                }
                            }
                            i.putExtra("anggota", anggota);
                            i.putExtra("jabang", jab);
                            i.putExtra("golang", gol);

                        } else {
                            i.putExtra("anggota", "tidak memiliki anggota");
                        }
                        i.putExtra("ket",Perjalanan.getStpKetNam());
                        i.putExtra("klu",Perjalanan.getStpKlu());
                        i.putExtra("tglklu",Perjalanan.getStpTglKlu());
                        i.putExtra("jen",Perjalanan.getStpJen());
                        i.putExtra("ketjab",Perjalanan.getStpKetJab());
                        i.putExtra("ketpang",Perjalanan.getStpKetPang());
                        i.putExtra("tanggal", Perjalanan.getStpTglKeg());
                        i.putExtra("judul", Perjalanan.getStpJud());
                        i.putExtra("no", Perjalanan.getStpNom());
                        i.putExtra("status", Perjalanan.getStpTglAcc());
                        i.putExtra("prov", Perjalanan.getStpProv());
                        i.putExtra("kot", Perjalanan.getStpKot());
                        i.putExtra("kec", Perjalanan.getStpKec());
                        i.putExtra("des", Perjalanan.getStpKel());
                        i.putExtra("tahun",tahun);
                        context.startActivity(i);
                    }
                });

            }
        });

    }
    private void tambahAnggota(String s,String golongan, String pangkat,LinearLayout lyDinamic) {
        LayoutInflater layoutInflater  =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_ang, null);
        TextView textView = (TextView)addView.findViewById(R.id.textout);
        TextView textpang = (TextView)addView.findViewById(R.id.pangkat);
        TextView textgol = (TextView)addView.findViewById(R.id.golongan);
        textpang.setText(pangkat);
        textgol.setText(golongan);
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
            ArrayList<Perjalanan> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Perjalanan item:rvDataList ){
                    if (item.getStpJud().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getStpTglAcc().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getStpTglKeg().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if(item.getStpTglAcc().toLowerCase().equals("")){

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