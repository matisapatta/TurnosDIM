package mobile.mads.turnosdim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by mati on 8/24/16.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<ObjectStruct> implements SpinnerAdapter {

    private ArrayList<ObjectStruct> data;
    private String[] array;
    private Context context;

    public CustomSpinnerAdapter(Context context, ArrayList<ObjectStruct> data){
        super(context,R.layout.support_simple_spinner_dropdown_item,data);
        this.data = data;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        //put the data in it
        /*String item = data<position>;
        if(item != null)
        {
            TextView text1 = (TextView) row.findViewById(R.id.rowText);
            text1.setTextColor(Color.WHITE);
            text1.setText(item);
        }*/

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
        //put the data in it
        /*String item = data<position>;
        if(item != null)
        {
            TextView text1 = (TextView) row.findViewById(R.id.rowText);
            text1.setTextColor(Color.WHITE);
            text1.setText(item);
        }*/

        return row;
    }
}

