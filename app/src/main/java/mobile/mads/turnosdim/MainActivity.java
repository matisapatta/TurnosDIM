package mobile.mads.turnosdim;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import database.DBManager;
import layout.LocationsFragment;
import layout.MainFragment;
import layout.NuevoTurnoFragment;
import layout.TurnosFragment;
import layout.UserSettingsFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,
        LocationsFragment.OnFragmentInteractionListener,
        NuevoTurnoFragment.OnFragmentInteractionListener,
        TurnosFragment.OnFragmentInteractionListener,
        UserSettingsFragment.OnFragmentInteractionListener {

    // Variables
    private Fragment selectedFragment = null;
    private Class fragmentClass;
    private boolean isDrawerLocked = false;
    private DBManager db;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ProgressDialog progressDialog;
    private String url;
    private String success;
    private Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Deshabilito esto hasta que volvamos a poner el boton
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        setupDrawerContent(nvDrawer);
        db = new DBManager(this);
        paciente = new Paciente();
        paciente = db.getPaciente(this);
        url =    WSConstants.StringConstants.WS_URL+WSConstants.StringConstants.WS_COMANDO_MISDATOS+ WSConstants.StringConstants.WS_ID_PACIENTE+
                paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+paciente.getTokenPaciente()+
                WSConstants.StringConstants.WS_FORMATO;
        new HttpRequestTask().execute();

    }

    @Override
    public void onStart(){
        super.onStart();
        selectedFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, selectedFragment).commit();
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        int id = menuItem.getItemId();
        switch(id) {
            case R.id.nav_turnos:
                // Carga el fragment de mis turnos
                fragmentClass = TurnosFragment.class;
                break;
            case R.id.nav_exams:
                //Carga el fragment de nuevos turnos
                fragmentClass = NuevoTurnoFragment.class;
                break;
            case R.id.nav_locations:
                // Carga el fragment de centros de atención
                fragmentClass = LocationsFragment.class;
                break;
            case R.id.nav_settings:
                // Carga el fragment de configuraciones de usuario
                fragmentClass = UserSettingsFragment.class;
                break;
            case R.id.nav_endSession:

                db.deleteAll();
                db.close();
                Intent i = new Intent(this.getApplicationContext(),EntryActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                fragmentClass = LocationsFragment.class;
        }

        try {
            selectedFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, selectedFragment)
                .addToBackStack(null).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawer(GravityCompat.START);
        //mDrawer.closeDrawers();

    }
    @Override
    public void onFragmentInteraction(){

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.deleteTurnos();
        db.close();
    }


    public class HttpRequestTask extends AsyncTask<Void , Void, String> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute()
        {
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.auth));
//            progressDialog.show();
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
                            paciente.setCobertura(jsonObject.getString(JSONConstants.JSON_COBERTURA2));
                            paciente.setEmail(jsonObject.getString(JSONConstants.JSON_EMAIL));
                            paciente.setFnac(jsonObject.getString(JSONConstants.JSON_FECHA_NAC));
                            paciente.setSexo(jsonObject.getString(JSONConstants.JSON_SEXO));
                            paciente.setTel(jsonObject.getString(JSONConstants.JSON_TEL));
                            paciente.setTelad(jsonObject.getString(JSONConstants.JSON_TEL_AD));
                            paciente.setPlan(jsonObject.getString(JSONConstants.JSON_PLAN));

                            db.updatePaciente(paciente.getId(),paciente);
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
//            progressDialog.dismiss();
            if(!success.equals("1")){
                Toast.makeText(getApplicationContext(), string,Toast.LENGTH_LONG).show();
            }
            //db.close();
        }

    }

}

