package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.ByteArrayOutputStream;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class ScdzbsActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageButton saomiao;
    private EditText qipingbianma;
    private EditText shangchuanzhaopian;
    private Size previewSize;
    private ImageReader imageReader;
    private Button btn_cancel;
    private Button btn_submit;
    private EditText chuchangbianhao;
    private ImageView show_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scdzbs);
        previewSize = new Size(1920, 1080);
        // 初始化 ImageReader 变量
        imageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1);
        btnBack = findViewById(R.id.btn_back);
        saomiao = findViewById(R.id.saomiao);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);
        shangchuanzhaopian = findViewById(R.id.shangchuanzhaopian);
        qipingbianma = findViewById(R.id.qipingbianma);
        chuchangbianhao = findViewById(R.id.chuchangbianhao);
        show_img = findViewById(R.id.show_img);
        btnBack.setOnClickListener(new ScdzbsActivityOnClickListeber());
        saomiao.setOnClickListener(new ScdzbsActivityOnClickListeber());
        shangchuanzhaopian.setOnClickListener(new ScdzbsActivityOnClickListeber());
        btn_cancel.setOnClickListener(new ScdzbsActivityOnClickListeber());
        btn_submit.setOnClickListener(new ScdzbsActivityOnClickListeber());
        IntentFilter intentFilter = new IntentFilter("com.example.SCAN_RESULT");
        registerReceiver(new ScanResultReceiver(), intentFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scanner.broadcast");
        filter.addAction("android.intent.action.scanner.RFID");
        ScannerReceiver receiver = new ScannerReceiver();
        registerReceiver(receiver, filter);
    }

    class ScdzbsActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(ScdzbsActivity.this, HomeActivity.class);
                startActivity(in);
            }
            if (view.getId() == R.id.saomiao) {
                IntentIntegrator integrator = new IntentIntegrator(ScdzbsActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("请对准二维码");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(ScanfDevActivity.class);
                integrator.initiateScan();
            }
            if (view.getId() == R.id.shangchuanzhaopian) {
                BottomSheetDialog dialog = new BottomSheetDialog(ScdzbsActivity.this);
                dialog.setContentView(R.layout.xuanzezhaopianfangshi);
                dialog.show();
            }
            if (view.getId() == R.id.btn_submit) {
                Log.e("jhkj", String.valueOf(qipingbianma.getText().length()));
                Log.e("jhkj", String.valueOf(chuchangbianhao.getText().length()));
               /* Log.e("jhkj",show_img.getDrawable().toString());*/
                if (qipingbianma.getText().length() == 0) {
                    Toast.makeText(ScdzbsActivity.this, "请输入或扫描二维码获得气瓶条码！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (chuchangbianhao.getText().length() == 0) {
                    Toast.makeText(ScdzbsActivity.this, "请输入出厂编号！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (show_img.getDrawable() == null) {
                    Toast.makeText(ScdzbsActivity.this, "请上传jpg格式的图片！", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Drawable drawable = show_img.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    String imageData = "data:image/jpeg;base64," + base64String;
                    String[] imageDataArray = imageData.split("base64,");
                    String formattedImageData = imageDataArray[1];

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
                    data.put("TagId", qipingbianma.getText().toString());
                    data.put("GasId", chuchangbianhao.getText().toString());
                    data.put("GasImage", formattedImageData);
                    data.put("QrCode", "");
                    Log.e("jhkj",formattedImageData);

                    // 创建一个URL对象
                    URL url = null;
                    try {
                        url = new URL("http://a.zbjiaheng.com/WebApi/Centre/UpdateGasTagId");
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
                        if (status.equals("0")) {
                            Toast.makeText(ScdzbsActivity.this, "上传第三方平台失败，请重新上传!", Toast.LENGTH_SHORT).show();
                        }
                        if (status.equals("1")) {
                            Toast.makeText(ScdzbsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ScdzbsActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
            if (view.getId() == R.id.btn_cancel) {
                Intent in = new Intent(ScdzbsActivity.this, HomeActivity.class);
                startActivity(in);
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
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
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
            ((ImageView) findViewById(R.id.show_img)).setImageBitmap(bitmap);// 将图片显示在ImageView里
            }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String imageUrl = String.valueOf(selectedImage); // 替换为你的图片URL
            ImageView imageView = findViewById(R.id.show_img); // 替换为你的ImageView ID
            Picasso.get()
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}