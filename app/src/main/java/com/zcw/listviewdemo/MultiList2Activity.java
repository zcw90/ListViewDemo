package com.zcw.listviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zcw.base.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiList2Activity extends AppCompatActivity {
    private static final String TAG = MultiList2Activity.class.getSimpleName();

    private ListView listView;
    private Multi2Adapter adapter;
    private List<String> data;

    private LinearLayout llMultiChoiceTitle;
    private Button btnDelete;

    private TextView tvSelectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list2);

        init();
    }

    private void init() {
        llMultiChoiceTitle = findViewById(R.id.ll_multi_title2);
        btnDelete = findViewById(R.id.btn_multi_delete2);
        tvSelectResult = findViewById(R.id.tv_multi_select_result2);

        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_multi_test2);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adapter = new Multi2Adapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList2Activity.this, data.get(position) + " Selection!!!");

                SparseBooleanArray booleanArray = listView.getCheckedItemPositions();
                StringBuilder builder = new StringBuilder();
                for(int i = 0;i < booleanArray.size(); i++) {
                    int key = booleanArray.keyAt(i);
                    if(booleanArray.get(key)) {
                        builder.append(adapter.getItem(key) + "\n");
                    }
                }
                tvSelectResult.setText(builder.toString());
            }
        });
    }

    private static class Multi2Adapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        public Multi2Adapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_multi_list2, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_multi_item_content2);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));
            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
            public ImageView imgSelect;
        }
    }
}
