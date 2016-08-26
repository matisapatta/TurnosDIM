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
import android.widget.TextView;

import org.w3c.dom.Text;

import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.TurnosStruct;

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
        TextView text = (TextView)v.findViewById(R.id.lblMedicoDialogo);
        text.setText(turno.getHoraTurno());
        builder.setMessage(turno.getMedico())
                .setTitle(R.string.nuevaConsulta)
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
        return builder.create();
    }

}
