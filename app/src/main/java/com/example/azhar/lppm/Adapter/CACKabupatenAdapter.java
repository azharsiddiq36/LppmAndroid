package com.example.azhar.lppm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.azhar.lppm.Model.Kabupaten;

import com.example.azhar.lppm.R;

import java.util.ArrayList;
import java.util.List;

public class CACKabupatenAdapter extends ArrayAdapter<Kabupaten> {
    private LayoutInflater layoutInflater;
    List<Kabupaten> mKabupatens;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Kabupaten)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Kabupaten> suggestions = new ArrayList<Kabupaten>();
                for (Kabupaten Kabupaten : mKabupatens) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Kabupaten.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Kabupaten);
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
                addAll((ArrayList<Kabupaten>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mKabupatens);
            }
            notifyDataSetChanged();
        }
    };

    public CACKabupatenAdapter(Context context, int textViewResourceId, List<Kabupaten> Kabupatens) {
        super(context, textViewResourceId, Kabupatens);
        // copy all the Kabupatens into a master list
        mKabupatens = new ArrayList<Kabupaten>(Kabupatens.size());
        mKabupatens.addAll(Kabupatens);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_autocomplete, null);
        }

        final Kabupaten Kabupaten = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.provinsi);
        name.setText(Kabupaten.getNama());

//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), ""+Kabupaten.getId(), Toast.LENGTH_SHORT).show();
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
