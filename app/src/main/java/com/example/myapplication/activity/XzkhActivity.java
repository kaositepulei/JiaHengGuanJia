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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class XzkhActivity extends AppCompatActivity {

    private XzkhAdapter xzkhAdapter;

    private JSONArray jsonArray;
    private Spinner xuanzetiaojian;
    private String  searchType;
    private ImageButton search;

    private EditText chaxunneirong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xzkh);
        searchType = "";
        try {
            getCustomerList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        search = findViewById(R.id.search);
        xuanzetiaojian = findViewById(R.id.xuanzetiaojian);
        chaxunneirong = findViewById(R.id.chaxunneirong);
        search.setOnClickListener(new XzkhActivityOnClickListeber());
        RecyclerView customerView=findViewById(R.id.customerView);
        customerView.setScrollbarFadingEnabled(false);
        customerView.setLayoutManager(new LinearLayoutManager(this));
        xzkhAdapter = new XzkhAdapter();
        customerView.setAdapter(xzkhAdapter);
        xuanzetiaojian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 当选择了一个选项时触发此方法
                String selectedItem = (String) parent.getItemAtPosition(position);
                // 处理选中的选项
                if (selectedItem.equals("客户姓名")){
                    searchType = "1";
                }
                if (selectedItem.equals("联系电话")){
                    searchType = "2";
                }
                if (selectedItem.equals("身份证号")){
                    searchType = "3";
                }
                if (selectedItem.equals("客户编码")){
                    searchType = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private class XzkhActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(XzkhActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.search) {
                try {
                    getCustomerList();
                    xzkhAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void getCustomerList() throws IOException {
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
        data.put("searchType",searchType);
        if(chaxunneirong==null){
            data.put("searchValue", "");
        }else {
            if (chaxunneirong.getText().length() <= 0){
                data.put("searchValue", "");
            }else {
                data.put("searchValue", chaxunneirong.getText().toString());
            }
        }
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/cuslist");
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
                JSONObject jsonObjects = JSON.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                jsonArray = jsonObjects.getJSONObject("data").getJSONArray("ds");
                Log.e("jhkj", String.valueOf(jsonArray));

            }
        }

    public class XzkhAdapter extends RecyclerView.Adapter<XzkhAdapter.XzkhViewHolder> {


        @NonNull
        @Override
        public XzkhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.xzkh_item,parent,false);
            return new XzkhViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull XzkhViewHolder holder, int position) {
            Intent intent = getIntent();
            String xzjm =  intent.getStringExtra("jrjm");
            JSONObject dataObject = jsonArray.getJSONObject(position);
            holder.kehumingcheng.setText("客户名称: "+dataObject.getString("name"));
            if (dataObject.getString("Category").equals("1")) {
                holder.kehuleixing.setText("客户类型: " + "零售");
            }else {
                holder.kehuleixing.setText("客户类型: " + "批发");
            }
            holder.yongqidizhi.setText("用气地址: "+dataObject.getString("ContactAddress")+"  |  ");
            holder.lianxidianhua.setText("联系电话: "+dataObject.getString("ContactTel"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // 点击事件处理逻辑
                    if (xzjm.equals("qpps")) {
                        Intent intent = new Intent(view.getContext(), KhxqQppsActivity.class);
                        intent.putExtra("kehumingcheng", dataObject.getString("name"));
                        intent.putExtra("kehuleixing", dataObject.getString("Category"));
                        intent.putExtra("yongqidizhi", dataObject.getString("ContactAddress"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("shenfenzhenghao", dataObject.getString("ContactTel"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("kehubianma", dataObject.getString("CusNum"));

                        view.getContext().startActivity(intent);
                    }
                    if (xzjm.equals("yp")) {
                        Intent intent = new Intent(view.getContext(), KhxqYpActivity.class);
                        intent.putExtra("kehumingcheng", dataObject.getString("name"));
                        intent.putExtra("kehuleixing", dataObject.getString("Category"));
                        intent.putExtra("yongqidizhi", dataObject.getString("ContactAddress"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("kehubianma", dataObject.getString("CusNum"));

                        view.getContext().startActivity(intent);
                    }
                    if (xzjm.equals("rhaj")) {
                        Intent intent = new Intent(view.getContext(), KhxqRhajActivity.class);
                        intent.putExtra("kehumingcheng", dataObject.getString("name"));
                        intent.putExtra("kehuleixing", dataObject.getString("Category"));
                        intent.putExtra("yongqidizhi", dataObject.getString("ContactAddress"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("lianxidianhua", dataObject.getString("ContactTel"));
                        intent.putExtra("kehubianma", dataObject.getString("CusNum"));

                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return jsonArray.size();
        }

        public class XzkhViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView kehumingcheng;
            TextView kehuleixing;
            TextView yongqidizhi;
            TextView lianxidianhua;
            public XzkhViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                kehumingcheng = itemView.findViewById(R.id.kehumingcheng);
                kehuleixing = itemView.findViewById(R.id.kehuleixing);
                yongqidizhi = itemView.findViewById(R.id.yongqidizhi);
                lianxidianhua = itemView.findViewById(R.id.yapingzongshu);
            }
        }

    }


    }





