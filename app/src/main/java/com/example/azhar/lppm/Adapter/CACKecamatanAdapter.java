package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.azhar.lppm.Model.Kecamatan;
import com.example.azhar.lppm.Model.Kecamatan;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.List;

public class CACKecamatanAdapter extends ArrayAdapter<Kecamatan> {
    private LayoutInflater layoutInflater;
    List<Kecamatan> mKecamatans;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Kecamatan)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Kecamatan> suggestions = new ArrayList<Kecamatan>();
                for (Kecamatan Kecamatan : mKecamatans) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Kecamatan.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Kecamatan);
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
                addAll((ArrayList<Kecamatan>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mKecamatans);
            }
            notifyDataSetChanged();
        }
    };

    public CACKecamatanAdapter(Context context, int textViewResourceId, List<Kecamatan> Kecamatans) {
        super(context, textViewResourceId, Kecamatans);
        // copy all the Kecamatans into a master list
        mKecamatans = new ArrayList<Kecamatan>(Kecamatans.size());
        mKecamatans.addAll(Kecamatans);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_autocomplete, null);
        }

        final Kecamatan Kecamatan = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.provinsi);
        name.setText(Kecamatan.getNama());

//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), ""+Kecamatan.getId(), Toast.LENGTH_SHORT).show();
//            }
//        });
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
