package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.activity.BaseActivity;
import com.example.myapplication.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐层状态栏和标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      /*  //隐藏标题栏
        getSupportActionBar().hide();*/ // 报错
        setContentView(R.layout.activity_main);
        //创建子线程
        Thread mThread=new Thread(){
            @Override
            public void run(){
                super.run();
                try{
                    sleep(2000);
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //启动线程
        mThread.start();
        }
    }