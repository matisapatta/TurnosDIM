package mobile.mads.turnosdim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by mati on 9/14/16.
 */

public class ListaUsuariosAdapter extends ArrayAdapter <UsuariosStruct> {

    static class ViewHolder {
        TextView title;
        TextView subtitle;

    }

    private ArrayList<UsuariosStruct> list;

    public ListaUsuariosAdapter(Context context, ArrayList<UsuariosStruct> data){
        super(context,R.layout.lista_usuarios_layout,data);
        this.list = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflate XML
        View item = convertView;
        ViewHolder holder;
        if(item==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.lista_usuarios_layout, null);
            holder = new ViewHolder();
            holder.title = (TextView)item.findViewById(R.id.titleLabel);
            holder.subtitle = (TextView)item.findViewById(R.id.subtitleLabel);
            item.setTag(holder);
        } else {
            holder = (ViewHolder)item.getTag();
        }
        holder.title.setText(list.get(position).getNom());
        holder.subtitle.setText(list.get(position).getDni());
        return(item);
    }
}
