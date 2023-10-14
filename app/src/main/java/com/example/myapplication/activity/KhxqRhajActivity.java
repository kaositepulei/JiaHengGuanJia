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
import android.widget.TextView;

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

public class KhxqRhajActivity extends AppCompatActivity {

    private Button jinrupeisong;
    private String kehubianma;
    private JSONArray jsonArray;
    private EditText yonghuleixing;
    private EditText yonghumingcheng;
    private EditText lianxiren;
    private EditText lianxidizhi;
    private EditText lianxidianhua;
    private EditText shenfenzhenghao;
    private EditText yongqidizhi;
    private EditText zuihouanjianriqi;
    private EditText anjianchaoshitianshu;
    private EditText beizhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khxq_rhqj);


        yonghumingcheng = findViewById(R.id.yonghumingcheng);
        yonghuleixing = findViewById(R.id.yonghuleixing);
        yongqidizhi = findViewById(R.id.yongqidizhi);
        jinrupeisong = findViewById(R.id.jinrupeisong);
        lianxiren = findViewById(R.id.lianxiren);
        lianxidizhi = findViewById(R.id.lianxidizhi);
        lianxidianhua = findViewById(R.id.lianxidianhua);
        shenfenzhenghao = findViewById(R.id.shenfenzhenghao);
        zuihouanjianriqi = findViewById(R.id.zuihouanjianriqi);
        anjianchaoshitianshu = findViewById(R.id.anjianchaoshitianshu);
        beizhu = findViewById(R.id.beizhu);
        jinrupeisong.setOnClickListener(new KhxqRhajActivityOnClickListeber());

        Intent intent = getIntent();
        kehubianma = (String) intent.getSerializableExtra("kehubianma");

        try {
            getKH();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

    private class KhxqRhajActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.jinrupeisong) {
                Intent in = new Intent(KhxqRhajActivity.this, ScqppsjlActivity.class);
                startActivity(in);
            }
        }
    }


    private void getKH() throws IOException {
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 获取存储的数据
        String userData = sharedPreferences.getString("userData", "");
        Log.e("jhkj",userData);
        // 将字符串解析为JSONObject
        JSONObject jsonObject = JSON.parseObject(userData);
        // 提取LoginName和LoginPass的值
        String LoginName = jsonObject.getString("LoginName");
        String LoginPass = jsonObject.getString("LoginPass");
        Log.e("jhkj",LoginName);
        Log.e("jhkj",LoginPass);

        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        data.put("searchValue", kehubianma);
        data.put("searchType", 2);
        if (userData == null){
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }else {
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/customerList");
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
                jsonArray = jsonObjects.getJSONArray("data");
                Log.e("jhkj", jsonArray.toString());
                if (jsonArray.getJSONObject(0).get("Category").toString().equals("1")) {
                    yonghuleixing.setText("零售");
                }else {
                    yonghuleixing.setText("批发");
                }
                yonghumingcheng.setText((String) jsonArray.getJSONObject(0).get("Name"));
                lianxiren.setText((String) jsonArray.getJSONObject(0).get("ContactName"));
                lianxidizhi.setText((String) jsonArray.getJSONObject(0).get("ContactAddress"));
                lianxidianhua.setText((String) jsonArray.getJSONObject(0).get("ContactTel"));
                shenfenzhenghao.setText((String) jsonArray.getJSONObject(0).get("ContactIDNumber"));
                yongqidizhi.setText((String) jsonArray.getJSONObject(0).get("GasAddress"));
                zuihouanjianriqi.setText((String) jsonArray.getJSONObject(0).get("LastCheckDate"));
                anjianchaoshitianshu.setText((String) jsonArray.getJSONObject(0).get("chaoshi"));
                beizhu.setText((String) jsonArray.getJSONObject(0).get("Remark"));

            }
        }
    }
}