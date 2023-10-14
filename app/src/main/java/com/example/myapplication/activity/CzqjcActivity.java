package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.util.ScanResultReceiver;
import com.example.myapplication.util.SpinnerWider;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class CzqjcActivity extends AppCompatActivity {

    private TextView chongqianjianchashijian;
    private TextView qpzjyyxqn;
    private ImageButton saomiao;
    private EditText qipingtiaoma;
    private Spinner jsmcjhxfzsygybz;
    private Button btn_submit;
    private Spinner jcry;
    private Spinner qpzzxkzjjdjcbj;
    private Spinner qtmcyqpgybzsfyz;
    private Spinner cqqpsfzy;
    private Spinner qpysbzsffhgd;
    private Spinner pfdcqklwxsffhgd;
    private Spinner qpnywsyyl;
    private Spinner wbsfylwbxjqtssqx;
    private Spinner aqfjqqbfhaqyq;

    private JSONArray chongzhuanggongArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czqjc);
        chongqianjianchashijian = findViewById(R.id.chongqianjianchashijian);
        qpzjyyxqn = findViewById(R.id.qpzjyyxqn);
        saomiao = findViewById(R.id.saomiao);
        jsmcjhxfzsygybz = findViewById(R.id.jsmcjhxfzsygybz);
        qipingtiaoma = findViewById(R.id.qipingtiaoma);
        btn_submit = findViewById(R.id.btn_submit);
        jcry = findViewById(R.id.jcry);
        qpzzxkzjjdjcbj = findViewById(R.id.qpzzxkzjjdjcbj);
        qtmcyqpgybzsfyz = findViewById(R.id.qtmcyqpgybzsfyz);
        cqqpsfzy = findViewById(R.id.cqqpsfzy);
        qpysbzsffhgd = findViewById(R.id.qpysbzsffhgd);
        pfdcqklwxsffhgd = findViewById(R.id.pfdcqklwxsffhgd);
        qpnywsyyl = findViewById(R.id.qpnywsyyl);
        wbsfylwbxjqtssqx = findViewById(R.id.wbsfylwbxjqtssqx);
        aqfjqqbfhaqyq = findViewById(R.id.aqfjqqbfhaqyq);


        chongqianjianchashijian.setOnClickListener(new CzqjcActivityOnClickListeber());
        saomiao.setOnClickListener(new CzqjcActivityOnClickListeber());
        qpzjyyxqn.setOnClickListener(new CzqjcActivityOnClickListeber());
        btn_submit.setOnClickListener(new CzqjcActivityOnClickListeber());

        SimpleDateFormat formatters = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatters.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDates = new Date(System.currentTimeMillis()); //获取当前时间
        String newTimes = formatters.format(curDates);   //格式转换
        chongqianjianchashijian.setText(newTimes);

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd"); //设置时间格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
        String newTime = formatter.format(curDate);   //格式转换
        qpzjyyxqn.setText(newTime);

        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);

        List<String> jsmcjhxfzsygybzList = new ArrayList<>();
        jsmcjhxfzsygybzList.add("一致");
        jsmcjhxfzsygybzList.add("不一致");
        List<String> qpysbzsffhgdList = new ArrayList<>();
        qpysbzsffhgdList.add("符合");
        qpysbzsffhgdList.add("不符合");
        List<String> aqfjqqbfhaqyqList = new ArrayList<>();
        aqfjqqbfhaqyqList.add("符合");
        aqfjqqbfhaqyqList.add("不符合");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CzqjcActivity.this, android.R.layout.simple_spinner_item,jsmcjhxfzsygybzList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        jsmcjhxfzsygybz.setAdapter(adapter);


        ArrayAdapter<String> adapters = new ArrayAdapter<String>(CzqjcActivity.this, android.R.layout.simple_spinner_item,qpysbzsffhgdList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        qpysbzsffhgd.setAdapter(adapters);


        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(CzqjcActivity.this, android.R.layout.simple_spinner_item,aqfjqqbfhaqyqList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        aqfjqqbfhaqyq.setAdapter(adapterss);
        getCzg();
    }

    class CzqjcActivityOnClickListeber implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.chongqianjianchashijian) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CzqjcActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(CzqjcActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(CzqjcActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(CzqjcActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(CzqjcActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(CzqjcActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(CzqjcActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(CzqjcActivity.this);
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
                TextView titleView = new TextView(CzqjcActivity.this);
                titleView.setText("请选择充前检查时间");
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
                                chongqianjianchashijian.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
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
            if (view.getId() == R.id.qpzjyyxqn) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CzqjcActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(CzqjcActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(CzqjcActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(CzqjcActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(CzqjcActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

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


                // 将NumberPicker添加到布局中
                layout.addView(yearPicker);
                layout.addView(monthPicker);
                layout.addView(dayPicker);

                // 设置对话框的标题和布局
                TextView titleView = new TextView(CzqjcActivity.this);
                titleView.setText("请选择检验有效期");
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
                                // 在这里可以使用选中的年份和月份进行相关操作
                                qpzjyyxqn.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
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
            if (view.getId() == R.id.saomiao) {
                IntentIntegrator integrator = new IntentIntegrator(CzqjcActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }
            if (view.getId() == R.id.btn_submit) {

                SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
                Log.e("jhkj", sharedPreferences.getString("userData", ""));
                // 获取存储的数据
                String jsonString = sharedPreferences.getString("userData", "");
                JSONObject userData = JSONObject.parseObject(jsonString);
                String loginName = userData.getString("LoginName");
                String loginPass = userData.getString("LoginPass");
                JSONObject data = new JSONObject();
                data.put("key", UUID.randomUUID().toString());
                data.put("LoginName", loginName);
                data.put("LoginPass", loginPass);
                data.put("gasIdType", 1);
                data.put("GasId", qipingtiaoma.getText().toString());
                data.put("CheckTime", chongqianjianchashijian.getText().toString());
                data.put("CheckPerson", jcry.getSelectedItem().toString());
                data.put("Xkz", qpzzxkzjjdjcbj.getSelectedItem().toString());
                data.put("Media", qtmcyqpgybzsfyz.getSelectedItem().toString());
                data.put("Tag", jsmcjhxfzsygybz.getSelectedItem().toString());
                data.put("Zy", cqqpsfzy.getSelectedItem().toString());
                data.put("Color", qpysbzsffhgd.getSelectedItem().toString());
                data.put("GBT15383", pfdcqklwxsffhgd.getSelectedItem().toString());
                data.put("Pressure", qpnywsyyl.getSelectedItem().toString());
                data.put("Qx", wbsfylwbxjqtssqx.getSelectedItem().toString());
                data.put("Valid", qpzjyyxqn.getText().toString());
                data.put("safe", aqfjqqbfhaqyq.getSelectedItem().toString());
                Log.e("jhkj",data.toString());



                // 创建一个URL对象
                URL url = null;
                try {
                    url = new URL("http://a.zbjiaheng.com/WebApi/Centre/CzCheckPreAdd");
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
                    JSONArray jsonArray = JSONArray.parseArray(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                    String status = (String) jsonArray.getJSONObject(0).get("status");
                    String msg = (String) jsonArray.getJSONObject(0).get("msg");
                    Log.e("jhkj", status);
                    Log.e("jhkj", msg);
                    if (status.equals("0")){
                        Toast.makeText(CzqjcActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CzqjcActivity.this);
                        builder.setTitle("")
                                .setMessage("提交成功，继续录入？")
                                .setPositiveButton("继续录入", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击继续录入按钮后的逻辑处理
                                        // 关闭当前Activity
                                        finish();
                                        // 重新启动当前Activity
                                        Intent intent = new Intent(CzqjcActivity.this, CzqjcActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("返回主页", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击返回主页按钮后的逻辑处理
                                        Intent intent = new Intent(CzqjcActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
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
                    qipingtiaoma.setText(data);
                } else {
                    String processedData = data.replaceAll("\\s+|,", "");
                    qipingtiaoma.setText(processedData);
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
                qipingtiaoma.setText(qpbm);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void getCzg() {

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

        // 创建一个URL对象
        URL url = null;
        try {
            url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetCZWhere");
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
            String status = jsonObject.getString("status");
            Log.e("jhkj", status);
            if (status.equals("1")) {
                chongzhuanggongArr = jsonObject.getJSONArray("data").getJSONArray(1);
                List<String> chongzhuanggongList;chongzhuanggongList = new ArrayList<>();
                for (int i = 0; i < chongzhuanggongArr.size(); i++) {
                    Log.e("jhkj",chongzhuanggongArr.getJSONObject(i).toString());
                    JSONObject sbpzObject = chongzhuanggongArr.getJSONObject(i);
                    chongzhuanggongList.add(sbpzObject.getString("Value"));
                }
                chongzhuanggongList.remove(0);
                ArrayAdapter<String> chongzhuanggongadapter = new ArrayAdapter<String>(CzqjcActivity.this, android.R.layout.simple_spinner_item, chongzhuanggongList){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setGravity(Gravity.END); // 设置文字靠右对齐
                        return textView;
                    }
                };
                jcry.setAdapter(chongzhuanggongadapter);
            }
        }
    }
}