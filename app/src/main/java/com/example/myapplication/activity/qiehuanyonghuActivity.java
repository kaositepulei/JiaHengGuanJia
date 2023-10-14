package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.myapplication.R;
import com.example.myapplication.entity.Login;
import com.example.myapplication.entity.LoginResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class qiehuanyonghuActivity extends AppCompatActivity {


    private JSONObject userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiehuanyonghu);
        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("userData");
        userData = new JSONObject(hashMap);
        try {
            JSONArray userListArray = GetUserList(userData.getString("GSID"));
            LinearLayout linearLayout = findViewById(R.id.user);
            for (int i = 0; i < userListArray.size(); i++) {
                JSONObject jsonObject = userListArray.getJSONObject(i);
                String Pass = md5jiemi(jsonObject.getString("password"));
                Log.e("jhkj",Pass);
                jsonObject.put("password",Pass );
                JSONArray jsonArray = getUserData(jsonObject);
                JSONObject dataObject = jsonArray.getJSONObject(0);
                String password = dataObject.getString("UserName");
                String account = jsonObject.getString("account");
                // 创建accountText
                TextView accountText = new TextView(this);
                accountText.setText("用户名：" + account);
                accountText.setTextSize(16);
                accountText.setTextColor(Color.BLACK);
                // 创建一个新的passwordText
                TextView passwordText = new TextView(this);
                passwordText.setText("账号：" + password);
                passwordText.setTextSize(16);
                passwordText.setTextColor(Color.BLACK);
                //创建分割线
                View lineView = new View(this);
                lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                lineView.setBackgroundColor(Color.BLACK);
                //创建button
                Button button = new Button(this);
                button.setText("切换为此账号");
                button.setTextSize(16);
                button.setTextColor(Color.BLACK);
                button.setBackgroundColor(Color.BLUE);
                //添加到LinearLayout中
                linearLayout.addView(accountText);
                linearLayout.addView(passwordText);
                linearLayout.addView(lineView);
                linearLayout.addView(button);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONArray GetUserList(String GSID) throws JSONException, IOException {
        JSONArray userListArray = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("id", GSID);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetUserList");
        // 创建一个StringBuilder对象来存储响应数据
        StringBuilder response = new StringBuilder();
        // 创建一个HttpURLConnection对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置请求方法为POST
        connection.setRequestMethod("POST");
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
            // 获取 SharedPreferences 对象
            SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
            // 获取存储的数据
            String userData = sharedPreferences.getString("userData", "");
            // 解析JSON数据
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            userListArray = jsonObject.getJSONArray("Data");
        }
        return userListArray;
    }

    private JSONArray getUserData(JSONObject data) throws JSONException, IOException {
            JSONArray dataArray =new JSONArray();
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetUserData");
            // 创建一个StringBuilder对象来存储响应数据
            StringBuilder response = new StringBuilder();
            // 创建一个HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为POST
            connection.setRequestMethod("POST");
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
                // 解析JSON数据
                JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                dataArray = jsonObject.getJSONArray("Data");
        }
            return dataArray;
    }

    public  String md5jiemi(String Pass) {
        String input = Pass;
        StringBuilder sb = new StringBuilder();
        try {
            // 创建MD5哈希算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算输入的MD5哈希值
            byte[] hash = md.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            // 打印MD5哈希值
            System.out.println("MD5 Hash: " + sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}