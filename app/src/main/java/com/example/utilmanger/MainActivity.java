package com.example.utilmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.utilmanger.manger.LayoutManger;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottie_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 约束布局动态设置
         */
        ConstraintLayout constraintlayout = (ConstraintLayout) findViewById(R.id.constraint);
        TextView tv_constraint = findViewById(R.id.tv_constraint);
        EditText et_edit = findViewById(R.id.et_edit);
        LayoutManger.setConstraintLayout(constraintlayout, tv_constraint, et_edit, this);
        /**
         * 动画json
         */
        lottie_view = findViewById(R.id.lottie_view);
        lottie_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie_view.playAnimation();
            }
        });

    }
}