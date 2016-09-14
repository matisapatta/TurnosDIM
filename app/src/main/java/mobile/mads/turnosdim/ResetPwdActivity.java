package mobile.mads.turnosdim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPwdActivity extends AppCompatActivity {

    private Button reestablecer;
    private EditText dniTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        reestablecer=(Button)findViewById(R.id.reestablecerBtn);
        dniTxt=(EditText)findViewById(R.id.resetDNI);
    }

    @Override
    public void onStart(){
        super.onStart();
        reestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dniTxt.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Por favor ingrese un DNI",Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(getApplicationContext(),"Se ha reestablecido la contraseña para el DNI "+dniTxt.getText().toString()+". Se ha enviado un correo electrónico"+
                        " al email registrado con las instrucciones",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ResetPwdActivity.this,EntryActivity.class);
                        startActivity(i);
                    }
                }
        });
    }
}
