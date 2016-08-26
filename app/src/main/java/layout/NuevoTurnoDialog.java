package layout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import database.DBManager;
import mobile.mads.turnosdim.CustomSpinnerAdapter;
import mobile.mads.turnosdim.JSONConstants;
import mobile.mads.turnosdim.ObjectStruct;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.ServiceHandler;
import mobile.mads.turnosdim.TurnosStruct;
import mobile.mads.turnosdim.Util;
import mobile.mads.turnosdim.WSConstants;

/**
 * Created by mati on 8/25/16.
 */

public class NuevoTurnoDialog extends android.support.v4.app.DialogFragment {

    private TurnosStruct turno;
    private String idmed;
    private String success;
    private Context context;

    public static NuevoTurnoDialog newInstance(TurnosStruct turno, String idmed){
        NuevoTurnoDialog dialog = new NuevoTurnoDialog();

        // Supply turno input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("data",turno);
        args.putString("idmed",idmed);

        dialog.setArguments(args);
        return dialog;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        turno = getArguments().getParcelable("data");
        idmed = getArguments().getString("idmed");

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
                DBManager db = new DBManager(getContext());
                Paciente paciente = db.getPaciente(getContext());
                String url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_DARCONSULTA+
                        WSConstants.StringConstants.WS_ID_PACIENTE+paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+
                        paciente.getTokenPaciente()+ WSConstants.StringConstants.WS_IDMEDICO+idmed+ WSConstants.StringConstants.WS_IDOBRASOCIAL+
                        "OSPR"+ WSConstants.StringConstants.WS_IDPLAN+"123";
                new HttpRequestTask().execute(url);

                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        context = getActivity();
        return dialog;
    }

    public class HttpRequestTask extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                ServiceHandler sh = new ServiceHandler();

                // Make WS Call
                String jsonData = sh.doGetRequest(params[0]);

                if(jsonData!=null){
                    try {
                        // Manejo de Array en JSON

                        JSONObject jsonObject = new JSONObject(jsonData);
                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        return jsonObject.getString(JSONConstants.JSON_MENSAJE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String string) {
            progressDialog.dismiss();
            if(string!=null) {
                if(success.equals("1")){
                    Toast.makeText(context, string,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,"Se ha producido un error al guardar el turno" ,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
        }

    }

}
