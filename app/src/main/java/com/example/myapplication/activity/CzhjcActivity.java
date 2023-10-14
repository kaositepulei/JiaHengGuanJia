package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.util.ScanResultReceiver;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CzhjcActivity extends AppCompatActivity {

    private EditText qipingtiaoma;
    private ImageButton saomiao;
    private TextView chonghoujianchashijian;
    private  List<String> wenduList = Arrays.asList("正常", "不正常");
    private  List<String> czlzgdfwnList = Arrays.asList("规定内", "规定外");
    private  List<String> pfjqtpgljdmflhList = Arrays.asList("良好", "不良好");
    private  List<String> gbbxhxldyzqxList = Arrays.asList("未出现", "出现");
    private  List<String> ptwdmyycsgdjxList = Arrays.asList("没异常", "异常");
    private  List<String> qpztjsbqhczbqList = Arrays.asList("有标签", "无标签");
    private  Spinner wendu;
    private  Spinner czlzgdfwn;
    private  Spinner pfjqtzpkljdmflh;
    private  Spinner gbbxhxldyzqx;
    private  Spinner ptwdmyycsgdjx;
    private  Spinner qpztjsbqhczbq;
    private JSONArray chongzhuanggongArr;
    private Spinner jcry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czhjc);
        qipingtiaoma = findViewById(R.id.qipingtiaoma);
        saomiao = findViewById(R.id.saomiao);
        chonghoujianchashijian = findViewById(R.id.chonghoujianchashijian);
        wendu = findViewById(R.id.wendu);
        czlzgdfwn = findViewById(R.id.czlzgdfwn);
        pfjqtzpkljdmflh = findViewById(R.id.pfjqtzpkljdmflh);
        gbbxhxldyzqx = findViewById(R.id.gbbxhxldyzqx);
        ptwdmyycsgdjx = findViewById(R.id.ptwdmyycsgdjx);
        qpztjsbqhczbq = findViewById(R.id.qpztjsbqhczbq);
        jcry = findViewById(R.id.jcry);
        saomiao.setOnClickListener(new CzhjcActivityOnClickListeber());
        chonghoujianchashijian.setOnClickListener(new CzhjcActivityOnClickListeber());
        SimpleDateFormat formatters = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatters.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDates = new Date(System.currentTimeMillis()); //获取当前时间
        String newTimes = formatters.format(curDates);   //格式转换
        chonghoujianchashijian.setText(newTimes);

        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);
        ArrayAdapter<String> wenduadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,wenduList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        wendu.setAdapter(wenduadapters);

        ArrayAdapter<String> czlzgdfwnadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,czlzgdfwnList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        czlzgdfwn.setAdapter(czlzgdfwnadapters);


        ArrayAdapter<String> pfjqtzpkljdmflhadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,pfjqtpgljdmflhList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        pfjqtzpkljdmflh.setAdapter(pfjqtzpkljdmflhadapters);


        ArrayAdapter<String> gbbxhxldyzqxadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,gbbxhxldyzqxList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        gbbxhxldyzqx.setAdapter(gbbxhxldyzqxadapters);


        ArrayAdapter<String> ptwdmyycsgdjxadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,ptwdmyycsgdjxList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        ptwdmyycsgdjx.setAdapter(ptwdmyycsgdjxadapters);


        ArrayAdapter<String> qpztjsbqhczbqadapters = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item,qpztjsbqhczbqList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.END); // 设置文字靠右对齐
                return textView;
            }
        };
        qpztjsbqhczbq.setAdapter(qpztjsbqhczbqadapters);
        getCzg();
    }

    class CzhjcActivityOnClickListeber implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.saomiao) {
                IntentIntegrator integrator = new IntentIntegrator(CzhjcActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }
            if (view.getId() == R.id.chonghoujianchashijian) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CzhjcActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(CzhjcActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(CzhjcActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(CzhjcActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(CzhjcActivity.this);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(31);
                dayPicker.setWrapSelectorWheel(false);
                dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

                NumberPicker hourPicker = new NumberPicker(CzhjcActivity.this);
                hourPicker.setMinValue(1);
                hourPicker.setMaxValue(24);
                hourPicker.setWrapSelectorWheel(false);
                hourPicker.setValue(calendar.get(Calendar.HOUR));

                NumberPicker minutePicker = new NumberPicker(CzhjcActivity.this);
                minutePicker.setMinValue(1);
                minutePicker.setMaxValue(60);
                minutePicker.setWrapSelectorWheel(false);
                minutePicker.setValue(calendar.get(Calendar.MINUTE));

                NumberPicker secondPicker = new NumberPicker(CzhjcActivity.this);
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
                TextView titleView = new TextView(CzhjcActivity.this);
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
                                chonghoujianchashijian.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay+" "+selectedHour+":"+selectedMinute+":"+selectedSecond);
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
                ArrayAdapter<String> chongzhuanggongadapter = new ArrayAdapter<String>(CzhjcActivity.this, android.R.layout.simple_spinner_item, chongzhuanggongList){
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