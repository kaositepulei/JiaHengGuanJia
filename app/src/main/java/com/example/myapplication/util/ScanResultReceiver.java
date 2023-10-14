package com.example.myapplication.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScanResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String scanResult = intent.getStringExtra("SCAN_RESULT");
        // 在这里处理扫描结果
        Log.d("ScanResult", scanResult);
    }
}