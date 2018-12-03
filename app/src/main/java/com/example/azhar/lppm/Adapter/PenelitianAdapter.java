package com.example.azhar.lppm.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azhar.lppm.Controller.SessionManager;
import com.example.azhar.lppm.DetailPenelitianActivity;
import com.example.azhar.lppm.LoginActivity;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.Penelitian;
import com.example.azhar.lppm.R;
import com.example.azhar.lppm.SettingActivity;

import java.util.List;

public class PenelitianAdapter extends RecyclerView.Adapter<PenelitianAdapter.ViewHolder>{
    private List<Penelitian> rvData;
    Context context;
    Dialog myDialog;
    String akses;
    private List<ModelRespon> data;
    public PenelitianAdapter(Context context,List<Penelitian> inputData,String akses){
        this.context = context;
        rvData=inputData;
        this.akses = akses;
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
    public void onBindViewHolder(ViewHolder holder, int position) {

            final Penelitian penelitian = rvData.get(position);
            holder.tvTanggalAcc.setText(penelitian.getSitTglAcc());
            holder.tvLetter.setText(penelitian.getSitJud());
            holder.tvTanggal.setText(penelitian.getSitTglKeg());
            holder.cvLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            if (this.akses.equals("kepala")) {
                holder.ivAccept.setVisibility(View.VISIBLE);
                holder.tvAccept.setVisibility(View.INVISIBLE);
                holder.ivAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                        alertdialog.setTitle("Setujui Surat ?");
                        alertdialog.setMessage("Klik Ya untuk Setuju")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "kambing", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertdialog.create();
                        alertDialog.show();

                    }
                });
                holder.cvLetter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,DetailPenelitianActivity.class);
                        context.startActivity(i);
                        Toast.makeText(context, "LKautan", Toast.LENGTH_SHORT).show();
                    }
                });
            }

    }
    @Override
    public int getItemCount() {
        return rvData.size();
    }
}