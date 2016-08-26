package layout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.TurnosStruct;

/**
 * Created by mati on 8/25/16.
 */

public class NuevoTurnoDialog extends android.support.v4.app.DialogFragment {

    private String medico;

    public static NuevoTurnoDialog newInstance(TurnosStruct turno){
        NuevoTurnoDialog dialog = new NuevoTurnoDialog();

        // Supply turno input as an argument.
        Bundle args = new Bundle();
        args.putString("med",turno.getMedico());
        dialog.setArguments(args);
        return dialog;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        medico = getArguments().getString("med");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(medico)
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
