package mobile.mads.turnosdim;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class EntryActivity extends AppCompatActivity {

    private Button loginBtn;
    private Paciente paciente;
    private EditText dniTxt;
    private EditText passTxt;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        dniTxt=(EditText)findViewById(R.id.loginDNI);
        passTxt=(EditText)findViewById(R.id.loginPassword);
    }

    @Override
    public void onStart(){
        super.onStart();
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                url = WSConstants.StringConstants.wsUrl+WSConstants.StringConstants.wsComandoLogin+
                        WSConstants.StringConstants.wsDni+dniTxt.getText()+WSConstants.StringConstants.wsPass+
                        passTxt.getText()+WSConstants.StringConstants.wsFormato;
                new HttpRequestTask().execute();

            }
        });
    }

    public class HttpRequestTask extends AsyncTask<Void , Void, Paciente> {


        @Override
        protected Paciente doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                paciente = restTemplate.getForObject(url, Paciente.class);
                return paciente;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;


        }

        @Override
        protected void onPostExecute(Paciente paciente) {
            if(paciente.getSuccess().equals("1")){
                Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
}
