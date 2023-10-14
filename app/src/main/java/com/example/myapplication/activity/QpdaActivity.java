package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.util.RecyclerItemClickListener;
import com.example.myapplication.util.ScanResultReceiver;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class QpdaActivity extends AppCompatActivity {

    private String  userData;
    private String  searchType;
    private JSONArray jsonArray;
    private ImageButton btnBack;
    private Spinner spinner;
    private EditText chaxunneirong;
    private ImageButton search;
    private QpdaAdapter qpdaAdapter;
    private Button kuaisuchaxundangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpda);
        searchType = "";
        try {
            getqpda();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnBack = findViewById(R.id.btn_back);
        spinner = findViewById(R.id.spinner);
        chaxunneirong = findViewById(R.id.chaxunneirong);
        search = findViewById(R.id.search);
        kuaisuchaxundangan = findViewById(R.id.kuaisuchaxundangan);
        kuaisuchaxundangan.setOnClickListener(new QpdaActivityOnClickListeber());
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setScrollbarFadingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        qpdaAdapter = new QpdaAdapter();
        recyclerView.setAdapter(qpdaAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 当选择了一个选项时触发此方法
                String selectedItem = (String) parent.getItemAtPosition(position);
                // 处理选中的选项
                if (selectedItem.equals("气瓶条码")){
                    searchType = "1";
                }
                if (selectedItem.equals("登记代码")){
                    searchType = "2";
                }
                if (selectedItem.equals("出厂编号")){
                    searchType = "3";
                }
                if (selectedItem.equals("自有编号")){
                    searchType = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnBack.setOnClickListener(new QpdaActivityOnClickListeber());
        search.setOnClickListener(new QpdaActivityOnClickListeber());
        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);
    }

    private class QpdaActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(QpdaActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.search) {
                try {
                    getqpda();
                    qpdaAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (view.getId() == R.id.kuaisuchaxundangan){
                IntentIntegrator integrator = new IntentIntegrator(QpdaActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }


        }
    }

    private void getqpda() throws IOException {
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
        Log.e("jhkj",LoginName);
        Log.e("jhkj",LoginPass);

        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        data.put("PageIndex", 1);
        data.put("searchType", searchType);
      /*  Log.e("jhkj", String.valueOf(chaxunneirong));
        data.put("searchValue", "");*/
        if(chaxunneirong==null){
            data.put("searchValue", "");
        }else {
            if (chaxunneirong.getText().length() <= 0){
                data.put("searchValue", "");
            }else {
                data.put("searchValue", chaxunneirong.getText().toString());
            }
        }
        if (userData == null){
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }else {
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetCylinder");
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
                Log.e("jhkj", String.valueOf(jsonArray));

            }
        }
    }

    public class ScannerReceiver extends BroadcastReceiver {
        public String getBarCodeFromURL(String url) {
            String[] codeArr = url.split("\\.");
            String lastCode = codeArr[codeArr.length - 1];
            String[] codeArrFiltered = lastCode.split("[^A-Za-z0-9]");
            String barCode = "";
            for (String code : codeArrFiltered) {
                if (code.length() > barCode.length()) {
                    barCode = code;
                }
            }
            return barCode;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.scanner.broadcast")) {
                String data = intent.getStringExtra("data");
                if (data.indexOf("http") != -1) {
                    data = getBarCodeFromURL(data);
                    chaxunneirong.setText(data);
                } else {
                    String processedData = data.replaceAll("\\s+|,", "");
                    chaxunneirong.setText(processedData);
                }
            }
        }

    }


        public class QpdaAdapter extends RecyclerView.Adapter<QpdaAdapter.QpdaViewHolder> {


            @NonNull
        @Override
        public QpdaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qpda_item,parent,false);
            return new QpdaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QpdaViewHolder holder, int position) {
            JSONObject dataObject = jsonArray.getJSONObject(position);
            holder.qipingtiaoma.setText("气瓶条码: "+dataObject.getString("BarCode"));
            holder.suoshugongsi.setText(dataObject.getString("FactoryName")+" | ");
            holder.qipingleixing.setText(dataObject.getString("CylinderTypeName")+" | ");
            holder.qitileixing.setText(dataObject.getString("MediumName"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // 点击事件处理逻辑
                    Intent intent = new Intent(view.getContext(), qpdaxqActivity.class);
                    intent.putExtra("shebeipinzhong", dataObject.getString("CylinderTypeName"));
                    intent.putExtra("zhizaodanwei", dataObject.getString("FactoryName"));
                    intent.putExtra("jianchadanwei", dataObject.getString("InspectNumber"));
                    intent.putExtra("chuchangbianhao", dataObject.getString("FactoryNumber"));
                    intent.putExtra("dengjidaima", dataObject.getString("RegisterNumber"));
                    intent.putExtra("ziyoubianhao", dataObject.getString("OwnNumber"));
                    intent.putExtra("qipingtiaoma", dataObject.getString("BarCode"));
                    intent.putExtra("qipingxinghao", dataObject.getString("CylinderModelName"));
                    intent.putExtra("chongzhuangjiezhi", dataObject.getString("MediumName"));
                    intent.putExtra("zhizaonianyue", dataObject.getString("MadeDate"));
                    intent.putExtra("shoumingnianxian", dataObject.getString("MediumScrappedPeriod"));
                    intent.putExtra("jianchazhouqi", dataObject.getString("MediumInspectPeriod"));
                    intent.putExtra("mocijianyannianyue", dataObject.getString("LastInspectDate"));
                    intent.putExtra("xiacijianyannianyue", dataObject.getString("NextInspectDate"));
                    intent.putExtra("gongchenggongzuoyali", dataObject.getString("NominalPressure"));
                    intent.putExtra("rongji", dataObject.getString("CylinderVolume"));
                    intent.putExtra("shejibihou", dataObject.getString("DesignThickness"));
                    intent.putExtra("shuishiyanyali", dataObject.getString("data02"));
                    intent.putExtra("gangpingzizhong", dataObject.getString("data01"));
                    intent.putExtra("qipingshiyongzhuangtai", dataObject.getString("UsageStatusFun"));
                    intent.putExtra("qipingzhuangtai", dataObject.getString("StatusFun"));
                    intent.putExtra("chanquanxingzhi", dataObject.getString("PropertyTypeFun"));
                    intent.putExtra("chanquandanwei", dataObject.getString("PropertyName"));

                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return jsonArray.size();
        }

        public class QpdaViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView qipingtiaoma;
            TextView suoshugongsi;
            TextView qipingleixing;
            TextView qitileixing;
            public QpdaViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                qipingtiaoma = itemView.findViewById(R.id.qipingtiaoma);
                suoshugongsi = itemView.findViewById(R.id.suoshugongsi);
                qipingleixing = itemView.findViewById(R.id.qipingleixing);
                qitileixing = itemView.findViewById(R.id.qitileixing);
            }
        }

    }

    //调用相机拍照后保存图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            int lastIndex = result.getContents().lastIndexOf("|");
            if (lastIndex != -1) {
                String qpbm = result.getContents().substring(lastIndex + 1);
                chaxunneirong.setText(qpbm);
            }
        }
    }


}