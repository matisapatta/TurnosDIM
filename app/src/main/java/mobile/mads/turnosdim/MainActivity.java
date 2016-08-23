package mobile.mads.turnosdim;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        setupDrawerContent(nvDrawer);
        selectedFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
             .replace(R.id.content_main, selectedFragment).commit();
        db = new DBManager(this);



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
                fragmentClass = MainFragment.class;
        }

        try {
            selectedFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, selectedFragment).commit();

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


}

