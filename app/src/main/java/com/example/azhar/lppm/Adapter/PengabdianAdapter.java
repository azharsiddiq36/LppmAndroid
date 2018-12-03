package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azhar.lppm.Model.Pengabdian;
import com.example.azhar.lppm.R;

import java.util.List;

public class PengabdianAdapter extends RecyclerView.Adapter<PengabdianAdapter.ViewHolder> {
    List<Pengabdian> pengabdians;
    Context context;
    public PengabdianAdapter(Context context,List<Pengabdian>pengabdians){
        this.pengabdians = pengabdians;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_letter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pengabdian pengabdian = pengabdians.get(position);
        holder.tvLetter.setText(pengabdian.getSipJud());
        holder.tvTanggalAcc.setText(pengabdian.getSipTglAcc());
        holder.tvTanggal.setText(pengabdian.getSipTglKeg());
    }

    @Override
    public int getItemCount() {
        return pengabdians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLetter,tvTanggal,tvTanggalAcc;
        CardView cvLetter;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTanggalAcc = (TextView)itemView.findViewById(R.id.tvStatus);
            tvLetter = (TextView)itemView.findViewById(R.id.tvLetter);
            tvTanggal = (TextView)itemView.findViewById(R.id.tvTanggal);
            cvLetter = (CardView)itemView.findViewById(R.id.cvLetter);

        }
    }
}

