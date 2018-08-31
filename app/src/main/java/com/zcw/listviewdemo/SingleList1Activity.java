package com.zcw.listviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcw.base.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SingleList1Activity extends AppCompatActivity {

    private ListView listView;
    private SingleAdapter1 adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list1);

        init();
    }

    private void init() {
        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_single_test1);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        adapter = new SingleAdapter1(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(SingleList1Activity.this, "Select " + data.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(SingleList1Activity.this, "Long click " + data.get(position));
                return true;
            }
        });
    }

    private static class SingleAdapter1 extends BaseAdapter {

        private Context context;
        private List<String> data;

        public SingleAdapter1(Context context, List<String> data) {
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
            SingleAdapter1.ViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new SingleAdapter1.ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_single_list1, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_single_item_content1);
                view.setTag(holder);
            }
            else {
                holder = (SingleAdapter1.ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));
            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
        }
    }
}
