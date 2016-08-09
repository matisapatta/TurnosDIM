package mobile.mads.turnosdim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        loginBtn=(Button)findViewById(R.id.loginBtn);
    }

    @Override
    public void onStart(){
        super.onStart();
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
