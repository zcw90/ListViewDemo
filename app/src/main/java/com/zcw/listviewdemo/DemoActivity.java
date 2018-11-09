package com.zcw.listviewdemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DemoActivity extends AppCompatActivity {

    private ConstraintLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        init();
    }

    private void init() {
        contentView = (ConstraintLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        TextView textView = new TextView(this);
        textView.setText("ZZZZ");
        textView.setBackgroundColor(Color.BLUE);

        contentView.addView(textView);
    }
}