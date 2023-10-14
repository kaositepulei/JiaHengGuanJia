package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShebeikongzhiActivity extends AppCompatActivity {

    private SearchAdapter searchAdapter;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shebeikongzhi);
        try {
            getSblb();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        RecyclerView searchView = findViewById(R.id.recyclerView);
        int spanCount = 4; // 每行（或每列）的网格数量
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        searchView.setLayoutManager(layoutManager);
        searchView.setScrollbarFadingEnabled(false);
        searchAdapter = new SearchAdapter();
        searchView.setAdapter(searchAdapter);
    }

    public void getSblb() throws IOException, JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        Log.e("jhkj", sharedPreferences.getString("userData", ""));
        // 获取存储的数据
        String jsonString = sharedPreferences.getString("userData", "");
        com.alibaba.fastjson.JSONObject userData = com.alibaba.fastjson.JSONObject.parseObject(jsonString);
        String loginName = userData.getString("LoginName");
        String loginPass = userData.getString("LoginPass");

        JSONObject data = new JSONObject();
        data.put("LoginName", loginName);
        data.put("LoginPass", loginPass);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetScaleList");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            jsonArray = jsonObject.getJSONArray("data");
            JSONObject jsonObjects = new JSONObject();
            jsonObjects.put("Key", "全部");
            jsonObjects.put("Value", "1");
            jsonArray.add(0,jsonObjects);

        }
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


        @NonNull
        @Override
        public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sbkz_item,parent,false);
            return new SearchAdapter.SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
            Log.e("jhkj",jsonArray.toString()+"****************************");
            JSONObject dataObject = null;
            dataObject = jsonArray.getJSONObject(position);
            holder.shebeibianhao.setText(dataObject.getString("Key"));
            String chenghao = dataObject.getString("Key");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShebeikongzhiActivity.this);
                    builder.setTitle("请选择操作")
                            .setItems(new CharSequence[]{"修改时间", "设置提前量", "设置扫码/普通充装"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 根据选择的项执行相应的操作
                                    switch (which) {
                                        case 0:
                                            // 点击了 "修改时间"
                                            Intent intent1 = new Intent(ShebeikongzhiActivity.this, SzcsjActivity.class);
                                            intent1.putExtra("chenghao",chenghao);
                                            startActivity(intent1);
                                            break;
                                        case 1:
                                            // 点击了 "设置提前量"
                                            Intent intent2 = new Intent(ShebeikongzhiActivity.this, ShebeikongzhiActivity.class);
                                            startActivity(intent2);
                                            break;
                                        case 2:
                                            // 点击了 "设置扫码/普通充装"
                                            Intent intent3 = new Intent(ShebeikongzhiActivity.this, ShebeikongzhiActivity.class);
                                            startActivity(intent3);
                                            break;
                                    }
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }


        @Override
        public int getItemCount() {
            if (jsonArray!=null) {
                Log.e("jhkj",jsonArray.toString()+"****************************");
                return jsonArray.size();
            }else {
                Log.e("jhkj",jsonArray.toString()+"****************************");
                return 0;
            }
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView shebeibianhao;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                shebeibianhao = itemView.findViewById(R.id.shebeibianhao);
            }
        }

    }
}