package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.DataBase.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class yhxxActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText xingming;
    private EditText shenfenzhenghaoma;
    private EditText danweibianhao;
    private EditText lianxidianhua;
    private EditText yuangongbianhao;
    private EditText zhengshubianhao;
    private TextView fazhengriqi;
    private TextView youxiaoriqi;
    private Button btn_cancel;
    private Button submit;

    private AlertDialog dialog;
    private String  userData;
    private String  LoginName;
    private String  LoginPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhxx);
        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("userData");
        JSONObject dataObject = new JSONObject(hashMap);
        xingming = findViewById(R.id.xingming);
        shenfenzhenghaoma = findViewById(R.id.shenfenzhenghaoma);
        danweibianhao = findViewById(R.id.danweibianhao);
        lianxidianhua = findViewById(R.id.lianxidianhua);
        yuangongbianhao = findViewById(R.id.yuangongbianhao);
        zhengshubianhao = findViewById(R.id.zhengshubianhao);
        fazhengriqi = findViewById(R.id.fazhengriqi);
        youxiaoriqi = findViewById(R.id.youxiaoriqi);
        btnBack = findViewById(R.id.btn_back);
        btn_cancel = findViewById(R.id.btn_cancel);
        submit = findViewById(R.id.submit);
        btnBack.setOnClickListener(new YhxxActivityOnClickListeber());
        btn_cancel.setOnClickListener(new YhxxActivityOnClickListeber());
        fazhengriqi.setOnClickListener(new YhxxActivityOnClickListeber());
        youxiaoriqi.setOnClickListener(new YhxxActivityOnClickListeber());
        submit.setOnClickListener(new YhxxActivityOnClickListeber());
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 获取存储的数据
        userData = sharedPreferences.getString("userData", "");
        Log.e("jhkj",userData);
        // 将字符串解析为JSONObject
        JSONObject jsonObject = JSON.parseObject(userData);
        // 提取LoginName和LoginPass的值
        LoginName = jsonObject.getString("LoginName");
        LoginPass = jsonObject.getString("LoginPass");
        Log.e("jhkj",LoginName);
        Log.e("jhkj",LoginPass);
        indata(dataObject);
    }

    private class YhxxActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(yhxxActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.btn_cancel) {
                Intent in = new Intent(yhxxActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.fazhengriqi) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(yhxxActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(yhxxActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(yhxxActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(yhxxActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(yhxxActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

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


                // 将NumberPicker添加到布局中
                layout.addView(yearPicker);
                layout.addView(monthPicker);
                layout.addView(dayPicker);

                // 设置对话框的标题和布局
                TextView titleView = new TextView(yhxxActivity.this);
                titleView.setText("请选择年月日");
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
                                // 在这里可以使用选中的年份和月份进行相关操作
                                fazhengriqi.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
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
            if (view.getId() == R.id.youxiaoriqi) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(yhxxActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(yhxxActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(yhxxActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(yhxxActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(yhxxActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

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


                // 将NumberPicker添加到布局中
                layout.addView(yearPicker);
                layout.addView(monthPicker);
                layout.addView(dayPicker);

                // 设置对话框的标题和布局
                TextView titleView = new TextView(yhxxActivity.this);
                titleView.setText("请选择年月日");
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
                                // 在这里可以使用选中的年份和月份进行相关操作
                                youxiaoriqi.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
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
            if (view.getId() == R.id.submit) {
                JSONObject data = new JSONObject();
                data.put("key", LoginName);
                data.put("LoginName", UUID.randomUUID().toString());
                data.put("LoginPass", LoginPass);
                data.put("LoginName", LoginName);
                data.put("EmployeePersonalCode", shenfenzhenghaoma.getText().toString());
                data.put("EmployeeName", xingming.getText().toString());
                data.put("UserNum", yuangongbianhao.getText().toString());
                data.put("EmployeeTel", lianxidianhua.getText().toString());
                data.put("EmployeeCode", danweibianhao.getText().toString());
                data.put("Certificate", zhengshubianhao.getText().toString());
                data.put("CertificateDate", fazhengriqi.getText().toString());
                data.put("ExpireDate", youxiaoriqi.getText().toString());
                // 创建一个URL对象
                URL url = null;
                try {
                    url = new URL("http://a.zbjiaheng.com/WebApi/Centre/PersonAdd");
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
                    Log.e("jhkj", response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                    // 解析JSON数据
                    JSONArray jsonArray = JSONArray.parseArray(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                    JSONObject jsonObjects = jsonArray.getJSONObject(0);
                    String msg = jsonObjects.getString("msg");
                    String status = jsonObjects.getString("status");
                    if (status.equals("1")) {
                        Toast.makeText(yhxxActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(yhxxActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(yhxxActivity.this, "修改成功请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(yhxxActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    public void indata(JSONObject dataObject) {
        xingming.setText(dataObject.getString("EmployeeName"));
        shenfenzhenghaoma.setText(dataObject.getString("EmployeePersonalCode"));
        danweibianhao.setText(dataObject.getString("EmployeeCode"));
        lianxidianhua.setText(dataObject.getString("EmployeeTel"));
        yuangongbianhao.setText(dataObject.getString("UserNum"));
        zhengshubianhao.setText(dataObject.getString("Certificate"));
        fazhengriqi.setText(dataObject.getString("CertificateDate"));
        youxiaoriqi.setText(dataObject.getString("ExpireDate"));
    }
}