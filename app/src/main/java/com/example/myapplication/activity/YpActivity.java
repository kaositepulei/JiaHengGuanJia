package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class YpActivity extends AppCompatActivity {

    private TextView yapingriqi;
    private TextView daoqiriqi;
    private EditText kehu;
    private Spinner qipingguige;
    private Button btn_submit;
    private String kehuhq;
    private String Cus_id;
    private EditText yajin;
    private EditText kaihufei;
    private EditText zhejiafei;
    private EditText yingbuchajia;
    private EditText yapingshuliang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yp);


        Intent intent = getIntent();
        kehuhq = intent.getStringExtra("kehu");
        Cus_id = intent.getStringExtra("Cus_id");
        Log.e("jhkj", intent.getStringExtra("Cus_id")+"kwhuhao");


        yapingriqi = findViewById(R.id.yapingriqi);
        daoqiriqi = findViewById(R.id.daoqiriqi);
        qipingguige = findViewById(R.id.qipingguige);
        kehu = findViewById(R.id.kehu);
        btn_submit = findViewById(R.id.btn_submit);
        yajin = findViewById(R.id.yajin);
        kaihufei = findViewById(R.id.kaihufei);
        zhejiafei = findViewById(R.id.zhejiafei);
        yingbuchajia = findViewById(R.id.yingbuchajia);
        yapingshuliang = findViewById(R.id.yapingshuliang);
        kehu.setText(kehuhq);
        yapingriqi.setOnClickListener(new YpActivityOnClickListeber());
        daoqiriqi.setOnClickListener(new YpActivityOnClickListeber());
        btn_submit.setOnClickListener(new YpActivityOnClickListeber());

        SimpleDateFormat formatters = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatters.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDates = new Date(System.currentTimeMillis()); //获取当前时间
        String newTimes = formatters.format(curDates);   //格式转换
        yapingriqi.setText(newTimes);

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
        String newTime = formatter.format(curDate);   //格式转换
        daoqiriqi.setText(newTime);

        try {
            getqpgg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    class YpActivityOnClickListeber implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.yapingriqi) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(YpActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(YpActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(YpActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(YpActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(YpActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(YpActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(YpActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(YpActivity.this);
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
                TextView titleView = new TextView(YpActivity.this);
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
                                yapingriqi.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
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
            if (view.getId() == R.id.daoqiriqi){
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(YpActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(YpActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(YpActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(YpActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(YpActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(YpActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(YpActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(YpActivity.this);
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
                TextView titleView = new TextView(YpActivity.this);
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
                                daoqiriqi.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
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
            if (view.getId() == R.id.btn_submit){
                try {
                    yp();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private void getqpgg() throws IOException {
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 获取存储的数据
        String userData = sharedPreferences.getString("userData", "");
        // 将字符串解析为JSONObject
        JSONObject jsonObject = JSON.parseObject(userData);
        // 提取LoginName和LoginPass的值
        String LoginName = jsonObject.getString("LoginName");
        String LoginPass = jsonObject.getString("LoginPass");

        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        if (userData == null){
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }else {
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetGsXh");
            // 创建一个StringBuilder对象来存储响应数据
            StringBuilder response = new StringBuilder();
            // 创建一个HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为POST
            connection.setRequestMethod("GET");
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
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            // 将请求体数据写入输出流
            StringBuffer buffer = new StringBuffer();
            buffer.append("data");
            buffer.append('=');
            Log.e("jhkj", String.valueOf(data));
            buffer.append(data);
            writer.write(buffer.toString());
            writer.flush();
            writer.close();
            // 建立连接
            connection.connect();
            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取响应数据流
                InputStream inputStream = connection.getInputStream();
                // 创建一个BufferedReader对象来读取响应数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                response.append(reader.readLine());
                Log.e("jhkj", response.toString());
                JSONObject jsonObjects = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                JSONArray dataArray = jsonObjects.getJSONArray("data");
                List<String> qpggList = new ArrayList<>();
                for (int i = 0; i < dataArray.size(); i++) {
                    JSONObject qpggObject = dataArray.getJSONObject(i);
                    qpggList.add(qpggObject.getString("Value"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(YpActivity.this, android.R.layout.simple_spinner_item, qpggList){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setGravity(Gravity.END); // 设置文字靠右对齐
                        return textView;
                    }
                };
                qipingguige.setAdapter(adapter);
            }
        }
    }

    private void yp() throws IOException {
        //判断日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date yapingriqis = dateFormat.parse(yapingriqi.getText().toString());
            Date daoqiriqis = dateFormat.parse(daoqiriqi.getText().toString());
            int comparison = yapingriqis.compareTo(daoqiriqis);

            if (comparison >= 0) {
                Toast.makeText(this, "到期日期必须大于押瓶日期", Toast.LENGTH_SHORT).show();
            }
            else if (yajin.getText().toString().isEmpty()){
                Toast.makeText(this, "请输入押金", Toast.LENGTH_SHORT).show();
            }else {
                // 获取 SharedPreferences 对象
                SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
                // 获取存储的数据
                String userData = sharedPreferences.getString("userData", "");
                // 将字符串解析为JSONObject
                JSONObject jsonObject = JSON.parseObject(userData);
                // 提取LoginName和LoginPass的值
                String LoginName = jsonObject.getString("LoginName");
                String LoginPass = jsonObject.getString("LoginPass");

                JSONObject data = new JSONObject();
                data.put("Cus_Name", kehuhq);
                data.put("Cus_id", Cus_id);
                data.put("Gs_Xh_Name", qipingguige.getSelectedItem().toString());
                if (qipingguige.getSelectedItem().toString().equals("2.00")) {
                    data.put("Gs_Xh", "19");
                }
                if (qipingguige.getSelectedItem().toString().equals("5.00")) {
                    data.put("Gs_Xh", "20");
                }
                if (qipingguige.getSelectedItem().toString().equals("10.00")) {
                    data.put("Gs_Xh", "21");
                }
                if (qipingguige.getSelectedItem().toString().equals("15.00")) {
                    data.put("Gs_Xh", "22");
                }
                if (qipingguige.getSelectedItem().toString().equals("50.00")) {
                    data.put("Gs_Xh", "23");
                }
                data.put("YaJin", yajin.getText().toString());
                data.put("KaiHuFei", kaihufei.getText().toString());
                data.put("ZheKouFei", zhejiafei.getText().toString());
                data.put("BuChaJia", yingbuchajia.getText().toString());
                data.put("GsCount", yapingshuliang.getText().toString());
                data.put("Mortgage_Date", yapingriqi.getText().toString());
                data.put("Due_Date", daoqiriqi.getText().toString());
                data.put("key", UUID.randomUUID().toString());
                data.put("LoginName", LoginName);
                data.put("LoginPass", LoginPass);
                if (userData == null){
                    Intent in = new Intent(this, LoginActivity.class);
                    startActivity(in);
                }else {
                    // 创建一个URL对象
                    URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/YaGsSave");
                    // 创建一个StringBuilder对象来存储响应数据
                    StringBuilder response = new StringBuilder();
                    // 创建一个HttpURLConnection对象
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 设置请求方法为POST
                    connection.setRequestMethod("GET");
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
                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    // 将请求体数据写入输出流
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("data");
                    buffer.append('=');
                    Log.e("jhkj", String.valueOf(data)+"************************");
                    buffer.append(data);
                    writer.write(buffer.toString());
                    writer.flush();
                    writer.close();
                    // 建立连接
                    connection.connect();
                    // 获取响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // 获取响应数据流
                        InputStream inputStream = connection.getInputStream();
                        // 创建一个BufferedReader对象来读取响应数据
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        response.append(reader.readLine());
                        Log.e("jhkj", response.toString());
                        JSONArray jsonArray = JSON.parseArray(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                        JSONObject jsonObjects = jsonArray.getJSONObject(0);
                        String msg = jsonObjects.getString("msg");
                        String status = jsonObjects.getString("status");
                        if (status.equals("1")) {
                            // 创建一个AlertDialog.Builder对象
                            AlertDialog.Builder builder = new AlertDialog.Builder(YpActivity.this);
                            // 创建一个LinearLayout作为对话框的布局
                            LinearLayout layout = new LinearLayout(YpActivity.this);
                            layout.setOrientation(LinearLayout.VERTICAL);
                            TextView textView = new TextView(YpActivity.this);
                            textView.setText(msg);
                            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textView.setTextSize(25);
                            layout.addView(textView);
                            // 设置对话框的标题和布局
                            TextView titleView = new TextView(YpActivity.this);
                            titleView.setText("押瓶结果");
                            titleView.setGravity(Gravity.CENTER);
                            titleView.setTextSize(20);
                            builder.setCustomTitle(titleView)
                                    .setView(layout)
                                    .setPositiveButton("选择其他客户押瓶", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(YpActivity.this, XzkhActivity.class);
                                            intent.putExtra("jrjm","yp");
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("进入配送流程", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Intent intent = new Intent(YpActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNeutralButton("继续押瓶", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(YpActivity.this, YpActivity.class);
                                            intent.putExtra("kehu",kehuhq);
                                            intent.putExtra("Cus_id",Cus_id);
                                            startActivity(intent);
                                        }
                                    });
                            // 创建并显示对话框
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}