package com.zcw.listviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zcw.base.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class NormalListActivity extends AppCompatActivity {
    private static final String TAG = NormalListActivity.class.getSimpleName();

    private ListView listView;
    private NormalAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_list);

        init();
    }

    private void init() {
        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_normal_test);
        adapter = new NormalAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(NormalListActivity.this, "Click " + data.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(NormalListActivity.this, "Long click " + data.get(position));
                return true;
            }
        });
    }

    private static class NormalAdapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        public NormalAdapter(Context context, List<String> data) {
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
            NormalAdapter.ViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new NormalAdapter.ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_normal_list, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_normal_item_content);
                view.setTag(holder);
            }
            else {
                holder = (NormalAdapter.ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));
            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
        }
    }
}
