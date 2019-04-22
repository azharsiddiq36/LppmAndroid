package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Activity.FgdActivity;
import com.example.azhar.lppm.Model.Pengguna;
import com.example.azhar.lppm.Activity.PenelitianActivity;
import com.example.azhar.lppm.Activity.PengabdianActivity;
import com.example.azhar.lppm.Activity.PerjalananActivity;
import com.example.azhar.lppm.R;

import java.util.ArrayList;

public class PenggunaAdapter extends RecyclerView.Adapter<PenggunaAdapter.ViewHolder>{
    private ArrayList<Pengguna> dataUser;
    private Context context;
    private ArrayList<Integer> jumlahfgd;
    private ArrayList<Integer> jumlahpenelitian;
    private ArrayList<Integer> jumlahperjalanan;
    private ArrayList<Integer> jumlahpengabdian;
    private String layout;
    public static final String IMAGE_URL_BASE_PATH="http://192.168.43.201/lppm/office/";
    public PenggunaAdapter(Context context, ArrayList<Pengguna>dataUser,
                           ArrayList<Integer> jumlahfgd,ArrayList<Integer> jumlahpenelitian,
                           ArrayList<Integer> jumlahperjalanan,
                           ArrayList<Integer> jumlahpengabdian,String layout){
        this.dataUser = dataUser;
        this.context = context;
        this.jumlahpenelitian = jumlahpenelitian;
        this.jumlahpengabdian = jumlahpengabdian;
        this.jumlahperjalanan = jumlahperjalanan;
        this.jumlahfgd = jumlahfgd;
        this.layout = layout;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_disposisi,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pengguna pengguna = dataUser.get(position);
        int fgd = jumlahfgd.get(position).intValue();
        int penelitian = jumlahpenelitian.get(position).intValue();
        int pengabdian = jumlahpengabdian.get(position).intValue();
        Log.d("kepitping",""+jumlahpengabdian+pengguna.getTahun() );
        int perjalanan = jumlahperjalanan.get(position).intValue();
        holder.tvFgd.setText(fgd+" Surat");
        holder.tvPenelitian.setText(penelitian+" Surat");
        holder.tvPerjalanan.setText(perjalanan+" Surat");
        holder.tvPengabdian.setText(pengabdian+" Surat");
        if (layout.equals("penelitian")){

            holder.tvPenelitian.setVisibility(View.VISIBLE);
            holder.tvFgd.setVisibility(View.INVISIBLE);
            holder.tvPengabdian.setVisibility(View.INVISIBLE);
            holder.tvPerjalanan.setVisibility(View.INVISIBLE);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,PenelitianActivity.class);
                    Toast.makeText(context, ""+pengguna.getTahun(), Toast.LENGTH_SHORT).show();
                    i.putExtra("tahun",pengguna.getTahun());
                    i.putExtra("layout","penelitian");
                    context.startActivity(i);
                }
            });
        }
        else if(layout.equals("fgd")){

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,FgdActivity.class);
                    i.putExtra("tahun",pengguna.getTahun());
                    i.putExtra("layout","fgd");
                    context.startActivity(i);
                }
            });
            holder.tvPenelitian.setVisibility(View.INVISIBLE);
            holder.tvFgd.setVisibility(View.VISIBLE);
            holder.tvPengabdian.setVisibility(View.INVISIBLE);
            holder.tvPerjalanan.setVisibility(View.INVISIBLE);
        }
        else if(layout.equals("pengabdian")){
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,PengabdianActivity.class);
                    i.putExtra("tahun",pengguna.getTahun());
                    i.putExtra("layout","pengabdian");
                    context.startActivity(i);
                }
            });
            holder.tvPenelitian.setVisibility(View.INVISIBLE);
            holder.tvFgd.setVisibility(View.INVISIBLE);
            holder.tvPengabdian.setVisibility(View.VISIBLE);
            holder.tvPerjalanan.setVisibility(View.INVISIBLE);
        }
        else{
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,PerjalananActivity.class);
                    i.putExtra("tahun",pengguna.getTahun());
                    i.putExtra("layout","perjalanan");
                    context.startActivity(i);
                }
            });
            holder.tvPenelitian.setVisibility(View.INVISIBLE);
            holder.tvFgd.setVisibility(View.INVISIBLE);
            holder.tvPengabdian.setVisibility(View.INVISIBLE);
            holder.tvPerjalanan.setVisibility(View.VISIBLE);
        }
        //holder.tvNip.setText(pengguna.getNip());
        holder.tvTahun.setText(pengguna.getTahun());

//        holder.tvNama.setText(pengguna.getNama());
//        holder.tvJabatan.setText(pengguna.getHak_akses());
//        String image_url = IMAGE_URL_BASE_PATH + pengguna.getFoto();
//        Picasso.get()
//                .load(image_url)
//                .placeholder(android.R.drawable.sym_def_app_icon)
//                .error(android.R.drawable.sym_def_app_icon)
//                .into(holder.ivFoto);
    }
    @Override
    public int getItemCount() {
        return dataUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvFgd,tvPerjalanan,tvTahun,tvPenelitian,tvPengabdian;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFgd = (TextView)itemView.findViewById(R.id.tvFgd);
            tvTahun = (TextView)itemView.findViewById(R.id.tvTahun);
            tvPerjalanan = (TextView)itemView.findViewById(R.id.tvPerjalanan);
            tvPengabdian = (TextView)itemView.findViewById(R.id.tvPengabdian);
            tvPenelitian = (TextView)itemView.findViewById(R.id.tvPenelitian);
            cardView = (CardView)itemView.findViewById(R.id.cvLetter);

        }
    }
}