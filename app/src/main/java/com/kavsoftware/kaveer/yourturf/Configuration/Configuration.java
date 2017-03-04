package com.kavsoftware.kaveer.yourturf.Configuration;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by kaveer on 3/4/2017.
 */

public class Configuration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public void FullScreen(){

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
