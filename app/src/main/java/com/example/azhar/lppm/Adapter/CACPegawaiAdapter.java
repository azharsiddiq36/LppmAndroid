package com.example.azhar.lppm.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.azhar.lppm.Model.Pegawai;
import com.example.azhar.lppm.R;
import java.util.ArrayList;
import java.util.List;

public class CACPegawaiAdapter extends ArrayAdapter<Pegawai> {
    private LayoutInflater layoutInflater;
    List<Pegawai> mPegawais;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Pegawai)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Pegawai> suggestions = new ArrayList<Pegawai>();
                for (Pegawai Pegawai : mPegawais) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Pegawai.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Pegawai);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Pegawai>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mPegawais);

            }

            notifyDataSetChanged();
        }
    };

    public CACPegawaiAdapter(Context context, int textViewResourceId, List<Pegawai> Pegawais) {
        super(context, textViewResourceId, Pegawais);
        // copy all the Pegawais into a master list
        mPegawais = new ArrayList<Pegawai>(Pegawais.size());
        mPegawais.addAll(Pegawais);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_actvanggota, null);
        }

        final Pegawai Pegawai = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.actvAnggota);
        TextView nip = (TextView) view.findViewById(R.id.actvNip);
        name.setText(Pegawai.getNama());
        nip.setText(Pegawai.getNip());
        return view;
    }

    private static String getId(String id) {

        return id;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
