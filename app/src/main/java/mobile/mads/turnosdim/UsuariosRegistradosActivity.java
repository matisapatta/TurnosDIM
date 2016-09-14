package mobile.mads.turnosdim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import database.DBManager;

public class UsuariosRegistradosActivity extends AppCompatActivity {

    private ListView list;
    private DBManager db;


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

    }
}
