package com.kavsoftware.kaveer.yourturf.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kavsoftware.kaveer.yourturf.Fragment.Nomination;
import com.kavsoftware.kaveer.yourturf.Fragment.RaceCard;
import com.kavsoftware.kaveer.yourturf.R;
import com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreenViewModel;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetFullScreenON();
        setContentView(R.layout.activity_main);


        DisplayDrawerOn();
        NavigationViewON();

    }

    @Override
    protected void onStart() {
        super.onStart();

        Initialization();

        if(isNetworkConnected()){
            new GetHomeScreenFromApi().execute();
        }
        else {
            Toast messageBox = Toast.makeText(this , "No internet connection" , Toast.LENGTH_LONG);
            messageBox.show();
        }
    }

    private void Initialization() {
        bar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private class GetHomeScreenFromApi extends AsyncTask<Void, Void, HomeScreenViewModel> {
        @Override
        protected HomeScreenViewModel doInBackground(Void... params) {
            try {
                final String url = getBaseContext().getResources().getString(R.string.GetHomeScreenFromApEndPoint); //endpoint in string.xml
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HomeScreenViewModel home = restTemplate.getForObject(url, HomeScreenViewModel.class);
                return home;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                Toast messageBox = Toast.makeText(MainActivity.this , e.getMessage(), Toast.LENGTH_LONG);
                messageBox.show();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar.setVisibility(View.VISIBLE);

            Toast messageBox = Toast.makeText(MainActivity.this , "Loading please wait.." , Toast.LENGTH_LONG);
            messageBox.show();
        }

        @Override
        protected void onPostExecute(HomeScreenViewModel home) {

            bar.setVisibility(View.GONE);

            if (home.getIsRaceCardAvailable() == true){
                RaceCard fragment = new  RaceCard();
                android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
                fmTransaction.replace(R.id.MainFrameLayout, fragment);
                fmTransaction.commit();
            }
            else {
                Nomination fragment = new  Nomination();
                android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
                fmTransaction.replace(R.id.MainFrameLayout, fragment);
                fmTransaction.commit();
            }
        }

    }

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }

    private void NavigationViewON() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void DisplayDrawerOn() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void SetFullScreenON() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_RaceCard) {
            RaceCard fragment = new  RaceCard();
            android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
        } else if (id == R.id.nav_Nomination) {
            Nomination fragment = new  Nomination();
            android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
