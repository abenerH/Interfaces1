package primero.angie.andrew.blabla.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import primero.angie.andrew.blabla.interfaces.Adapter.ComplaintsAdapter;
import primero.angie.andrew.blabla.interfaces.activities.complaintadd_activity;
import primero.angie.andrew.blabla.interfaces.fragments.denunciasFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    Context context = this;

    Button button;
    Button button2;
    EditText editText;
    EditText editText2;


    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Here", "0");
        super.onCreate(savedInstanceState);
        Log.i("Here", "1");
        setContentView(R.layout.activity_main);
        Log.i("Here", "2");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i("Here", "3");
        setSupportActionBar(toolbar);
        Log.i("Here", " 4 y 5");
        setNavigationViewListener();
        Log.i("Here", "6");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.i("Here", "7");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Log.i("Here", "8");
        navigationView.setNavigationItemSelectedListener(this);

        Log.i("Here", "9");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        boolean fragmentoSeleccionado = false;
        Class fragmentClass = null;

         /* //do somthing
                fragmentClass = ComplaintsAdapter.class;
                break;
            */

        switch (item.getItemId()) {

            case R.id.nav_denuncia:
                try {
                    Intent intent;
                    intent = new Intent(this, complaintadd_activity.class);
                    startActivity(intent);
                    break;
                } catch (Exception e) {
                    Log.i("Error", "blabla", e);
                }
                break;

            case R.id.nav_misdenuncias:
                fragmentClass = denunciasFragment.class;
                fragmentoSeleccionado = true;

                break;

        }
        if (fragmentoSeleccionado) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                Log.i("Still", "alive /// ");
                FragmentManager fragmentManager = getSupportFragmentManager();
                Log.i("Still", "alive /// ");
                fragmentManager.beginTransaction().replace(R.id.Contenedor, fragment).commit();
                Log.i("Still", "alive /// ");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        } else {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {


    }
}