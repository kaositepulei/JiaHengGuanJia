package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.util.ScanResultReceiver;
import com.google.gson.JsonObject;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ScqppsjlActivity extends AppCompatActivity {

    private Spinner kongmanleixing;
    private EditText qipingbianma;
    private ImageButton saomiao;
    private String kehuhq;
    private String Cus_id;
    private String ContactTel;
    private EditText gukexingming;
    private EditText gukedianhua;
    private TextView dangqianshijian;
    private Button btn_submit;
    private Spinner peisongleixing;
    private EditText shouhuodizhi;
    private EditText gangpingzhongliang;
    private String PersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scqppsjl);
        List<String> kmlxList = new ArrayList<>();
        kmlxList.add("空瓶");
        kmlxList.add("重瓶");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScqppsjlActivity.this, android.R.layout.simple_spinner_item,kmlxList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        kongmanleixing = findViewById(R.id.kongmanleixing);
        kongmanleixing.setAdapter(adapter);
        qipingbianma = findViewById(R.id.qipingbianma);
        saomiao = findViewById(R.id.saomiao);
        gukexingming = findViewById(R.id.gukexingming);
        gukedianhua = findViewById(R.id.gukedianhua);
        dangqianshijian = findViewById(R.id.dangqianshijian);
        btn_submit = findViewById(R.id.btn_submit);
        peisongleixing = findViewById(R.id.peisongleixing);
        shouhuodizhi = findViewById(R.id.shouhuodizhi);
        gangpingzhongliang = findViewById(R.id.gangpingzhongliang);
        saomiao.setOnClickListener(new ScqppsjlActivityOnClickListeber());
        dangqianshijian.setOnClickListener(new ScqppsjlActivityOnClickListeber());
        btn_submit.setOnClickListener(new ScqppsjlActivityOnClickListeber());

        SimpleDateFormat formatters = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatters.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDates = new Date(System.currentTimeMillis()); //获取当前时间
        String newTimes = formatters.format(curDates);   //格式转换
        dangqianshijian.setText(newTimes);

        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);
        Intent intent = getIntent();
        kehuhq = intent.getStringExtra("kehu");
        gukexingming.setText(kehuhq);
        Cus_id = intent.getStringExtra("Cus_id");
        ContactTel = intent.getStringExtra("ContactTel");
        gukedianhua.setText(ContactTel);
        Log.e("jhkj", intent.getStringExtra("Cus_id")+"kwhuhao");
    }

    class ScqppsjlActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {

            }
            if (view.getId() == R.id.saomiao) {
                IntentIntegrator integrator = new IntentIntegrator(ScqppsjlActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }
            if (view.getId() == R.id.dangqianshijian) {
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ScqppsjlActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(ScqppsjlActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(ScqppsjlActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(android.icu.util.Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(ScqppsjlActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(android.icu.util.Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(ScqppsjlActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(android.icu.util.Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(ScqppsjlActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(android.icu.util.Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(ScqppsjlActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(android.icu.util.Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(ScqppsjlActivity.this);
                secondPicker.setMinValue(1);
                secondPicker.setMaxValue(60);
                secondPicker.setWrapSelectorWheel(false);
                secondPicker.setValue(calendar.get(Calendar.SECOND));

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
                LinearLayout.LayoutParams hourPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(dayPickerParams);
                LinearLayout.LayoutParams minutePickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(minutePickerParams);
                LinearLayout.LayoutParams secondPickerParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                dayPicker.setLayoutParams(secondPickerParams);


                // 将NumberPicker添加到布局中
                layout.addView(yearPicker);
                layout.addView(monthPicker);
                layout.addView(dayPicker);
                layout.addView(hourPicker);
                layout.addView(minutePicker);
                layout.addView(secondPicker);

                // 设置对话框的标题和布局
                TextView titleView = new TextView(ScqppsjlActivity.this);
                titleView.setText("请选择充后检查时间");
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
                                int selectedHour = hourPicker.getValue();
                                int selectedMinute = minutePicker.getValue();
                                int selectedSecond = secondPicker.getValue();
                                // 在这里可以使用选中的年份和月份进行相关操作
                                dangqianshijian.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                androidx.appcompat.app.AlertDialog dialog = builder.create();
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
            if (view.getId() == R.id.btn_submit) {
                JSONObject dataObject = new JSONObject();
                dataObject.put("Cus_id",Cus_id);
                if(peisongleixing.getSelectedItem().toString().equals("回收")) {
                    dataObject.put("type",1);
                }
                if(peisongleixing.getSelectedItem().toString().equals("发出")) {
                    dataObject.put("type",2);
                }
                dataObject.put("GasId",qipingbianma.getText().toString());
                dataObject.put("Address",shouhuodizhi.getText().toString());
                dataObject.put("ZongZhong",gangpingzhongliang.getText().toString());
                dataObject.put("DeliveryTime",dangqianshijian.getText().toString());
                try {
                    getPersonName();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dataObject.put("PersonName",PersonName);
                dataObject.put("Customer",kehuhq);
                dataObject.put("Telephone",ContactTel);
                dataObject.put("Longitude","");
                dataObject.put("Latitude","");
                if(kongmanleixing.getSelectedItem().toString().equals("空瓶")) {
                    dataObject.put("KongManLeiXing", 1);
                }
                if(peisongleixing.getSelectedItem().toString().equals("重瓶")) {
                    dataObject.put("KongManLeiXing", 2);
                }
                dataObject.put("uploadStatus", 1);
                dataObject.put("errMsg", "");
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(ScqppsjlActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(ScqppsjlActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView textView = new TextView(ScqppsjlActivity.this);
                textView.setText("添加成功，继续录入");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextSize(25);
                layout.addView(textView);
                // 设置对话框的标题和布局
                TextView titleView = new TextView(ScqppsjlActivity.this);
                titleView.setText("提交成功");
                titleView.setGravity(Gravity.CENTER);
                titleView.setTextSize(20);
                builder.setCustomTitle(titleView)
                        .setView(layout)
                        .setPositiveButton("提交数据", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ScqppsjlActivity.this, ScqppsjllbActivity.class);
                                intent.putExtra("qppsData",dataObject);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("继续录入", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(ScqppsjlActivity.this, ScqppsjlActivity.class);
                                startActivity(intent);
                            }
                        });
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();
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
                    qipingbianma.setText(data);
                } else {
                    String processedData = data.replaceAll("\\s+|,", "");
                    qipingbianma.setText(processedData);
                }
            }
        }

    }


    //调用相机拍照后保存图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result!=null && result.getContents() != null) {
            int lastIndex = result.getContents().lastIndexOf("|");
            if (lastIndex != -1) {
                String qpbm = result.getContents().substring(lastIndex + 1);
                qipingbianma.setText(qpbm);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getPersonName() throws IOException {
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
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        if (userData == null){
            Intent in = new Intent(this, LoginActivity.class);
            startActivity(in);
        }else {
            // 创建一个URL对象
            URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetUserData");
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
                // 解析JSON数据
                JSONObject jsonObjects = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                PersonName = jsonObjects.getString("UserName");
            }
        }
    }
  }