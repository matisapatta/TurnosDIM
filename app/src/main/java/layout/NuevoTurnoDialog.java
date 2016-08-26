package layout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.TurnosStruct;
import mobile.mads.turnosdim.Util;

/**
 * Created by mati on 8/25/16.
 */

public class NuevoTurnoDialog extends android.support.v4.app.DialogFragment {

    private TurnosStruct turno;

    public static NuevoTurnoDialog newInstance(TurnosStruct turno){
        NuevoTurnoDialog dialog = new NuevoTurnoDialog();

        // Supply turno input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("data",turno);
        dialog.setArguments(args);
        return dialog;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        turno = getArguments().getParcelable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_nueva_consulta,container, false);
        View txt = v.findViewById(R.id.lblMedicoDialogo);
        ((TextView)txt).setText(turno.getEspecialidad());
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_nueva_consulta,null);
        builder.setView(v);
        TextView textDia = (TextView)v.findViewById(R.id.lblDiaDialogo);
        TextView textNumDia = (TextView)v.findViewById(R.id.lblNumDiaDialogo);
        TextView textMes = (TextView)v.findViewById(R.id.lblMesDialogo);
        final TextView textMed = (TextView)v.findViewById(R.id.lblMedicoDialogo);
        TextView textEsp = (TextView)v.findViewById(R.id.lblEspecialidadDialogo);
        TextView textHora = (TextView)v.findViewById(R.id.lblHoraDialogo);
        final Button btnAceptar = (Button)v.findViewById(R.id.btnAceptar);
        Button btnCancelar = (Button)v.findViewById(R.id.btnCancelar);

        Date date = new Util().StringToDate(turno.getFechaTurno());
        String day = new Util().getDay(date);
        String dayn = new Util().getNumberDay(date);
        String month = new Util().getMonth(date);
        textNumDia.setText(dayn);
        textDia.setText(day);
        textMes.setText(month);
        textMed.setText(turno.getMedico());
        textEsp.setText(turno.getEspecialidad());
        textHora.setText(turno.getHoraTurno());


        builder.setTitle(R.string.nuevaConsulta)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        final AlertDialog dialog =  builder.create();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMed.setText("HOLA");
            }
        });

        return dialog;
    }

}
