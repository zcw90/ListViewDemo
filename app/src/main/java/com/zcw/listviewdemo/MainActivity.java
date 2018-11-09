package com.zcw.listviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.btn_normal_list).setOnClickListener(listener);
        findViewById(R.id.btn_single_list1).setOnClickListener(listener);
        findViewById(R.id.btn_single_list2).setOnClickListener(listener);
        findViewById(R.id.btn_multi_list1).setOnClickListener(listener);
        findViewById(R.id.btn_multi_list2).setOnClickListener(listener);
        findViewById(R.id.btn_multi_list3).setOnClickListener(listener);
        findViewById(R.id.btn_slide_delete).setOnClickListener(listener);
        findViewById(R.id.btn_test).setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_normal_list:
                    startActivity(getIntent(NormalListActivity.class));
                    break;

                case R.id.btn_single_list1:
                    startActivity(getIntent(SingleList1Activity.class));
                    break;

                case R.id.btn_single_list2:
                    startActivity(getIntent(SingleList2Activity.class));
                    break;

                case R.id.btn_multi_list1:
                    startActivity(getIntent(MultiList1Activity.class));
                    break;

                case R.id.btn_multi_list2:
                    startActivity(getIntent(MultiList2Activity.class));
                    break;

                case R.id.btn_multi_list3:
                    startActivity(getIntent(MultiList3Activity.class));
                    break;

                case R.id.btn_slide_delete:
                    startActivity(getIntent(SlideDeleteActivity.class));
                    break;

                case R.id.btn_test:
                    startActivity(getIntent(DemoActivity.class));
                    break;
            }
        }
    };

    private Intent getIntent(Class<?> cls) {
        return new Intent(MainActivity.this, cls);
    }
}
