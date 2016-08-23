package mobile.mads.turnosdim;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
                public void onLineClick(View v, String s){
                    Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
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
        private TextView txtFecha;
        public IMyViewHolderClicks mListener;

        public TurnosViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            txtMedico = (TextView)itemView.findViewById(R.id.lblMedico);
            txtEspecialidad = (TextView)itemView.findViewById(R.id.lblEspecialidad);
            txtFecha = (TextView)itemView.findViewById(R.id.lblFecha);
            itemView.setOnClickListener(this);

        }

        public void bindTurno(TurnosStruct t) {
            txtMedico.setText(t.getMedico());
            txtEspecialidad.setText(t.getEspecialidad());
            txtFecha.setText(t.getFechaTurno());
        }

        @Override
        public void onClick(View v){
            mListener.onLineClick(v, txtMedico.getText().toString());
        }

    }

    public interface IMyViewHolderClicks {
        public void onLineClick(View v, String s);
    }

}
