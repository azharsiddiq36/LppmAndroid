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
import com.example.azhar.lppm.Activity.FormPenelitianActivity;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PenelitianAdapter extends RecyclerView.Adapter<PenelitianAdapter.ViewHolder> implements Filterable {
    private ArrayList<Penelitian> rvData;
    Context context;
    Dialog myDialog;
    String akses;
    String tahun;
//    String img_url = "http://karir-suska.org/lppm/public/img/foto_ktp/";
    String img_url = "http://192.168.43.201/lppm/public/img/foto_ktp/";
    SessionManager sessionManager;
    private ArrayList<Penelitian> rvDataList;
    private List<ModelRespon> data;
    public PenelitianAdapter(Context context,ArrayList<Penelitian> inputData,String akses,String tahun){
        this.context = context;
        rvData=inputData;
        this.tahun = tahun;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
            final Penelitian penelitian = rvData.get(position);
            if (penelitian.getSitTglAcc().equals("")){
                holder.tvTanggalAcc.setText("Status : Belum di Setujui");
            }
            else {
                holder.tvTanggalAcc.setText("Disetujui Pada : "+penelitian.getSitTglAcc());
            }
            holder.tvLetter.setText(penelitian.getSitJud());
            holder.tvTanggal.setText(penelitian.getSitTglKeg());
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
                final View popupView = inflater.inflate(R.layout.detail_penelitian, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setWidth(width);
                popupWindow.setHeight(height-200);
                popupView.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                TextView tvJud,tvKtp,tvNama,tvNip,tvTglKeg,tvLok,tvNoWa,tvTglPeng,tvStat,tvIns;
                LinearLayout lyDinamic;
                ImageView fotktp;
                Button btnEdit;
                sessionManager = new SessionManager(context);
                HashMap<String,String> map = sessionManager.getDetailsLoggin();
                fotktp = (ImageView)popupView.findViewById(R.id.gambarKtp);
                lyDinamic = (LinearLayout)popupView.findViewById(R.id.lyDinamic);
                btnEdit = (Button)popupView.findViewById(R.id.btnEdit);
                tvJud = (TextView)popupView.findViewById(R.id.tvJud);
                tvKtp = (TextView)popupView.findViewById(R.id.tvKtp);
                tvNoWa = (TextView)popupView.findViewById(R.id.tvWa);
                tvLok = (TextView)popupView.findViewById(R.id.tvLok);
                tvTglKeg = (TextView)popupView.findViewById(R.id.tvTglKeg);
                tvTglPeng = (TextView)popupView.findViewById(R.id.tvTglPeng);
                tvStat = (TextView)popupView.findViewById(R.id.tvStat);
                tvIns = (TextView)popupView.findViewById(R.id.tvIns);
                tvNip = (TextView)popupView.findViewById(R.id.tvNip);
                tvNama = (TextView)popupView.findViewById(R.id.tvNama);
                tvNama.setText(""+map.get(sessionManager.KEY_NAMA));
                tvNip.setText(""+map.get(sessionManager.KEY_NIP));
                tvJud.setText(penelitian.getSitJud());
                tvKtp.setText(penelitian.getSitNomKtp());
                tvIns.setText(penelitian.getSitInsTuj());
                tvLok.setText(penelitian.getSitProv()+","+penelitian.getSitKot()+","
                +penelitian.getSitKec()+","+penelitian.getSitKel());
                Picasso.get()
                .load(img_url+penelitian.getSitFotKtp()+".jpg")
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(fotktp);
                if (penelitian.getSitTglAcc().equals("")){
                    tvStat.setText("Belum Disetujui");
                }
                else {
                    tvStat.setText("Disetujui Pada : "+penelitian.getSitTglAcc());
                }
                if (penelitian.getSitAngNam() != null) {
                    for (int a = 0; a < penelitian.getSitAngNam().size(); a++) {
                        tambahAnggota(penelitian.getSitAngNam().get(a),lyDinamic);
                    }
                }
                tvTglKeg.setText(penelitian.getSitTglKeg());
                tvNoWa.setText(penelitian.getSitNom());
                tvTglPeng.setText(penelitian.getSitTglSratKel());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                Intent i = new Intent(context, FormPenelitianActivity.class);
                i.putExtra("edit",String.valueOf(penelitian.getSitId()));
                String anggota = "-";
                if (penelitian.getSitAngNam() != null) {
                    for (int a = 0; a < penelitian.getSitAngNam().size(); a++) {
                        if (anggota.equals("-")) {
                            anggota = penelitian.getSitAngNam().get(a);
                        } else {
                            anggota = penelitian.getSitAngNam().get(a) + "-" + anggota;
                        }
                    }
                    i.putExtra("anggota", anggota);

                }
                else {
                    i.putExtra("anggota", "tidak memiliki anggota");
                }
                i.putExtra("tanggal", penelitian.getSitTglKeg());
                i.putExtra("judul", penelitian.getSitJud());
                i.putExtra("ktp",penelitian.getSitNomKtp());
                i.putExtra("wa",penelitian.getSitNom());
                i.putExtra("status", penelitian.getSitTglAcc());
                i.putExtra("prov",penelitian.getSitProv());
                i.putExtra("kot",penelitian.getSitKot());
                i.putExtra("kec",penelitian.getSitKec());
                i.putExtra("des",penelitian.getSitKel());
                i.putExtra("instansi",penelitian.getSitInsTuj());
                i.putExtra("tahun",tahun);
                context.startActivity(i);
                    }
                });

            }
        });

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
            ArrayList<Penelitian> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Penelitian item:rvDataList ){
                    if (item.getSitJud().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getSitTglAcc().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getSitTglKeg().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if(item.getSitTglAcc().toLowerCase().equals("")){

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