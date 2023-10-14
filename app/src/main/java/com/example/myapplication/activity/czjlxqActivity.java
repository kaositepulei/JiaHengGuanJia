package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;

public class czjlxqActivity extends AppCompatActivity {

    private TextView gonghao;
    private TextView chenghao;
    private TextView qipingtiaoma;
    private TextView qipingxinghao;
    private TextView kaishishijian;
    private TextView jieshushijian;
    private TextView chongqianzhongliang;
    private TextView chonghouzhongliang;
    private TextView shedingzhongliang;
    private TextView xianshijingzhong;
    private TextView shijiguanzhuangliang;
    private TextView tingzhuangmoshi;
    private TextView guanzhuangshichang;
    private TextView mojianriqi;
    private TextView xiajianriqi;
    private TextView guanzhuanggong;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czjlxq);

        gonghao = findViewById(R.id.gonghao);
        chenghao = findViewById(R.id.chenghao);
        qipingtiaoma = findViewById(R.id.qipingtiaoma);
        qipingxinghao = findViewById(R.id.qipingxinghao);
        kaishishijian = findViewById(R.id.kaishishijian);
        jieshushijian = findViewById(R.id.jieshushijian);
        chongqianzhongliang = findViewById(R.id.chongqianzhongliang);
        chonghouzhongliang = findViewById(R.id.chonghouzhongliang);
        shedingzhongliang = findViewById(R.id.shedingzhongliang);
        xianshijingzhong = findViewById(R.id.xianshijingzhong);
        shijiguanzhuangliang = findViewById(R.id.shijiguanzhuangliang);
        tingzhuangmoshi = findViewById(R.id.tingzhuangmoshi);
        guanzhuangshichang = findViewById(R.id.guanzhuangshichang);
        mojianriqi = findViewById(R.id.mojianriqi);
        xiajianriqi = findViewById(R.id.xiajianriqi);
        guanzhuanggong = findViewById(R.id.guanzhuanggong);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new czjlxqActivity.czjlxqActivityOnClickListeber());

        Intent intent = getIntent();
        String gonghaos = (String) intent.getSerializableExtra("gonghao");
        String chenghaos = (String) intent.getSerializableExtra("chenghao");
        String qipingtiaomas = (String) intent.getSerializableExtra("qipingtiaoma");
        String qipingxinghaos = (String) intent.getSerializableExtra("qipingxinghao");
        String kaishishijians = (String) intent.getSerializableExtra("kaishishijian");
        String jieshushijians = (String) intent.getSerializableExtra("jieshushijian");
        String chongqianzhongliangs = (String) intent.getSerializableExtra("chongqianzhongliang");
        String chonghouzhongliangs = (String) intent.getSerializableExtra("chonghouzhongliang");
        String shedingzhongliangs = (String) intent.getSerializableExtra("shedingzhongliang");
        String xianshijingzhongs = (String) intent.getSerializableExtra("xianshijingzhong");
        String shijiguanzhuangliangs = (String) intent.getSerializableExtra("shijiguanzhuangliang");
        String tingzhuangmoshis = (String) intent.getSerializableExtra("tingzhuangmoshi");
        String guanzhuangshichangs = (String) intent.getSerializableExtra("guanzhuangshichang");
        String mojianriqis = (String) intent.getSerializableExtra("mojianriqi");
        String xiajianriqis = (String) intent.getSerializableExtra("xiajianriqi");
        String guanzhuanggongs = (String) intent.getSerializableExtra("guanzhuanggong");

        gonghao.setText(gonghaos);
        chenghao.setText(chenghaos);
        qipingtiaoma.setText(qipingtiaomas);
        qipingxinghao.setText(qipingxinghaos);
        kaishishijian.setText(kaishishijians);
        jieshushijian.setText(jieshushijians);
        chongqianzhongliang.setText(chongqianzhongliangs);
        chonghouzhongliang.setText(chonghouzhongliangs);
        shedingzhongliang.setText(shedingzhongliangs);
        xianshijingzhong.setText(xianshijingzhongs);
        shijiguanzhuangliang.setText(shijiguanzhuangliangs);
        tingzhuangmoshi.setText(tingzhuangmoshis);
        guanzhuangshichang.setText(guanzhuangshichangs);
        mojianriqi.setText(mojianriqis);
        xiajianriqi.setText(xiajianriqis);
        guanzhuanggong.setText(guanzhuanggongs);
    }

    private class czjlxqActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(czjlxqActivity.this, CzcxActivity.class);
                startActivity(in);
            }


        }
    }
}