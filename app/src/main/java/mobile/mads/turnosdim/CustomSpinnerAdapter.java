package mobile.mads.turnosdim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mati on 8/24/16.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> implements SpinnerAdapter, Filterable {

    private ArrayList<String> data, tempListData;
    private Context context;

    public CustomSpinnerAdapter(Context context, ArrayList<String> data){
        super(context,R.layout.support_simple_spinner_dropdown_item, data);
        this.data = data;
        this.context = context;
        this.tempListData = data;


    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                final ArrayList<String> tempFliteredDataList = new ArrayList<>();
                // We implement here the filter logic
                if (constraint == null || constraint.toString().trim().length() == 0) {
                    // No filter implemented we return all the list
                    results.values = data;
                } else {
                    // We perform filtering operation
                    String constrainString = constraint.toString().toLowerCase();
                    for (String post: data) {
                        if (post.toLowerCase().contains(constrainString)) {
                            tempFliteredDataList.add(post);
                        }
                    }
                    results.values = tempFliteredDataList ;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values!=null){
                    tempListData = (ArrayList<String>) results.values; // returns the filtered list based on the search
                    data = (ArrayList<String>) results.values; // returns the filtered list based on the search
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;


        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        // Tambien hay que cargar los items en el nuevo layout


        return row;
    }*/
}

