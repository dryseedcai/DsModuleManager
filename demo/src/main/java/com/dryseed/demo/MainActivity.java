package com.dryseed.demo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dryseed.demo.service.Service1;
import com.dryseed.dsmodulemanager.ModuleManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ModuleManager.getQYPageModel().clearMessageRedDot();

        //为了便于测试跨进程服务的效果
        //这里通过startService创建了3个进程，这几个服务本身无用。
        startService(new Intent(this, Service1.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //测试api
        //创建进程需要时间，这里延长1.5秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取服务
                    //ModuleManager.getQYPageModel().clearMessageRedDot();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1500);
    }
}
