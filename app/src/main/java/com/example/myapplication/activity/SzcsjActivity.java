package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SzcsjActivity extends AppCompatActivity {

    private TextView shezhishijian;

    private TextView shebeihao;

    private Button submit;

    private Button btn_cancel;
    private String chenghgao;
    private String newTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szcsj);
        shezhishijian = findViewById(R.id.shezhishijian);
        shebeihao = findViewById(R.id.shebeihao);
        submit = findViewById(R.id.submit);
        btn_cancel = findViewById(R.id.btn_cancel);
        Intent intent = getIntent();
        chenghgao = intent.getStringExtra("chenghao");
        shebeihao.setText(chenghgao);
        shezhishijian.setOnClickListener(new SzcsjsActivityOnClickListeber());
        submit.setOnClickListener(new SzcsjsActivityOnClickListeber());
        btn_cancel.setOnClickListener(new SzcsjsActivityOnClickListeber());
        SimpleDateFormat formatters = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatters.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDates = new Date(System.currentTimeMillis()); //获取当前时间
        newTimes = formatters.format(curDates);   //格式转换
        shezhishijian.setText(newTimes);
    }

    class SzcsjsActivityOnClickListeber implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.shezhishijian) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SzcsjActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(SzcsjActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(SzcsjActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(SzcsjActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(SzcsjActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(SzcsjActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(SzcsjActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(SzcsjActivity.this);
                secondPicker.setMinValue(1);
                secondPicker.setMaxValue(60);
                secondPicker.setWrapSelectorWheel(false);
                secondPicker.setValue(calendar.get(Calendar.SECOND));

                // 设置NumberPicker的布局参数为MATCH_PARENT，并设置layout_weight为1，使其占据LinearLayout的三分之一宽度
                LinearLayout.LayoutParams yearPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                yearPicker.setLayoutParams(yearPickerParams);
                LinearLayout.LayoutParams monthPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                monthPicker.setLayoutParams(monthPickerParams);
                LinearLayout.LayoutParams dayPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(dayPickerParams);
                LinearLayout.LayoutParams hourPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(dayPickerParams);
                LinearLayout.LayoutParams minutePickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(minutePickerParams);
                LinearLayout.LayoutParams secondPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(secondPickerParams);


                // 将NumberPicker添加到布局中
                layout.addView(yearPicker);
                layout.addView(monthPicker);
                layout.addView(dayPicker);
                layout.addView(hourPicker);
                layout.addView(minutePicker);
                layout.addView(secondPicker);

                // 设置对话框的标题和布局
                TextView titleView = new TextView(SzcsjActivity.this);
                titleView.setText("请选择充后检查时间");
                titleView.setGravity(Gravity.CENTER);
                titleView.setTextSize(20);
                builder.setCustomTitle(titleView)
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 点击确定按钮后的逻辑处理
                                int selectedYear = yearPicker.getValue();
                                int selectedMonth = monthPicker.getValue();
                                int selectedDay = dayPicker.getValue();
                                int selectedHour = hourPicker.getValue();
                                int selectedMinute = minutePicker.getValue();
                                int selectedSecond = secondPicker.getValue();
                                // 在这里可以使用选中的年份和月份进行相关操作
                                shezhishijian.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                // 设置对话框的显示位置为屏幕底部
                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.gravity = Gravity.BOTTOM;
                    window.setAttributes(layoutParams);
                }
                dialog.show();
            }
            if (view.getId() == R.id.submit){
                JSONObject data = new JSONObject();
                data.put("device_id", chenghgao);
                data.put("Time", newTimes);
                // 创建一个URL对象
                URL url = null;
                try {
                    url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetScaleList");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                // 创建一个StringBuilder对象来存储响应数据
                StringBuilder response = new StringBuilder();
                // 创建一个HttpURLConnection对象
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 设置请求方法为POST
                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                }
                // 设置连接超时时间
                connection.setConnectTimeout(5000);
                // 设置读取超时时间
                connection.setReadTimeout(5000);
                // 允许输入流和输出流
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // 设置请求体的内容类型
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
                // 获取输出流
                OutputStream outputStream = null;
                try {
                    outputStream = connection.getOutputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                // 将请求体数据写入输出流
                StringBuffer buffer = new StringBuffer();
                buffer.append("data");
                buffer.append('=');
                Log.e("jhkj", String.valueOf(data)+"************************");
                buffer.append(data);
                try {
                    writer.write(buffer.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 建立连接
                try {
                    connection.connect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 获取响应码
                int responseCode = 0;
                try {
                    responseCode = connection.getResponseCode();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 获取响应数据流
                    InputStream inputStream = null;
                    try {
                        inputStream = connection.getInputStream();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // 创建一个BufferedReader对象来读取响应数据
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        response.append(reader.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("jhkj", response.toString());
                    JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                }
            }
            if (view.getId() == R.id.btn_cancel){
                Intent intent = new Intent(SzcsjActivity.this, ShebeikongzhiActivity.class);
                startActivity(intent);
            }
        }

        }
}