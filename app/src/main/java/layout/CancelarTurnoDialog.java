package layout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import mobile.mads.turnosdim.R;

/**
 * Created by mati on 8/28/16.
 */

public class CancelarTurnoDialog extends DialogFragment {

    private Spinner spinnerRazones;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_cancelar_turno,container, false);
        spinnerRazones = (Spinner)v.findViewById(R.id.spinnerRazones);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_cancelar_turno, null);
        builder.setView(v);
        spinnerRazones = (Spinner)v.findViewById(R.id.spinnerRazones);
        Button btnCancelarTurno = (Button)v.findViewById(R.id.btnCancelarTurno);
        Button btnSalir = (Button)v.findViewById(R.id.btnSalir);

        builder.setTitle(R.string.cancelarTurno);
        // Create the AlertDialog object and return it
        final AlertDialog dialog = builder.create();
        return dialog;
    }
}
