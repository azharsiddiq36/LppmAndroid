package com.example.azhar.lppm.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.azhar.lppm.Model.Semuaprovinsi;
import com.example.azhar.lppm.R;
import java.util.ArrayList;
import java.util.List;

public class CACProvinsiAdapter extends ArrayAdapter<Semuaprovinsi> {
    private LayoutInflater layoutInflater;
    List<Semuaprovinsi> mSemuaprovinsis;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Semuaprovinsi)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Semuaprovinsi> suggestions = new ArrayList<Semuaprovinsi>();
                for (Semuaprovinsi Semuaprovinsi : mSemuaprovinsis) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Semuaprovinsi.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Semuaprovinsi);
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
                addAll((ArrayList<Semuaprovinsi>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mSemuaprovinsis);
            }
            notifyDataSetChanged();
        }
    };

    public CACProvinsiAdapter(Context context, int textViewResourceId, List<Semuaprovinsi> Semuaprovinsis) {
        super(context, textViewResourceId, Semuaprovinsis);
        // copy all the Semuaprovinsis into a master list
        mSemuaprovinsis = new ArrayList<Semuaprovinsi>(Semuaprovinsis.size());
        mSemuaprovinsis.addAll(Semuaprovinsis);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_autocomplete, null);
        }

        final Semuaprovinsi Semuaprovinsi = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.provinsi);
        name.setText(Semuaprovinsi.getNama());
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
