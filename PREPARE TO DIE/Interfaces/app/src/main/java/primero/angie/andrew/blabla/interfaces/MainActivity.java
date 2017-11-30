package primero.angie.andrew.blabla.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import primero.angie.andrew.blabla.interfaces.activities.LoginActivity;
import primero.angie.andrew.blabla.interfaces.activities.complaintadd_activity;
import primero.angie.andrew.blabla.interfaces.fragments.denunciasFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final int REQUEST_CODE = 123;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNavigationViewListener();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Reload();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    
        // Navigation views control
        Fragment fragment = null;
        boolean fragmentoSeleccionado = false;
        Class fragmentClass = null;

        switch (item.getItemId()) {

            case R.id.nav_complaint:

                    Intent intent0;
                    intent0 = new Intent(this, complaintadd_activity.class);
                    startActivityForResult(intent0, REQUEST_CODE);
                    break;

            case R.id.nav_mycomplaints:
                fragmentClass = denunciasFragment.class;
                fragmentoSeleccionado = true;

                break;

            case R.id.nav_close_s:
                Intent intent;
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        if (fragmentoSeleccionado) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.Contenedor, fragment).commit();
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
        // Inflate the menu and  adds items to the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
          automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml. */
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

            if(requestCode == REQUEST_CODE){
                if (resultCode == Activity.RESULT_OK) {
                    Reload();
                }
            }
    }

    private void Reload(){
        Class fragmentClass = null;
        fragmentClass = denunciasFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Contenedor, fragment).commit();
    }
}
