package layout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import database.DBManager;
import mobile.mads.turnosdim.CustomSpinnerAdapter;
import mobile.mads.turnosdim.JSONConstants;
import mobile.mads.turnosdim.ObjectStruct;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.ServiceHandler;
import mobile.mads.turnosdim.WSConstants;

/**
 * Created by mati on 8/28/16.
 */

public class CancelarTurnoDialog extends DialogFragment {

    private ArrayList<ObjectStruct> datos;
    private ArrayList<String> datosText;
    private Spinner spinnerRazones;
    private Context context;
    private String idTurno;
    private String success;
    private Button btnCancelarTurno;
    private String idRazon;




    public static CancelarTurnoDialog newInstance(ArrayList<ObjectStruct> datos, ArrayList<String> datosText, String idTurno){
        CancelarTurnoDialog dialog = new CancelarTurnoDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("data",datos);
        args.putStringArrayList("texto",datosText);
        args.putString("idturno",idTurno);

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        datos = getArguments().getParcelableArrayList("data");
        datosText = getArguments().getStringArrayList("texto");
        idTurno = getArguments().getString("idturno");
    }


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
        context = getActivity();
        spinnerRazones = (Spinner)v.findViewById(R.id.spinnerRazones);
        btnCancelarTurno = (Button)v.findViewById(R.id.btnCancelarTurno);
        Button btnSalir = (Button)v.findViewById(R.id.btnSalir);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,datosText);
        spinnerRazones.setAdapter(adapter);


        spinnerRazones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idRazon = datos.get(position).getIdObj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle(R.string.cancelarTurno);
        // Create the AlertDialog object and return it
        final AlertDialog dialog = builder.create();



        return dialog;
    }
    @Override
    public void onStart(){
        super.onStart();
        btnCancelarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ObjectStruct razon = datos.get(spinnerRazones.getSelectedItemPosition());
                DBManager db = new DBManager(context);
                Paciente paciente = db.getPaciente(context);

                String url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_CANCELARTURNO+
                        WSConstants.StringConstants.WS_ID_PACIENTE+paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+
                        paciente.getTokenPaciente()+ WSConstants.StringConstants.WS_IDMOTIVOCANCELACION+idRazon+
                        WSConstants.StringConstants.WS_IDTURNO+idTurno;

                new HttpRequestTask().execute(url);
                getDialog().dismiss();

            }
        });



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
                    ((AppCompatActivity)context).finish();
                    //FragmentManager transaction = ((FragmentActivity)context).getSupportFragmentManager();
                    //transaction.beginTransaction().replace(R.id.content_main,NuevoTurnoFragment.newInstance())
                    //        .commit();
                } else {
                    Toast.makeText(context,"Se ha producido un error al guardar el turno" ,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
        }

    }
}
