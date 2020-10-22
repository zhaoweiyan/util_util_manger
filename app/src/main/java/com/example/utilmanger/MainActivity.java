package com.example.utilmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.utilmanger.manger.LayoutManger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintlayout = (ConstraintLayout) findViewById(R.id.constraint);
        TextView tv_constraint = (TextView) findViewById(R.id.tv_constraint);
        EditText et_edit = (EditText) findViewById(R.id.et_edit);
        LayoutManger.setConstraintLayout(constraintlayout, tv_constraint, et_edit, this);
    }
}