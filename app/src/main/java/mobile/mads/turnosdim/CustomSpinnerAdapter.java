package mobile.mads.turnosdim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mati on 8/24/16.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> implements SpinnerAdapter {

    private ArrayList<String> data;
    private Context context;

    public CustomSpinnerAdapter(Context context, ArrayList<String> data){
        super(context,R.layout.support_simple_spinner_dropdown_item, data);
        this.data = data;
        this.context = context;


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

