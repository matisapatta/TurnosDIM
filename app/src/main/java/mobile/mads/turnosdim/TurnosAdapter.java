package mobile.mads.turnosdim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import layout.TurnosFragment;

/**
 * Created by mati on 8/22/16.
 */

public class TurnosAdapter extends RecyclerView.Adapter<TurnosAdapter.TurnosViewHolder> {

    private ArrayList<TurnosStruct> datos;


    public TurnosAdapter(ArrayList<TurnosStruct> datos) {
        this.datos = datos;
    }

    @Override
    public TurnosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.turnos_layout, viewGroup, false);

        TurnosViewHolder tvh = new TurnosViewHolder(itemView, new TurnosAdapter.IMyViewHolderClicks(){
                public void onLineClick(View v, int i){

                    Intent intent = new Intent(v.getContext(), DetalleTurno.class);
                    Bundle b = new Bundle();
                    b.putString("IDTURNO",datos.get(i).getIdTurno());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                    }

        });

        return tvh;
    }

    @Override
    public void onBindViewHolder(TurnosViewHolder viewHolder, int pos) {
        TurnosStruct item = datos.get(pos);
        viewHolder.bindTurno(item);
    }


    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class TurnosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtMedico;
        private TextView txtEspecialidad;
        private TextView txtHora;
        private TextView txtDia;
        private TextView txtNumDia;
        private TextView txtMes;
        public IMyViewHolderClicks mListener;

        public TurnosViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            txtMedico = (TextView)itemView.findViewById(R.id.lblMedico);
            txtEspecialidad = (TextView)itemView.findViewById(R.id.lblEspecialidad);
            txtHora = (TextView)itemView.findViewById(R.id.lblHora);
            txtDia = (TextView)itemView.findViewById(R.id.lblDia);
            txtNumDia = (TextView)itemView.findViewById(R.id.lblNumDia);
            txtMes = (TextView)itemView.findViewById(R.id.lblMes);
            itemView.setOnClickListener(this);

        }

        public void bindTurno(TurnosStruct t) {

            txtMedico.setText(t.getMedico());
            txtEspecialidad.setText(t.getEspecialidad());
            txtHora.setText(t.getHoraTurno());
            Date date = new Util().StringToDate(t.getFechaTurno());
            String day = new Util().getDay(date);
            String dayn = new Util().getNumberDay(date);
            String month = new Util().getMonth(date);
            txtNumDia.setText(dayn);
            txtDia.setText(day);
            txtMes.setText(month);



        }

        @Override
        public void onClick(View v){
            mListener.onLineClick(v, getAdapterPosition());
        }

    }

    public interface IMyViewHolderClicks {
        public void onLineClick(View v, int i);
    }

}
