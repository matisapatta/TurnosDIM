package mobile.mads.turnosdim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

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

        contentMedico.setText(turno.getMedico());
        contentEspecialidad.setText(turno.getEspecialidad());
        contentCentro.setText(turno.getCentro());
        contentConsultorio.setText(turno.getConsultorio());
        contentCobertura.setText(turno.getCobertura());
        contentPreparacion.setText(turno.getPreparacion());

        String s = turno.getFechaTurno()+" "+turno.getHoraTurno();
        contentFecha.setText(s);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "A Test Event from android app");
                startActivity(intent);
            }
        });


    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
