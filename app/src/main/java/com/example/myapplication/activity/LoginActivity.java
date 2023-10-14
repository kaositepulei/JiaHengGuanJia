package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.api.Api;
import com.example.myapplication.api.ApiConfig;
import com.example.myapplication.api.TtitCallback;
import com.example.myapplication.entity.LoginResponse;
import com.example.myapplication.util.SetCustomDensity;
import com.example.myapplication.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private EditText jhAccount;
    private EditText jhPassWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        jhAccount = findViewById(R.id.input_account);
        jhPassWord = findViewById(R.id.input_password);

        btnLogin.setOnClickListener(new LoginActivityOnClickListeber());
        jhAccount.setOnClickListener(new LoginActivityOnClickListeber());
        jhPassWord.setOnClickListener(new LoginActivityOnClickListeber());
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "无法继续返回", Toast.LENGTH_SHORT).show();
    }




    class LoginActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_login) {
                String account = jhAccount.getText().toString().trim();
                String pwd = jhPassWord.getText().toString().trim();
                try {
                    login(account, pwd);
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void login(String account, String pwd) throws JSONException, IOException {
        if (StringUtils.isEmpty(account)) {
            Log.e("jhkj：", account);
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            Log.e("jhkj：", pwd);
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject data = new JSONObject();
        data.put("LoginName", account);
        data.put("LoginPass", pwd);


        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetLogin");
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
            Log.e("jhkj", response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            JSONArray jsonArray = JSONArray.parseArray(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            String status = (String) jsonArray.getJSONObject(0).get("status");
            String msg = (String) jsonArray.getJSONObject(0).get("msg");
            Log.e("jhkj", status);
            Log.e("jhkj", msg);
            if (status.equals("0")) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            if (status.equals("1")) {
                // 获取 SharedPreferences 对象
                SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
                // 获取 SharedPreferences.Editor 对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 存储数据
                editor.putString("userData", String.valueOf(data));
                // 提交数据
                editor.apply();
                Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }


    }
}