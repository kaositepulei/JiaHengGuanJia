package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.R;
import com.example.myapplication.util.ScanResultReceiver;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TjxpActivity extends AppCompatActivity {

    private String  userData;
    private String  LoginName;
    private String  LoginPass;
    private Spinner shebeipinzhong;
    private Spinner zhizaodanwei;
    private Spinner qipingxinghao;
    private Spinner chongzhuangjiezhi;
    private TextView zhizaonianyue;
    private TextView mocijianyannianyue;
    private TextView xiacijianyannianyue;
    private ImageButton saomiao;
    private EditText qipingtiaoma;
    private EditText scqpzp;
    private EditText cphgzzp;
    private EditText zlzmszp;
    private EditText jdjyzszp;
    private EditText xssyzs;
    private EditText dengjidaima;
    private EditText ziyoubianhao;
    private EditText shoumingnianxian;
    private EditText jianyanzhouqi;
    private EditText gongchenggongzuoyali;
    private EditText shejibihou;
    private EditText shuishiyanyali;
    private EditText gangpingzizhong;
    private EditText chanquandanwei;
    private EditText rongji;
    private String factoryName;
    private String CylinderTypeName;
    private String CylinderModelName;
    private String MediumName;
    private Button btn_submit;
    private EditText jianyandanwei;
    private EditText chuchangbianhao;
    private Spinner qipingshiyongzhuangtai;
    private Spinner qipingzhuangtai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjxp);
        shebeipinzhong = findViewById(R.id.shebeipinzhong);
        zhizaodanwei = findViewById(R.id.zhizaodanwei);
        qipingxinghao = findViewById(R.id.qipingxinghao);
        chongzhuangjiezhi = findViewById(R.id.chongzhuangjiezhi);
        zhizaonianyue = findViewById(R.id.zhizaonianyue);
        mocijianyannianyue = findViewById(R.id.mocijianyannianyue);
        xiacijianyannianyue = findViewById(R.id.xiacijianyannianyue);
        saomiao = findViewById(R.id.saomiao);
        qipingtiaoma = findViewById(R.id.qipingtiaoma);
        scqpzp = findViewById(R.id.scqpzp);
        cphgzzp = findViewById(R.id.cphgzzp);
        zlzmszp = findViewById(R.id.zlzmszp);
        jdjyzszp = findViewById(R.id.jdjyzszp);
        xssyzs = findViewById(R.id.xssyzs);
        dengjidaima = findViewById(R.id.dengjidaima);
        ziyoubianhao = findViewById(R.id.ziyoubianhao);
        shoumingnianxian = findViewById(R.id.shoumingnianxian);
        jianyanzhouqi = findViewById(R.id.jianyanzhouqi);
        gongchenggongzuoyali = findViewById(R.id.gongchenggongzuoyali);
        shejibihou = findViewById(R.id.shejibihou);
        shuishiyanyali = findViewById(R.id.shuishiyanyali);
        gangpingzizhong = findViewById(R.id.gangpingzizhong);
        chanquandanwei = findViewById(R.id.chanquandanwei);
        rongji = findViewById(R.id.rongji);
        btn_submit = findViewById(R.id.btn_submit);
        jianyandanwei = findViewById(R.id.jianyandanwei);
        chuchangbianhao = findViewById(R.id.chuchangbianhao);
        qipingshiyongzhuangtai = findViewById(R.id.qipingshiyongzhuangtai);
        qipingzhuangtai = findViewById(R.id.qipingzhuangtai);
        chuchangbianhao = findViewById(R.id.chuchangbianhao);
        zhizaonianyue.setOnClickListener(new TjxpActivityOnClickListeber());
        mocijianyannianyue.setOnClickListener(new TjxpActivityOnClickListeber());
        xiacijianyannianyue.setOnClickListener(new TjxpActivityOnClickListeber());
        saomiao.setOnClickListener(new TjxpActivityOnClickListeber());
        scqpzp.setOnClickListener(new TjxpActivityOnClickListeber());
        cphgzzp.setOnClickListener(new TjxpActivityOnClickListeber());
        zlzmszp.setOnClickListener(new TjxpActivityOnClickListeber());
        jdjyzszp.setOnClickListener(new TjxpActivityOnClickListeber());
        xssyzs.setOnClickListener(new TjxpActivityOnClickListeber());
        btn_submit.setOnClickListener(new TjxpActivityOnClickListeber());
        // 获取 SharedPreferences 对象
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        // 获取存储的数据
        userData = sharedPreferences.getString("userData", "");
        // 将字符串解析为JSONObject
        JSONObject jsonObject = JSON.parseObject(userData);
        // 提取LoginName和LoginPass的值
        LoginName = jsonObject.getString("LoginName");
        LoginPass = jsonObject.getString("LoginPass");
        try {
            //获取上一次填写的记录信息
            GetAll();

            GetSbpz();
            GetZzdw();
            GetQpxh();
            GetCzjz();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);
    }

    class TjxpActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.zhizaonianyue) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(TjxpActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(TjxpActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(TjxpActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(TjxpActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(TjxpActivity.this);
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
                TextView titleView = new TextView(TjxpActivity.this);
                titleView.setText("请选择年月日");
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
                                zhizaonianyue.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
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
            if (view.getId() == R.id.mocijianyannianyue) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(TjxpActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(TjxpActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(TjxpActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(TjxpActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(TjxpActivity.this);
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
                TextView titleView = new TextView(TjxpActivity.this);
                titleView.setText("请选择年月日");
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
                                mocijianyannianyue.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
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
            if (view.getId() == R.id.xiacijianyannianyue) {
                Calendar calendar = Calendar.getInstance();
                // 创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(TjxpActivity.this);
                // 创建一个LinearLayout作为对话框的布局
                LinearLayout layout = new LinearLayout(TjxpActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // 创建一个NumberPicker并设置范围和禁用循环滚动
                NumberPicker yearPicker = new NumberPicker(TjxpActivity.this);
                yearPicker.setMinValue(1949);
                yearPicker.setMaxValue(2061);
                yearPicker.setWrapSelectorWheel(false);
                yearPicker.setValue(calendar.get(Calendar.YEAR));

                NumberPicker monthPicker = new NumberPicker(TjxpActivity.this);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setValue(calendar.get(Calendar.MONTH)+1);

                NumberPicker dayPicker = new NumberPicker(TjxpActivity.this);
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
                TextView titleView = new TextView(TjxpActivity.this);
                titleView.setText("请选择年月日");
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
                                xiacijianyannianyue.setText(selectedYear+"-"+selectedMonth+"-"+selectedDay);
                            }
                        })
                        .setNegativeButton("取消", null);
                // 创建并显示对话框
                AlertDialog dialog = builder.create();
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
                IntentIntegrator integrator = new IntentIntegrator(TjxpActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }
            if (view.getId() == R.id.scqpzp) {
                BottomSheetDialog dialog = new BottomSheetDialog(TjxpActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.cphgzzp) {
                BottomSheetDialog dialog = new BottomSheetDialog(TjxpActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.zlzmszp) {
                BottomSheetDialog dialog = new BottomSheetDialog(TjxpActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.jdjyzszp) {
                BottomSheetDialog dialog = new BottomSheetDialog(TjxpActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.xssyzs) {
                BottomSheetDialog dialog = new BottomSheetDialog(TjxpActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.btn_submit) {
                JSONObject data = new JSONObject();
                data.put("img1", "");
                data.put("img2", "");
                data.put("img3", "");
                data.put("img4", "");
                data.put("img5", "");
                data.put("CylinderTypeName", shebeipinzhong.getSelectedItem().toString());
                if (shebeipinzhong.getSelectedItem().toString().equals("无缝气瓶")) {
                    data.put("CylinderTypeCode", 1);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("液化二甲醚钢瓶")) {
                    data.put("CylinderTypeCode", 8);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("液化石油气纤维缠绕气瓶")) {
                    data.put("CylinderTypeCode", 10);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("焊接气瓶")) {
                    data.put("CylinderTypeCode", 2);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("液化石油气钢瓶")) {
                    data.put("CylinderTypeCode", 3);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("溶解乙炔气瓶")) {
                    data.put("CylinderTypeCode", 4);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("车用气瓶")) {
                    data.put("CylinderTypeCode", 5);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("低温绝热气瓶")) {
                    data.put("CylinderTypeCode", 6);
                }
                if (shebeipinzhong.getSelectedItem().toString().equals("铝合金无缝气瓶")) {
                    data.put("CylinderTypeCode", 7);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东永安特种装备有限公司")) {
                    data.put("FactoryCode", 2);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东永安高压容器有限公司")) {
                    data.put("FactoryCode", 3);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东莱州市鑫星压力容器有限公司")) {
                    data.put("FactoryCode", 4);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东省潍坊市红旗钢瓶厂")) {
                    data.put("FactoryCode", 5);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东省建设高压容器有限公司")) {
                    data.put("FactoryCode", 6);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东永安合力特种装备有限公司")) {
                    data.put("FactoryCode", 7);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东盛达钢瓶有限公司")) {
                    data.put("FactoryCode", 8);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东金泰永安特种装备股份有限公司")) {
                    data.put("FactoryCode", 9);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东安诚压力容器有限公司")) {
                    data.put("FactoryCode", 10);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东鑫昊特种装备股份有限公司")) {
                    data.put("FactoryCode", 11);
                }
                if (zhizaodanwei.getSelectedItem().toString().equals("山东环日集团有限公司")) {
                    data.put("FactoryCode", 13);
                }
                data.put("FactoryName", zhizaodanwei.getSelectedItem().toString());
                data.put("InspectNumber", jianyandanwei.getText().toString());
                data.put("FactoryNumber", chuchangbianhao.getText().toString());
                data.put("RegisterNumber", dengjidaima.getText().toString());
                data.put("OwnNumber", ziyoubianhao.getText().toString());
                data.put("BarCode", qipingtiaoma.getText().toString());
                data.put("CylinderModelName", qipingxinghao.getSelectedItem().toString());
                if (qipingxinghao.getSelectedItem().toString().equals("YSP4.7")) {
                    data.put("CylinderModelCode", 19);
                }
                if (qipingxinghao.getSelectedItem().toString().equals("YSP12")) {
                    data.put("CylinderModelCode", 20);
                }
                if (qipingxinghao.getSelectedItem().toString().equals("YSP23.5")) {
                    data.put("CylinderModelCode", 21);
                }
                if (qipingxinghao.getSelectedItem().toString().equals("YSP35.5")) {
                    data.put("CylinderModelCode", 22);
                }
                if (qipingxinghao.getSelectedItem().toString().equals("YSP118")) {
                    data.put("CylinderModelCode", 23);
                }
                data.put("MediumName", chongzhuangjiezhi.getSelectedItem().toString());
                if (chongzhuangjiezhi.getSelectedItem().toString().equals("液化石油气")) {
                    data.put("MediumCode", 9);
                }
                if (chongzhuangjiezhi.getSelectedItem().toString().equals("二氧化碳")) {
                    data.put("MediumCode", 10);
                }
                if (chongzhuangjiezhi.getSelectedItem().toString().equals("丙烷")) {
                    data.put("MediumCode", 129);
                }
                if (chongzhuangjiezhi.getSelectedItem().toString().equals("氧气")) {
                    data.put("MediumCode", 287);
                }
                if (chongzhuangjiezhi.getSelectedItem().toString().equals("溶解乙炔")) {
                    data.put("MediumCode", 288);
                }
                data.put("MadeDate", zhizaonianyue.getText().toString());
                data.put("MediumScrappedPeriod", shoumingnianxian.getText().toString());
                data.put("MediumInspectPeriod", jianyanzhouqi.getText().toString());
                data.put("LastInspectDate", mocijianyannianyue.getText().toString());
                data.put("NextInspectDate", xiacijianyannianyue.getText().toString());
                data.put("NominalPressure", gongchenggongzuoyali.getText().toString());
                data.put("CylinderVolume", rongji.getText().toString());
                data.put("DesignThickness", shejibihou.getText().toString());
                data.put("data02", shuishiyanyali.getText().toString());
                data.put("data01", gangpingzizhong.getText().toString());
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("空瓶在库")) {
                    data.put("UsageStatus", 1);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("重瓶在库")) {
                    data.put("UsageStatus", 2);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("派送中")) {
                    data.put("UsageStatus", 3);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("客户处")) {
                    data.put("UsageStatus", 4);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("回收中")) {
                    data.put("UsageStatus", 5);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("重瓶配送站")) {
                    data.put("UsageStatus", 6);
                }
                if (qipingshiyongzhuangtai.getSelectedItem().toString().equals("空瓶配送站")) {
                    data.put("UsageStatus", 7);
                }
                if (qipingzhuangtai.getSelectedItem().toString().equals("正常")) {
                    data.put("Status", 1);
                }
                if (qipingzhuangtai.getSelectedItem().toString().equals("过期")) {
                    data.put("Status", 2);
                }
                if (qipingzhuangtai.getSelectedItem().toString().equals("报废")) {
                    data.put("Status", 3);
                }
                if (qipingzhuangtai.getSelectedItem().toString().equals("丢失")) {
                    data.put("Status", 4);
                }
                if (qipingzhuangtai.getSelectedItem().toString().equals("异常报废")) {
                    data.put("Status", 5);
                }
                data.put("PropertyType", 1);
                data.put("PropertyName", chanquandanwei.getText().toString());
                data.put("LoginName", LoginName);
                data.put("LoginPass", LoginPass);
                // 创建一个URL对象
                URL url = null;
                try {
                    url = new URL("http://a.zbjiaheng.com/WebApi/Centre/CreateArchives");
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
                    connection.setRequestMethod("GET");
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
                Log.e("jhkj", data.toString());
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
                    // 解析JSON数据
                    Log.e("jhkj", response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                    JSONArray jsonArray = JSON.parseArray(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
                    JSONObject jsonObjects = jsonArray.getJSONObject(0);
                    String msg = jsonObjects.getString("msg");
                    String status = jsonObjects.getString("status");
                    Log.e("jhkj", msg);
                    Log.e("jhkj", status);
                    if (status.equals("1")) {
                        // 创建一个AlertDialog.Builder对象
                        AlertDialog.Builder builder = new AlertDialog.Builder(TjxpActivity.this);
                        // 创建一个LinearLayout作为对话框的布局
                        LinearLayout layout = new LinearLayout(TjxpActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        TextView textView = new TextView(TjxpActivity.this);
                        textView.setText(msg);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setTextSize(25);
                        layout.addView(textView);
                        // 设置对话框的标题和布局
                        TextView titleView = new TextView(TjxpActivity.this);
                        titleView.setText("上传结果");
                        titleView.setGravity(Gravity.CENTER);
                        titleView.setTextSize(20);
                        builder.setCustomTitle(titleView)
                                .setView(layout)
                                .setPositiveButton("继续上传", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(TjxpActivity.this, TjxpActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent intent = new Intent(TjxpActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        // 创建并显示对话框
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else {
                        // 创建一个AlertDialog.Builder对象
                        AlertDialog.Builder builder = new AlertDialog.Builder(TjxpActivity.this);
                        // 创建一个LinearLayout作为对话框的布局
                        LinearLayout layout = new LinearLayout(TjxpActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        TextView textView = new TextView(TjxpActivity.this);
                        textView.setText(msg);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setTextSize(25);
                        layout.addView(textView);
                        // 设置对话框的标题和布局
                        TextView titleView = new TextView(TjxpActivity.this);
                        titleView.setText("上传结果");
                        titleView.setGravity(Gravity.CENTER);
                        titleView.setTextSize(20);
                        builder.setCustomTitle(titleView)
                                .setView(layout)
                                .setPositiveButton("重新上传", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(TjxpActivity.this, TjxpActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent intent = new Intent(TjxpActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        // 创建并显示对话框
                        AlertDialog dialog = builder.create();
                        dialog.show();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", java.util.Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle1 = data.getExtras();
            Bitmap bitmap1 = (Bitmap) bundle1.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView1 = findViewById(R.id.showimg_scqpzp);
            imageView1.setImageBitmap(bitmap1);// 将图片显示在ImageView里
        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", java.util.Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle3 = data.getExtras();
            Bitmap bitmap3 = (Bitmap) bundle3.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView3 = findViewById(R.id.showimg_cphgzzp);
            imageView3.setImageBitmap(bitmap3);// 将图片显示在ImageView里
        }
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", java.util.Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle4 = data.getExtras();
            Bitmap bitmap4 = (Bitmap) bundle4.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView4 = findViewById(R.id.showimg_zlzmszp);
            imageView4.setImageBitmap(bitmap4);// 将图片显示在ImageView里
        }
        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", java.util.Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle5 = data.getExtras();
            Bitmap bitmap5 = (Bitmap) bundle5.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap5.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView5 = findViewById(R.id.showimg_jdjyzszp);
            imageView5.setImageBitmap(bitmap5);// 将图片显示在ImageView里
        }
        if (requestCode == 6 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", java.util.Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle6 = data.getExtras();
            Bitmap bitmap6 = (Bitmap) bundle6.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap6.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView6 = findViewById(R.id.showimg_xssyzs);
            imageView6.setImageBitmap(bitmap6);// 将图片显示在ImageView里
        }
/*        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String imageUrl = String.valueOf(selectedImage); // 替换为你的图片URL
            ImageView showimg_scqpzp = findViewById(R.id.showimg_scqpzp); // 替换为你的ImageView ID
            ImageView showimg_cphgzzp = findViewById(R.id.showimg_cphgzzp); // 替换为你的ImageView ID
            ImageView showimg_zlzmszp = findViewById(R.id.showimg_zlzmszp); // 替换为你的ImageView ID
            ImageView showimg_jdjyzszp = findViewById(R.id.showimg_jdjyzszp); // 替换为你的ImageView ID
            ImageView showimg_xssyzs = findViewById(R.id.showimg_xssyzs); // 替换为你的ImageView ID
            Picasso.get()
                    .load(imageUrl)
                    .into(showimg_scqpzp);
            Picasso.get()
                    .load(imageUrl)
                    .into(showimg_cphgzzp);
            Picasso.get()
                    .load(imageUrl)
                    .into(showimg_zlzmszp);
            Picasso.get()
                    .load(imageUrl)
                    .into(showimg_jdjyzszp);
            Picasso.get()
                    .load(imageUrl)
                    .into(showimg_xssyzs);
        }*/
    }

    //获取上一次填写的记录信息
    private void GetAll() throws IOException {
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        // 创建一个URL对象
        URL url = null;
        try {
            url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetZiyoubianhao");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        // 创建一个StringBuilder对象来存储响应数据
        StringBuilder response = new StringBuilder();
        // 创建一个HttpURLConnection对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置请求方法为GET
        try {
            connection.setRequestMethod("GET");
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
            // 解析JSON数据
            /*JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));*/
            String jsonString = response.toString().replaceAll("^\"|\"$", "").replace("\\", "");
            String modifiedStr ="";
            if (jsonString.length() >= 22 && jsonString.length() >= 3) {
                StringBuilder sb = new StringBuilder(jsonString);
                sb.deleteCharAt(21); // 删除第22个字符
                sb.deleteCharAt(sb.length() - 3); // 删除倒数第三个字符
                modifiedStr = sb.toString();
                Log.e("jhkj",modifiedStr);
            } else {
                System.out.println("字符串长度不足，无法删除指定位置的字符。");
            }
            JSONArray jsonArray = JSON.parseArray(modifiedStr);
            Log.e("jhkj", "jsonArray*****"+String.valueOf(jsonArray));
            String status = (String) jsonArray.getJSONObject(0).get("status");
            if (status.equals("1")) {
                // 遍历JSON数组中的每个对象
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // 获取FactoryName的值
                    JSONObject msgObject = jsonObject.getJSONObject("msg");
                    if (msgObject.getString("CylinderTypeName")!=null) {
                        CylinderTypeName = msgObject.getString("CylinderTypeName");
                    }
                    if (msgObject.getString("FactoryName")!=null) {
                        factoryName = msgObject.getString("FactoryName");
                    }
                    if (msgObject.getString("RegisterNumber")!=null) {
                        dengjidaima.setText(msgObject.getString("RegisterNumber"));
                    }
                    if (msgObject.getString("OwnNumber")!=null) {
                        ziyoubianhao.setText(msgObject.getString("OwnNumber"));
                    }
                    if (msgObject.getString("CylinderModelName")!=null) {
                        CylinderModelName = msgObject.getString("CylinderModelName");
                    }
                    if (msgObject.getString("MediumName")!=null) {
                        MediumName = msgObject.getString("MediumName");
                    }
                    if (msgObject.getString("MadeDate")!=null) {
                        zhizaonianyue.setText(msgObject.getString("MadeDate"));
                    }
                    if (msgObject.getString("MediumScrappedPeriod")!=null) {
                        shoumingnianxian.setText(msgObject.getString("MediumScrappedPeriod"));
                    }
                    if (msgObject.getString("LastInspectDate")!=null) {
                        mocijianyannianyue.setText(msgObject.getString("LastInspectDate"));
                    }
                    if (msgObject.getString("NextInspectDate")!=null) {
                        xiacijianyannianyue.setText(msgObject.getString("NextInspectDate"));
                    }
                    if (msgObject.getString("NominalPressure")!=null) {
                        gongchenggongzuoyali.setText(msgObject.getString("NominalPressure"));
                    }
                    if (msgObject.getString("CylinderVolume")!=null) {
                        rongji.setText(msgObject.getString("CylinderVolume"));
                    }
                    if (msgObject.getString("DesignThickness")!=null) {
                        shejibihou.setText(msgObject.getString("DesignThickness"));
                    }
                    if (msgObject.getString("data02")!=null) {
                        shuishiyanyali.setText(msgObject.getString("data02"));
                    }
                    if (msgObject.getString("data01")!=null) {
                        gangpingzizhong.setText(msgObject.getString("data01"));
                    }
                    if (msgObject.getString("PropertyName")!=null) {
                        chanquandanwei.setText(msgObject.getString("PropertyName"));
                    }
                    if (msgObject.getString("UsageStatus")!=null) {
                        chanquandanwei.setText(msgObject.getString("PropertyName"));
                    }
                }
            }
        }
    }

    private void GetSbpz() throws IOException {
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetGsLX");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            JSONArray dataArray = jsonObject.getJSONArray("data");
            List<String> sbpzList = new ArrayList<>();
            int moren0 = 0;
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject sbpzObject = dataArray.getJSONObject(i);
                sbpzList.add(sbpzObject.getString("Value"));
                if (sbpzObject.getString("Value").equals(CylinderTypeName)){
                    moren0 = i;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TjxpActivity.this, android.R.layout.simple_spinner_item, sbpzList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setGravity(Gravity.END); // 设置文字靠右对齐
                    return textView;
                }
            };

            shebeipinzhong.setAdapter(adapter);
            shebeipinzhong.setSelection(moren0);
        }
    }
    private void GetZzdw() throws IOException {
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetGsZZC");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            JSONArray dataArray = jsonObject.getJSONArray("data");
            List<String> zzdwList = new ArrayList<>();
            int moren = 0;
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject zzdwObject = dataArray.getJSONObject(i);
                zzdwList.add(zzdwObject.getString("Value"));
                if (zzdwObject.getString("Value").equals(factoryName)){
                    moren = i;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TjxpActivity.this, android.R.layout.simple_spinner_item, zzdwList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setGravity(Gravity.END); // 设置文字靠右对齐
                    return textView;
                }
            };
            zhizaodanwei.setAdapter(adapter);
            zhizaodanwei.setSelection(moren);
        }
    }
    private void GetQpxh() throws IOException {
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetGasXh");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            JSONArray dataArray = jsonObject.getJSONArray("data");
            List<String> qpxhList = new ArrayList<>();
            int moren2 = 0;
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject qpxhObject = dataArray.getJSONObject(i);
                qpxhList.add(qpxhObject.getString("Value"));
                if (qpxhObject.getString("Value").equals(CylinderModelName)){
                    moren2 = i;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TjxpActivity.this, android.R.layout.simple_spinner_item, qpxhList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setGravity(Gravity.END); // 设置文字靠右对齐
                    return textView;
                }
            };
            qipingxinghao.setAdapter(adapter);
            qipingxinghao.setSelection(moren2);
        }
    }
    private void GetCzjz() throws IOException {
        JSONObject data = new JSONObject();
        data.put("LoginName", LoginName);
        data.put("LoginPass", LoginPass);
        // 创建一个URL对象
        URL url = new URL("http://a.zbjiaheng.com/WebApi/Centre/GetGsCZJZ");
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
            JSONObject jsonObject = JSONObject.parseObject(response.toString().replaceAll("^\"|\"$", "").replace("\\", ""));
            JSONArray dataArray = jsonObject.getJSONArray("data");
            List<String> czjzList = new ArrayList<>();
            int moren3 = 0;
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject czjzObject = dataArray.getJSONObject(i);
                czjzList.add(czjzObject.getString("Value"));
                if (czjzObject.getString("Value").equals(MediumName)){
                    moren3 = i;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TjxpActivity.this, android.R.layout.simple_spinner_item, czjzList){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setGravity(Gravity.END); // 设置文字靠右对齐
                    return textView;
                }
            };
            chongzhuangjiezhi.setAdapter(adapter);
            chongzhuangjiezhi.setSelection(moren3);
        }
    }


    private void handleSelection(int selection) throws CameraAccessException {
        // 根据选择的选项执行相应的操作
        if (selection == 1) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
            startActivityForResult(intent, 1);
        } else if (selection == 2) {
            // 创建一个用于选择照片的Intent
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*"); // 设置选择的文件类型为图片
            // 启动相册应用，并等待结果
            startActivityForResult(intent, 2);
        }
    }

    public void handleOption1Click(View view) throws CameraAccessException {
        handleSelection(1);
    }

    public void handleOption2Click(View view) throws CameraAccessException {
        handleSelection(2);
    }
}