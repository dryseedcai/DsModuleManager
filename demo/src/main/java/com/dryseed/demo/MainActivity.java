package com.dryseed.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dryseed.dsmodulemanager.ModuleManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ModuleManager.getQYPageModel().clearMessageRedDot();
    }
}
