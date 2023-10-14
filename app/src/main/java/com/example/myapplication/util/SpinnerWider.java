package com.example.myapplication.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class SpinnerWider {


    public  SpinnerWider(Spinner spinner) {
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        // 创建一个 Paint 对象
        Paint paint = new Paint();
        paint.setTextSize(spinner.getSelectedItem().toString().length());

        // 找到最长的字段宽度
        float maxWidth = 0;
        for (String item : items) {
            float width = paint.measureText(item);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        // 将最长字段的宽度应用于 Spinner 的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) spinner.getLayoutParams();
        params.width = (int) maxWidth;
        spinner.setLayoutParams(params);
    }
}
