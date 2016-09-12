package mobile.mads.turnosdim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alan on 11/9/2016.
 */

public class InfoViewsAdapter extends RecyclerView.Adapter<InfoViewsAdapter.InfoViewsViewHolder> {

    private ArrayList<InfoViewsStruct> datos;


    public InfoViewsAdapter(ArrayList<InfoViewsStruct> datos) {
        this.datos = datos;
    }

    @Override
    public InfoViewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.infoviews_layout, viewGroup, false);

        InfoViewsViewHolder ivvh = new InfoViewsAdapter.InfoViewsViewHolder(itemView);

        return ivvh;
    }

    @Override
    public void onBindViewHolder(InfoViewsViewHolder viewHolder, int pos) {
        InfoViewsStruct item = datos.get(pos);
        viewHolder.bindInfoview(item);
    }


    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class InfoViewsViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtTitulo;
        private TextView txtTexto;
        private ImageView imgIcono;

        public InfoViewsViewHolder(View itemView) {
            super(itemView);
            txtTitulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            txtTexto = (TextView)itemView.findViewById(R.id.txtTexto);
            imgIcono = (ImageView)itemView.findViewById(R.id.imgIcono);
        }

        public void bindInfoview(InfoViewsStruct t) {

        }

    }

}