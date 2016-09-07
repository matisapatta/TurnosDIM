package mobile.mads.turnosdim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import database.DBManager;
import layout.CancelarTurnoDialog;
import layout.UserSettingsFragment;

public class RegisterActivity extends AppCompatActivity {

    private String success;
    private Button guardar;
    private Spinner spinnerSexo;
    private Spinner spinnerPlan;
    private Spinner spinnerCobertura;
    private String[] spinnerArray;
    private ArrayList<String> coberturaArray;
    private ArrayList<String> planArray;
    private ArrayList<ObjectStruct> coberturaObj;
    private ArrayList<ObjectStruct> planObj;

    private String url;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        guardar = (Button)findViewById(R.id.btnSaveNewUser);
        spinnerSexo = (Spinner)findViewById(R.id.spinnerSexo);
        spinnerPlan = (Spinner)findViewById(R.id.spinnerPlan);
        spinnerCobertura = (Spinner)findViewById(R.id.spinnerCobertura);
        spinnerArray = getResources().getStringArray(R.array.sexoArray);

    }

    @Override
    public void onStart(){
        super.onStart();

        ArrayAdapter<String> sexoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(sexoAdapter);
        url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_GET_OBRASOCIAL+WSConstants.StringConstants.WS_ID_PACIENTE +
                WSConstants.StringConstants.FIXED_IDPACIENTE+WSConstants.StringConstants.WS_TOKEN+ WSConstants.StringConstants.FIXED_TOKEN;
        new HttpRequestTaskCobertura().execute(url);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://portalweb.dim.com.ar:8091/global.ashx?comando=demo_PacienteGuardarDatos" +
                        "&idpaciente=111111&token=a00e7719-6645-42ba-bcc0-fe4f03ea2927&telefono=44444444" +
                        "&telefonoadicional=1111111111&fechanacimiento=22/12/2010&sexo=M&email=pepe@pepe.com";
                new HttpRequestTask().execute(url);
            }
        });

    }


    public class HttpRequestTask extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.loading));
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
                    Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, EntryActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Se ha producido un error al guardar el turno" ,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
        }

    }

    public class HttpRequestTaskCobertura extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.loading));
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
                        JSONObject json_data;
                        coberturaArray = new ArrayList<>();
                        coberturaObj = new ArrayList<>();

                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            JSONArray jArray = jsonObject.getJSONArray("items");

                            for(int i=0;i<jArray.length();i++){
                                ObjectStruct cobertura = new ObjectStruct();
                                json_data = jArray.getJSONObject(i);
                                cobertura.setIdObj(json_data.getString(JSONConstants.JSON_ID));
                                cobertura.setDescripcion(json_data.getString(JSONConstants.JSON_DESCRIPCION));
                                coberturaArray.add(cobertura.getDescripcion());
                                coberturaObj.add(cobertura);
                            }

                            return success;
                        } else  {
                            return jsonObject.getString(JSONConstants.JSON_MENSAJE);
                        }


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
            if(success!=null) {
                if(success.equals("1")){
                    // Set adapter
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getApplicationContext(),coberturaArray);
                    spinnerCobertura.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }


        }

    }
}
