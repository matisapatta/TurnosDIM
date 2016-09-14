package mobile.mads.turnosdim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import database.DBManager;

public class UsuariosRegistradosActivity extends AppCompatActivity {

    private ListView list;
    private DBManager db;
    private ArrayList<UsuariosStruct> dataList;
    private ListaUsuariosAdapter adapter;
    private ProgressDialog progressDialog;
    private String success;
    private Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_registrados);
        list=(ListView)findViewById(R.id.listView);
        db = new DBManager(getApplicationContext());

    }

    @Override
    public void onStart(){
        super.onStart();
        dataList = db.getUsuarios();
        paciente = new Paciente();
        if(dataList!=null)
            adapter = new ListaUsuariosAdapter(this,dataList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {
                String selected = ((UsuariosStruct)a.getItemAtPosition(position)).getDni();
                UsuariosStruct data = db.getUsuarioPorDNI(selected);
                String url = WSConstants.StringConstants.WS_URL+WSConstants.StringConstants.WS_COMANDO_LOGIN+
                        WSConstants.StringConstants.WS_DNI+data.getDni()+WSConstants.StringConstants.WS_PASS+
                        data.getPwd()+WSConstants.StringConstants.WS_FORMATO;
                new HttpRequestTask().execute(url,data.getDni());

            }
        });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Intent i = new Intent(UsuariosRegistradosActivity.this,EntryActivity.class);
        //startActivity(i);
    }

    public class HttpRequestTask extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(UsuariosRegistradosActivity.this);
            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.auth));
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
                        JSONObject jsonObject = new JSONObject(jsonData);
                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            paciente.setIdpaciente(jsonObject.getString(JSONConstants.JSON_IDPACIENTE));
                            paciente.setNombre(jsonObject.getString(JSONConstants.JSON_NOMBRE));
                            paciente.setTokenPaciente(jsonObject.getString(JSONConstants.JSON_TOKEN));
                            paciente.setDni(params[1]);

                            // Nuevo
                            /*url = WSConstants.StringConstants.WS_COMANDO_MISDATOS+ WSConstants.StringConstants.WS_ID_PACIENTE+
                                    paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+paciente.getTokenPaciente()+
                                    WSConstants.StringConstants.WS_FORMATO;
                            String jsonData2 = sh.doGetRequest(url);
                            if(jsonData2!=null){
                                try {
                                    JSONObject jsonObject2 = new JSONObject(jsonData2);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }*/
                            db.newEntry(paciente);
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
                    Intent intent = new Intent(UsuariosRegistradosActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
            db.close();


        }

    }


}
