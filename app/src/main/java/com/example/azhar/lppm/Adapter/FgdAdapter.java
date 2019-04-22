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
import com.example.azhar.lppm.Activity.FormFgdActivity;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Fgd;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FgdAdapter extends RecyclerView.Adapter<FgdAdapter.ViewHolder> implements Filterable {
    private ArrayList<Fgd> rvData;
    Context context;
    Dialog myDialog;
    String akses;
    String tahun;
    private ArrayList<Fgd> rvDataList;
    SessionManager sessionManager;
    private List<ModelRespon> data;
    public FgdAdapter(Context context, ArrayList<Fgd> inputData, String akses,String tahun){
        this.context = context;
        this.tahun = tahun;
        rvData=inputData;
        this.akses = akses;
        rvDataList = new ArrayList<>(rvData);
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvLetter,tvTPesgal,tvTPesgalAcc,tvPengaju,coba;
        public CardView cvLetter;

        public ImageView ivAccept,tvAccept;
        public ViewHolder(View v){
            super(v);
            tvAccept = (ImageView)v.findViewById(R.id.tvAccept);
            ivAccept = (ImageView)v.findViewById(R.id.ivAccept);
        //    tvPengaju = (TextView)v.findViewById(R.id.tvPengaju);
            tvTPesgalAcc = (TextView) v.findViewById(R.id.tvStatus);
            cvLetter=(CardView)v.findViewById(R.id.cvLetter);
            tvTPesgal = (TextView)v.findViewById(R.id.tvTanggal);
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
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
            final Fgd Fgd = rvData.get(pofgdion);
            if (Fgd.getFgdTglAcc().equals("")){
                holder.tvTPesgalAcc.setText("Status : Belum di Setujui");
//                holder.cvLetter.setVisibility(View.INVISIBLE);
            }
            else {
                holder.tvTPesgalAcc.setText("Disetujui Pada : "+Fgd.getFgdTglAcc());
            }
            holder.tvLetter.setText(Fgd.getFgdJud());
            holder.tvTPesgal.setText(Fgd.getFgdTglPel());
            Log.d("kambingdata",""+rvData.get(pofgdion));
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
                final View popupView = inflater.inflate(R.layout.detail_fgd, null);
                ImageView close = (ImageView)popupView.findViewById(R.id.btnClose);
                TextView tvJud,tvMod,tvNar,tvNip,tvTglKeg,tvLok,tvTglPeng,tvStat,tvJen;
                LinearLayout lyDinamic;
                Button btnEdit;
                sessionManager = new SessionManager(context);
                HashMap<String,String> map = sessionManager.getDetailsLoggin();
                lyDinamic = (LinearLayout)popupView.findViewById(R.id.lyDinamic);
                btnEdit = (Button)popupView.findViewById(R.id.btnEdit);
                tvJud = (TextView)popupView.findViewById(R.id.tvJud);
                tvJen = (TextView)popupView.findViewById(R.id.tvJen);
                tvLok = (TextView)popupView.findViewById(R.id.tvLok);
                tvTglKeg = (TextView)popupView.findViewById(R.id.tvTglKeg);
                tvTglPeng = (TextView)popupView.findViewById(R.id.tvTglPeng);
                tvStat = (TextView)popupView.findViewById(R.id.tvStat);
                tvNar = (TextView)popupView.findViewById(R.id.tvNar);
                tvMod = (TextView)popupView.findViewById(R.id.tvNama);
                tvJen.setText(Fgd.getFgdJen());
                tvMod.setText(Fgd.getFgdModNam());
                tvNar.setText(Fgd.getFgdNarNam());
                tvNip = (TextView)popupView.findViewById(R.id.tvNip);
                tvNip.setText(""+map.get(sessionManager.KEY_NIP));
                tvJud.setText(Fgd.getFgdJud());
                tvLok.setText(Fgd.getFgdProv()+","+Fgd.getFgdKot()+","
                        +Fgd.getFgdKec()+","+Fgd.getFgdKel());
                if (Fgd.getFgdTglAcc().equals("")){
                    tvStat.setText("Belum Disetujui");
                }
                else {
                    tvStat.setText("Disetujui Pada : "+Fgd.getFgdTglAcc());
                }
                if (Fgd.getFgdPesNam() != null) {
                    for (int a = 0; a < Fgd.getFgdPesNam().size(); a++) {
                        tambahAnggota(Fgd.getFgdPesNam().get(a),lyDinamic);
                    }
                }
                tvTglKeg.setText(Fgd.getFgdTglPel());
                tvTglPeng.setText(Fgd.getFgdTglSurat());
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
                        Intent i = new Intent(context, FormFgdActivity.class);
                        i.putExtra("edit",String.valueOf(Fgd.getFgdId()));
                        String anggota = "-";
                        if (Fgd.getFgdPesNam() != null) {
                            for (int a = 0; a < Fgd.getFgdPesNam().size(); a++) {
                                if (anggota.equals("-")) {
                                    anggota = Fgd.getFgdPesNam().get(a);
                                } else {
                                    anggota = Fgd.getFgdPesNam().get(a) + "-" + anggota;
                                }
                            }
                            i.putExtra("anggota", anggota);

                        }
                        else {
                            i.putExtra("anggota", "tidak memiliki anggota");
                        }
                        i.putExtra("tanggal", Fgd.getFgdTglPel());
                        i.putExtra("judul", Fgd.getFgdJud());
                        i.putExtra("mod", Fgd.getFgdModNam());
                        i.putExtra("nar", Fgd.getFgdNarNam());
                        i.putExtra("status", Fgd.getFgdTglAcc());
                        i.putExtra("prov",Fgd.getFgdProv());
                        i.putExtra("kot",Fgd.getFgdKot());
                        i.putExtra("kec",Fgd.getFgdKec());
                        i.putExtra("des",Fgd.getFgdKel());
                        i.putExtra("jen",Fgd.getFgdJen());
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
            ArrayList<Fgd> filteredList =new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(rvDataList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Fgd item:rvDataList ){
                    if (item.getFgdJud().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getFgdTglAcc().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if (item.getFgdTglPel().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if(item.getFgdTglAcc().toLowerCase().equals("")){

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