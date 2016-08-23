package mobile.mads.turnosdim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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


    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
