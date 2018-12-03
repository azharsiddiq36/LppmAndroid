package com.example.azhar.lppm.Adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azhar.lppm.Model.Fgd;
import com.example.azhar.lppm.R;

import java.util.List;

public class FgdAdapter extends RecyclerView.Adapter<FgdAdapter.ViewHolder> {
    private List<Fgd> fgds;
    Context context;
    public FgdAdapter(Context context,List<Fgd> fgds){
        this.context = context;
        this.fgds = fgds;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_letter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder   ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fgd fgd = fgds.get(position);
        holder.tvTanggal.setText(fgd.getFgdTglPel());
        holder.tvLetter.setText(fgd.getFgdJud());
        holder.tvTanggalAcc.setText(fgd.getFgdTglAcc());
    }

    @Override
    public int getItemCount() {
        return fgds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLetter,tvTanggal,tvTanggalAcc,tvTanggalPel;
        CardView cvLetter;
        public ViewHolder(View itemView) {
            super(itemView);
            tvLetter = (TextView)itemView.findViewById(R.id.tvLetter);
            tvTanggal = (TextView) itemView.findViewById(R.id.tvTanggal);
            cvLetter = (CardView)itemView.findViewById(R.id.cvLetter);
            tvTanggalAcc = (TextView)itemView.findViewById(R.id.tvStatus);
        }
    }
}
