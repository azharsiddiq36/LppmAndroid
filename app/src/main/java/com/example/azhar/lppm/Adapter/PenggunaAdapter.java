package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.Model.Pengguna;
import com.example.azhar.lppm.Model.Pengguna;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.List;

public class PenggunaAdapter extends RecyclerView.Adapter<PenggunaAdapter.ViewHolder>{
    private List<Pengguna> dataUser;
    private Context context;
    private List<Integer> jumlahfgd;
    private List<Integer> jumlahpenelitian;
    private List<Integer> jumlahperjalanan;
    private List<Integer> jumlahpengabdian;
    public PenggunaAdapter(Context context, List<Pengguna>dataUser,
                           List<Integer> jumlahfgd,List<Integer> jumlahpenelitian,
                           List<Integer> jumlahperjalanan,
                           List<Integer> jumlahpengabdian){
        this.dataUser = dataUser;
        this.context = context;
        this.jumlahpenelitian = jumlahpenelitian;
        this.jumlahpengabdian = jumlahpengabdian;
        this.jumlahperjalanan = jumlahperjalanan;
        this.jumlahfgd = jumlahfgd;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_disposisi,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pengguna pengguna = dataUser.get(position);
        int fgd = jumlahfgd.get(position).intValue();
        int penelitian = jumlahpenelitian.get(position).intValue();
        int pengabdian = jumlahpengabdian.get(position).intValue();
        int perjalanan = jumlahperjalanan.get(position).intValue();
        holder.tvNip.setText(pengguna.getNip());
        holder.tvTahun.setText(pengguna.getTahun());
        holder.tvFgd.setText("Jumlah Surat Fgd = "+fgd);
        holder.tvPenelitian.setText("Jumlah Surat Penelitian = "+penelitian);
        holder.tvPerjalanan.setText("Jumlah Surat Perjalanan = "+perjalanan);
        holder.tvPengabdian.setText("Jumlah Surat Pengabdian = "+pengabdian);
        holder.tvNama.setText(pengguna.getNama());
        holder.tvJabatan.setText(pengguna.getHak_akses());
    }

    @Override
    public int getItemCount() {
        return dataUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNama,tvNip,tvJabatan,tvFgd,tvPerjalanan,tvTahun,tvPenelitian,tvPengabdian;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFgd = (TextView)itemView.findViewById(R.id.tvFgd);
            tvTahun = (TextView)itemView.findViewById(R.id.tvTahun);
            tvPerjalanan = (TextView)itemView.findViewById(R.id.tvPerjalanan);
            tvPengabdian = (TextView)itemView.findViewById(R.id.tvPengabdian);
            tvPenelitian = (TextView)itemView.findViewById(R.id.tvPenelitian);
            tvFgd = (TextView)itemView.findViewById(R.id.tvFgd);
            tvNama = (TextView)itemView.findViewById(R.id.tvNama);
            tvNip = (TextView)itemView.findViewById(R.id.tvNip);
            tvJabatan = (TextView)itemView.findViewById(R.id.tvJabatan);
            cardView = (CardView)itemView.findViewById(R.id.cvLetter);
        }
    }
}