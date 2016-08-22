package mobile.mads.turnosdim;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

        TurnosViewHolder tvh = new TurnosViewHolder(itemView);

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

    public static class TurnosViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMedico;
        private TextView txtEspecialidad;
        private TextView txtFecha;

        public TurnosViewHolder(View itemView) {
            super(itemView);
            txtMedico = (TextView)itemView.findViewById(R.id.lblMedico);
            txtEspecialidad = (TextView)itemView.findViewById(R.id.lblEspecialidad);
            txtFecha = (TextView)itemView.findViewById(R.id.lblFecha);
        }

        public void bindTurno(TurnosStruct t) {
            txtMedico.setText(t.getMedico());
            txtEspecialidad.setText(t.getEspecialidad());
            txtFecha.setText(t.getFechaTurno());
        }

    }

}
