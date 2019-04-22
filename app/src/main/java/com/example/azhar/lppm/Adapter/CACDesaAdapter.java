package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.azhar.lppm.Model.Desa;
import com.example.azhar.lppm.Model.Desa;
import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.List;

public class CACDesaAdapter extends ArrayAdapter<Desa> {
    private LayoutInflater layoutInflater;
    List<Desa> mDesas;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Desa)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Desa> suggestions = new ArrayList<Desa>();
                for (Desa Desa : mDesas) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Desa.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Desa);
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
                addAll((ArrayList<Desa>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mDesas);
            }
            notifyDataSetChanged();
        }
    };

    public CACDesaAdapter(Context context, int textViewResourceId, List<Desa> Desas) {
        super(context, textViewResourceId, Desas);
        // copy all the Desas into a master list
        mDesas = new ArrayList<Desa>(Desas.size());
        mDesas.addAll(Desas);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_autocomplete, null);
        }

        final Desa Desa = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.provinsi);
        name.setText(Desa.getNama());

//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), ""+Desa.getId(), Toast.LENGTH_SHORT).show();
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
