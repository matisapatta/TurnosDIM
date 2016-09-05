package mobile.mads.turnosdim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import layout.UserSettingsFragment;

public class RegisterActivity extends AppCompatActivity {

    private String success;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        guardar = (Button)findViewById(R.id.btnSaveNewUser);
    }

    @Override
    public void onStart(){
        super.onStart();
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
}
