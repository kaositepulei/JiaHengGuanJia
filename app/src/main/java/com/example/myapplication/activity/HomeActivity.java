package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
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

public class HomeActivity extends AppCompatActivity {

    private ImageButton btnSet;
    private ImageButton btnScdzbs;
    private ImageButton qpda;
    private ImageButton tjxp;
    private ImageButton czqjc;
    private ImageButton czjl;
    private ImageButton qpps;
    private ImageButton yp;
    private ImageButton rhaj;
    private ImageButton gyqtcz;
    private ImageButton czhjc;
    private ImageButton shebeikongzhi;
    private String  userData;
    private TextView caozuoyuan;
    private TextView ruzhishijian;
    private TextView lianxidianhua;
    private TextView suoshuqizhan;
    private JSONObject dataObject;
    private LinearLayout yonghuxinxi;
    private JSONArray dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)  throws JSONException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSet = findViewById(R.id.btn_set);
        btnScdzbs = findViewById(R.id.btn_scdzbs);
        caozuoyuan = findViewById(R.id.caozuoyuan);
        ruzhishijian = findViewById(R.id.ruzhishijian);
        lianxidianhua = findViewById(R.id.lianxidianhua);
        suoshuqizhan = findViewById(R.id.suoshuqizhan);
        yonghuxinxi = findViewById(R.id.yonghuxinxi);
        yp = findViewById(R.id.yp);
        rhaj = findViewById(R.id.rhaj);
        qpda = findViewById(R.id.qpda);
        tjxp = findViewById(R.id.tjxp);
        czqjc = findViewById(R.id.czqjc);
        czjl = findViewById(R.id.czjl);
        qpps = findViewById(R.id.qpps);
        gyqtcz = findViewById(R.id.gyqtcz);
        czhjc = findViewById(R.id.czhjc);
        shebeikongzhi = findViewById(R.id.shebeikongzhi);
        caozuoyuan.setOnClickListener(new HomeActivityOnClickListeber());
        btnSet.setOnClickListener(new HomeActivityOnClickListeber());
        btnScdzbs.setOnClickListener(new HomeActivityOnClickListeber());
        yonghuxinxi.setOnClickListener(new HomeActivityOnClickListeber());
        qpda.setOnClickListener(new HomeActivityOnClickListeber());
        tjxp.setOnClickListener(new HomeActivityOnClickListeber());
        czqjc.setOnClickListener(new HomeActivityOnClickListeber());
        czjl.setOnClickListener(new HomeActivityOnClickListeber());
        qpps.setOnClickListener(new HomeActivityOnClickListeber());
        yp.setOnClickListener(new HomeActivityOnClickListeber());
        rhaj.setOnClickListener(new HomeActivityOnClickListeber());
        gyqtcz.setOnClickListener(new HomeActivityOnClickListeber());
        czhjc.setOnClickListener(new HomeActivityOnClickListeber());
        shebeikongzhi.setOnClickListener(new HomeActivityOnClickListeber());
        try {
            getUserData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "无法继续返回", Toast.LENGTH_SHORT).show();
    }

    private void getUserData() throws JSONException, IOException {
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 获取存储的数据
        userData = sharedPreferences.getString("userData", "");
        if (userData == null){
            Intent in = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(in);
        }else {

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
            buffer.append(userData);
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
                // 解析JSON数据
                JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                dataArray = jsonObject.getJSONArray("Data");
                Log.e("jhkj", String.valueOf(dataArray));
                dataObject = dataArray.getJSONObject(0);
                caozuoyuan.setText(dataObject.getString("UserName"));
                ruzhishijian.append(dataObject.getString("EntryTimes"));
                lianxidianhua.append(dataObject.getString("EmployeeTel"));
                suoshuqizhan.append(dataObject.getString("GSName"));

            }
        }
    }


    class HomeActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_set) {
                Intent in = new Intent(HomeActivity.this, StettingActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.btn_scdzbs) {
                Intent in = new Intent(HomeActivity.this, ScdzbsActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.yonghuxinxi) {
                Intent in = new Intent(HomeActivity.this, yhxxActivity.class);
                in.putExtra("userData",dataObject);
                startActivity(in);
            }
            if (view.getId() == R.id.caozuoyuan) {
                Log.e("jhkj", dataObject.getString("RoleName"));
                if (dataObject.getString("RoleName").equals("气站管理员")) {
                    Intent in = new Intent(HomeActivity.this, qiehuanyonghuActivity.class);
                    in.putExtra("userData",dataObject);
                    startActivity(in);
                }
            }
            if (view.getId() == R.id.qpda) {
                Intent in = new Intent(HomeActivity.this, QpdaActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.tjxp) {
                Intent in = new Intent(HomeActivity.this, TjxpActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.czqjc) {
                Intent in = new Intent(HomeActivity.this, CzqjcActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.czjl) {
                Intent in = new Intent(HomeActivity.this, CzjlActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.qpps) {
                Intent in = new Intent(HomeActivity.this, XzkhActivity.class);
                in.putExtra("jrjm","qpps");
                startActivity(in);
            }
            if (view.getId() == R.id.yp) {
                Intent in = new Intent(HomeActivity.this, XzkhActivity.class);
                in.putExtra("jrjm","yp");
                startActivity(in);
            }
            if (view.getId() == R.id.rhaj) {
                Intent in = new Intent(HomeActivity.this, XzkhActivity.class);
                in.putExtra("jrjm","rhaj");
                startActivity(in);
            }
            if (view.getId() == R.id.czhjc) {
                Intent in = new Intent(HomeActivity.this, CzhjcActivity.class);
                in.putExtra("jrjm","rhaj");
                startActivity(in);
            }
            if (view.getId() == R.id.shebeikongzhi) {
                Intent in = new Intent(HomeActivity.this, ShebeikongzhiActivity.class);
                startActivity(in);
            }
        }
    }



}