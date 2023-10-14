package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;

public class qpdaxqActivity extends AppCompatActivity {

    private TextView shebeipinzhong;
    private TextView zhizaodanwei;
    private TextView jianchadanwei;
    private TextView chuchangbianhao;
    private TextView dengjidaima;
    private TextView ziyoubianhao;
    private TextView qipingtiaoma;
    private TextView qipingxinghao;
    private TextView chongzhuangjiezhi;
    private TextView zhizaonianyue;
    private TextView shoumingnianxian;
    private TextView jianchazhouqi;
    private TextView mocijianyannianyue;
    private TextView xiacijianyannianyue;
    private TextView gongchenggongzuoyali;
    private TextView rongji;
    private TextView shejibihou;
    private TextView shuishiyanyali;
    private TextView gangpingzizhong;
    private TextView qipingshiyongzhuangtai;
    private TextView qipingzhuangtai;
    private TextView chanquanxingzhi;
    private TextView chanquandanwei;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpdaxq);

        shebeipinzhong = findViewById(R.id.shebeipinzhong);
        zhizaodanwei = findViewById(R.id.zhizaodanwei);
        jianchadanwei = findViewById(R.id.jianchadanwei);
        chuchangbianhao = findViewById(R.id.chuchangbianhao);
        dengjidaima = findViewById(R.id.dengjidaima);
        ziyoubianhao = findViewById(R.id.ziyoubianhao);
        qipingtiaoma = findViewById(R.id.qipingtiaoma);
        qipingxinghao = findViewById(R.id.qipingxinghao);
        chongzhuangjiezhi = findViewById(R.id.chongzhuangjiezhi);
        zhizaonianyue = findViewById(R.id.zhizaonianyue);
        shoumingnianxian = findViewById(R.id.shoumingnianxian);
        jianchazhouqi = findViewById(R.id.jianchazhouqi);
        mocijianyannianyue = findViewById(R.id.mocijianyannianyue);
        xiacijianyannianyue = findViewById(R.id.xiacijianyannianyue);
        gongchenggongzuoyali = findViewById(R.id.gongchenggongzuoyali);
        rongji = findViewById(R.id.rongji);
        shejibihou = findViewById(R.id.shejibihou);
        shuishiyanyali = findViewById(R.id.shuishiyanyali);
        gangpingzizhong = findViewById(R.id.gangpingzizhong);
        qipingshiyongzhuangtai = findViewById(R.id.qipingshiyongzhuangtai);
        qipingzhuangtai = findViewById(R.id.qipingzhuangtai);
        chanquanxingzhi = findViewById(R.id.chanquanxingzhi);
        chanquandanwei = findViewById(R.id.chanquandanwei);

        Intent intent = getIntent();
        String shebeipinzhongs = (String) intent.getSerializableExtra("shebeipinzhong");
        String zhizaodanweis = (String) intent.getSerializableExtra("zhizaodanwei");
        String jianchadanweis = (String) intent.getSerializableExtra("jianchadanwei");
        String chuchangbianhaos = (String) intent.getSerializableExtra("chuchangbianhao");
        String dengjidaimas = (String) intent.getSerializableExtra("dengjidaima");
        String ziyoubianhaos = (String) intent.getSerializableExtra("ziyoubianhao");
        String qipingtiaomas = (String) intent.getSerializableExtra("qipingtiaoma");
        String qipingxinghaos = (String) intent.getSerializableExtra("qipingxinghao");
        String chongzhuangjiezhis = (String) intent.getSerializableExtra("chongzhuangjiezhi");
        String zhizaonianyues = (String) intent.getSerializableExtra("zhizaonianyue");
        String shoumingnianxians = (String) intent.getSerializableExtra("shoumingnianxian");
        String jianchazhouqis = (String) intent.getSerializableExtra("jianchazhouqi");
        String mocijianyannianyues = (String) intent.getSerializableExtra("mocijianyannianyue");
        String xiacijianyannianyues = (String) intent.getSerializableExtra("xiacijianyannianyue");
        String gongchenggongzuoyalis = (String) intent.getSerializableExtra("gongchenggongzuoyali");
        String rongjis = (String) intent.getSerializableExtra("rongji");
        String shejibihous = (String) intent.getSerializableExtra("shejibihou");
        String shuishiyanyalis = (String) intent.getSerializableExtra("shuishiyanyali");
        String gangpingzizhongs = (String) intent.getSerializableExtra("gangpingzizhong");
        String qipingshiyongzhuangtais = (String) intent.getSerializableExtra("qipingshiyongzhuangtai");
        String qipingzhuangtais = (String) intent.getSerializableExtra("qipingzhuangtai");
        String chanquanxingzhis = (String) intent.getSerializableExtra("chanquanxingzhi");
        String chanquandanweis = (String) intent.getSerializableExtra("chanquandanwei");
        shebeipinzhong.setText(shebeipinzhongs);
        zhizaodanwei.setText(zhizaodanweis);
        jianchadanwei.setText(jianchadanweis);
        chuchangbianhao.setText(chuchangbianhaos);
        dengjidaima.setText(dengjidaimas);
        ziyoubianhao.setText(ziyoubianhaos);
        qipingtiaoma.setText(qipingtiaomas);
        qipingxinghao.setText(qipingxinghaos);
        chongzhuangjiezhi.setText(chongzhuangjiezhis);
        zhizaonianyue.setText(zhizaonianyues);
        shoumingnianxian.setText(shoumingnianxians);
        jianchazhouqi.setText(jianchazhouqis);
        mocijianyannianyue.setText(mocijianyannianyues);
        xiacijianyannianyue.setText(xiacijianyannianyues);
        gongchenggongzuoyali.setText(gongchenggongzuoyalis);
        rongji.setText(rongjis);
        shejibihou.setText(shejibihous);
        shuishiyanyali.setText(shuishiyanyalis);
        gangpingzizhong.setText(gangpingzizhongs);
        qipingshiyongzhuangtai.setText(qipingshiyongzhuangtais);
        qipingzhuangtai.setText(qipingzhuangtais);
        chanquanxingzhi.setText(chanquanxingzhis);
        chanquandanwei.setText(chanquandanweis);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new qpdxqaActivityOnClickListeber());

    }

    private class qpdxqaActivityOnClickListeber implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_back) {
                Intent in = new Intent(qpdaxqActivity.this, QpdaActivity.class);
                startActivity(in);
            }


        }
    }
}