package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

public class CzcxActivity extends AppCompatActivity {

    private EditText jingzhong;

    private EditText pingshu;

    private SearchAdapter searchAdapter;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czcx);
        Log.e("jhkj","********************进入查询详情界面**********************");
        jingzhong = findViewById(R.id.jingzhong);
        pingshu = findViewById(R.id.pingshu);
        //获取查询数据
        getSearchData();

        //进行数据展示
        RecyclerView searchView=findViewById(R.id.searchView);
        searchView.setScrollbarFadingEnabled(false);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter();
        searchView.setAdapter(searchAdapter);
    }

    public void getSearchData() {
        // 获取传递的 Intent
        Intent intent = getIntent();
        // 从 Intent 中提取值
        String searchDataString = intent.getStringExtra("searchData"); // 替换 "key" 为你在 CzjlActivity 中设置的键
        JSONObject searchData = JSONObject.parseObject(searchDataString);
        String startTime = "";
        String endTime = "";
        String fillingMode = "";
        String chongzhuanggong = "";
        String customer = "";
        String chenghao = "";
        String qipingtiaoma = "";
        if (searchData!=null) {
            Log.e("jhkj", String.valueOf(searchData));
             startTime = searchData.getString("startTime");
             endTime = searchData.getString("endTime");
             fillingMode = searchData.getString("fillingMode");
             chongzhuanggong = searchData.getString("chongzhuanggong");
             customer = searchData.getString("customer");
             chenghao = searchData.getString("chenghao");
             qipingtiaoma = searchData.getString("barcode");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        Log.e("jhkj", sharedPreferences.getString("userData", ""));
        // 获取存储的数据
        String jsonString = sharedPreferences.getString("userData", "");
        JSONObject userData = JSONObject.parseObject(jsonString);
        String loginName = userData.getString("LoginName");
        String loginPass = userData.getString("LoginPass");

        JSONObject data = new JSONObject();
        data.put("LoginName", loginName);
        data.put("LoginPass", loginPass);
        data.put("startTime", startTime);
        data.put("endTime", endTime);
        data.put("fillingMode", fillingMode);
        data.put("chongzhuanggong", chongzhuanggong);
        data.put("customer", customer);
        data.put("chenghao", chenghao);
        data.put("PageIndex", 1);
        data.put("barcode", qipingtiaoma);

        Log.e("jhkj", String.valueOf(data));

        // 创建一个URL对象
        URL url = null;
        try {
            url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetCZRecord");
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
            connection.setRequestMethod("POST");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            Log.e("jhkj", String.valueOf(jsonObject));
            String TotalNumber = jsonObject.getString("TotalNumber");
            String TotalNetweight = jsonObject.getString("TotalNetweight");
            jingzhong.setText(TotalNetweight);
            pingshu.setText(TotalNumber);
            jsonArray = jsonObject.getJSONArray("data");
        }
    }



    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cxjl_item,parent,false);
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            JSONObject dataObject = jsonArray.getJSONObject(position);
            holder.qipingtiaoma.setText("气瓶条码: "+dataObject.getString("barcode"));
            holder.chenghao.setText("秤号: "+dataObject.getString("device_id"));
            holder.pizhong.setText("皮重: "+dataObject.getString("ActualbottleWeight")+" | ");
            holder.jingzhong.setText("净重: "+dataObject.getString("actualJingZweight"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // 点击事件处理逻辑
                    Intent intent = new Intent(view.getContext(), czjlxqActivity.class);
                    intent.putExtra("gonghao", dataObject.getString("UserNum"));
                    intent.putExtra("chenghao", dataObject.getString("device_id"));
                    intent.putExtra("qipingtiaoma", dataObject.getString("barcode"));
                    intent.putExtra("qipingxinghao", dataObject.getString("GasXH"));
                    intent.putExtra("kaishishijian", dataObject.getString("startDate"));
                    intent.putExtra("jieshushijian", dataObject.getString("endDate"));
                    intent.putExtra("chongqianzhongliang", dataObject.getString("ActualbottleWeight"));
                    intent.putExtra("chonghouzhongliang", dataObject.getString("ActualtotalWeight"));
                    intent.putExtra("shedingzhongliang", dataObject.getString("actualSetweight"));
                    intent.putExtra("xianshijingzhong", dataObject.getString("actualJingZweight"));
                    intent.putExtra("shijiguanzhuangliang", dataObject.getString("ActualnetWeight"));
                    intent.putExtra("tingzhuangmoshi", dataObject.getString("FillErrMODE"));
                    intent.putExtra("guanzhuangshichang", dataObject.getString("durationtime"));
                    intent.putExtra("mojianriqi", dataObject.getString("lastInspect"));
                    intent.putExtra("xiajianriqi", dataObject.getString("nextInspect"));
                    intent.putExtra("guanzhuanggong", dataObject.getString("username"));

                    view.getContext().startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return jsonArray.size();
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView qipingtiaoma;
            TextView chenghao;
            TextView pizhong;
            TextView jingzhong;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                qipingtiaoma = itemView.findViewById(R.id.qipingtiaoma);
                chenghao = itemView.findViewById(R.id.chenghao);
                pizhong = itemView.findViewById(R.id.pizhong);
                jingzhong = itemView.findViewById(R.id.jingzhong);
            }
        }

    }
}