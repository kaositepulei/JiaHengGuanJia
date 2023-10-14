package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class StettingActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private LinearLayout btnLogOut;
    private LinearLayout btnChangePass;
    private AlertDialog dialog;
    private String userData;
    /*private Button passTrue;
    private Button passCancel;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stetting);
        btnBack = findViewById(R.id.btn_back);
        btnLogOut = findViewById(R.id.log_out);
        btnChangePass = findViewById(R.id.btn_change_pass);
        /*passTrue = findViewById(R.id.pass_cancel);
        passTrue = findViewById(R.id.pass_cancel);*/
        btnBack.setOnClickListener(new StettingActivityOnClickListeber());
        btnLogOut.setOnClickListener(new StettingActivityOnClickListeber());
        btnChangePass.setOnClickListener(new StettingActivityOnClickListeber());
    }

    class StettingActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(StettingActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.log_out) {
                Intent in = new Intent(StettingActivity.this, LoginActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.btn_change_pass) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(StettingActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(StettingActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);




                EditText editText = new EditText(StettingActivity.this);
                editText.setHint("请输入修改密码");

                layout.addView(editText);




                // 设置对话框的标题和布局
                TextView titleView = new TextView(StettingActivity.this);
                titleView.setText("修改登录密码");
                titleView.setGravity(Gravity.CENTER);
                titleView.setTextSize(20);
                builder.setCustomTitle(titleView)
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 点击确定按钮后的逻辑处理
                                String NewLoginPass = String.valueOf(editText.getText());
                                Log.e("jhkj",NewLoginPass);
                                // 获取 SharedPreferences 对象
                                SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
                                // 获取存储的数据
                                userData = sharedPreferences.getString("userData", "");
                                Log.e("jhkj",userData);
                                // 将字符串解析为JSONObject
                                JSONObject jsonObject = JSON.parseObject(userData);
                                // 提取LoginName和LoginPass的值
                                String LoginName = jsonObject.getString("LoginName");
                                String LoginPass = jsonObject.getString("LoginPass");
                                JSONObject data = new JSONObject();
                                data.put("LoginName", LoginName);
                                data.put("LoginPass", LoginPass);
                                data.put("NewLoginPass", NewLoginPass);
                                // 创建一个URL对象
                                URL url = null;
                                try {
                                    url = new URL("http://a.zbjiaheng.com/WebApi/Centre/setPassword");
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
                                        Intent intent = new Intent(StettingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(StettingActivity.this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(StettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}