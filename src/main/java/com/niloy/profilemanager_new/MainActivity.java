package com.niloy.profilemanager_new;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), ProfileManager.class));
    }


    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), ProfileManager.class));
    }


}
