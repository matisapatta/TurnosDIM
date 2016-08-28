package mobile.mads.turnosdim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import database.DBManager;


public class DetalleTurno extends AppCompatActivity {

    private TextView contentMedico;
    private TextView contentEspecialidad;
    private TextView contentFecha;
    private TextView contentCentro;
    private TextView contentConsultorio;
    private TextView contentCobertura;
    private TextView contentPreparacion;
    private DBManager db;
    private TurnosStruct turno;
    private String idTurno;
    private Button btnCalendar;
    private Button btnCancelarTurno;
    private Date dateObj;
    private String success;
    private Paciente paciente;
    private ArrayList<String> razonesCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_turno);

        contentMedico = (TextView)findViewById(R.id.contentMedico);
        contentEspecialidad = (TextView)findViewById(R.id.contentEspecialidad);
        contentFecha = (TextView)findViewById(R.id.contentFecha);
        contentCentro = (TextView)findViewById(R.id.contentCentro);
        contentConsultorio = (TextView)findViewById(R.id.contentConsultorio);
        contentCobertura = (TextView)findViewById(R.id.contentCobertura);
        contentPreparacion = (TextView)findViewById(R.id.contentPreparacion);
        btnCalendar = (Button)findViewById(R.id.btnSaveCalendar);
        btnCancelarTurno = (Button)findViewById(R.id.btnDelete);

        db = new DBManager(getApplicationContext());

        // Tomo el idturno de la activity pasada
        Intent i = getIntent();
        Bundle b = i.getExtras();
        idTurno = b.getString("IDTURNO");


    }

    @Override
    public void onStart(){
        super.onStart();
        turno = db.getTurno(idTurno);
        paciente = db.getPaciente(getApplicationContext());

        contentMedico.setText(turno.getMedico());
        contentEspecialidad.setText(turno.getEspecialidad());
        contentCentro.setText(turno.getCentro());
        contentConsultorio.setText(turno.getConsultorio());
        contentCobertura.setText(turno.getCobertura());
        contentPreparacion.setText(turno.getPreparacion());

        String s = turno.getFechaTurno()+" "+turno.getHoraTurno();
        contentFecha.setText(s);
        /*SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        try {
            dateObj = curFormater.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        dateObj = new Util().StringToDate(s,1);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(dateObj);
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal1.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra("endTime", cal1.getTimeInMillis()+30*60*1000);
                intent.putExtra("title", "Turno "+turno.getEspecialidad()+" con "+turno.getMedico());
                intent.putExtra("eventLocation", turno.getCentro());
                startActivity(intent);
            }
        });

        btnCancelarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_CANCELARTURNOMOTIVOS+
                        WSConstants.StringConstants.WS_ID_PACIENTE+paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+
                        paciente.getTokenPaciente();
                new HttpRequestTask().execute(url);
            }
        });


    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }


    public class HttpRequestTask extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(getApplicationContext());
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
                        razonesCancel = new ArrayList<>();
                        //JSONObject json_practicas;

                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            JSONArray jArray = jsonObject.getJSONArray("items");
                            for(int i=0;i<jArray.length();i++){
                                json_data = jArray.getJSONObject(i);

                                razonesCancel.add(json_data.getString(JSONConstants.JSON_DESCRIPCION));
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
