package mobile.mads.turnosdim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import database.DBManager;


public class EntryActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button newUserBtn;
    private String success;
    private EditText dniTxt;
    private EditText passTxt;
    private String url;
    private Paciente paciente;
    private ProgressDialog progressDialog;
    private DBManager db;
    private String dni;
    private String pwd;
    private TextView resetPwdBtn;
    private TextView registeredUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // Asocio vistas a variables
        loginBtn=(Button)findViewById(R.id.loginBtn);
        dniTxt=(EditText)findViewById(R.id.loginDNI);
        passTxt=(EditText)findViewById(R.id.loginPassword);
        newUserBtn=(Button)findViewById(R.id.newUserBtn);
        resetPwdBtn=(TextView)findViewById(R.id.resetPwdBtn);
        registeredUser=(TextView)findViewById(R.id.registeredUser);

        // Inicializo la DB
        db = new DBManager(getApplicationContext());
        //db.update();

        if(db.getPaciente(getApplicationContext())!=null){
            db.close();
            Intent intent = new Intent(EntryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        if(db.getPaciente(getApplicationContext())!=null)
            finish();
    }

    @Override
    public void onStart(){
        super.onStart();
        paciente = new Paciente();
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                url = WSConstants.StringConstants.WS_URL+WSConstants.StringConstants.WS_COMANDO_LOGIN+
                        WSConstants.StringConstants.WS_DNI+dniTxt.getText()+WSConstants.StringConstants.WS_PASS+
                        passTxt.getText()+WSConstants.StringConstants.WS_FORMATO;
                dni = dniTxt.getText().toString();
                pwd = passTxt.getText().toString();
                new HttpRequestTask().execute();

            }
        });
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        resetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this,ResetPwdActivity.class);
                startActivity(i);

            }
        });
        registeredUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this,UsuariosRegistradosActivity.class);
                startActivity(i);

            }
        });
    }

    public class HttpRequestTask extends AsyncTask<Void , Void, String> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(EntryActivity.this);
            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.auth));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                ServiceHandler sh = new ServiceHandler();

                // Make WS Call
                String jsonData = sh.doGetRequest(url);

                if(jsonData!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            paciente.setIdpaciente(jsonObject.getString(JSONConstants.JSON_IDPACIENTE));
                            paciente.setNombre(jsonObject.getString(JSONConstants.JSON_NOMBRE));
                            paciente.setTokenPaciente(jsonObject.getString(JSONConstants.JSON_TOKEN));
                            paciente.setDni(dni);

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


                            UsuariosStruct user = db.getUsuarioPorDNI(dni);
                            if(user==null){
                                db.guardarUsuario(dni,pwd,paciente.getNombre().toString());
                            }
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
                    Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();
                    dniTxt.setText("");
                    passTxt.setText("");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
            db.close();
        }

    }
}

