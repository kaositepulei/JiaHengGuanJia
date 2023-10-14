package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.myapplication.R;

import java.util.Calendar;

public class NumberPickerActivity extends AppCompatActivity {

    private NumberPicker yearPicker;
    private NumberPicker monthPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker);
        yearPicker = findViewById(R.id.yearPicker);
        monthPicker = findViewById(R.id.monthPicker);
        // 设置年份范围
        String[] years = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        yearPicker.setDisplayedValues(years);
        yearPicker.setMinValue(1);
        yearPicker.setMaxValue(years.length);
        yearPicker.setValue(1);
    }
}