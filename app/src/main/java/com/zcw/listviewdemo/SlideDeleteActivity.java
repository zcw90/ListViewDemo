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
import com.zcw.listviewdemo.bean.SlideDeleteBean;
import com.zcw.listviewdemo.view.SlideDeleteLayout;

import java.util.ArrayList;
import java.util.List;

public class SlideDeleteActivity extends AppCompatActivity {

    private ListView listView;
    private SlideDeleteAdapter adapter;
    private List<SlideDeleteBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_delete);

        init();
    }

    private void init() {
        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            SlideDeleteBean bean = new SlideDeleteBean();
            bean.setTitle("Title " + (i + 1));
            bean.setContent("content " + (i + 1));
            data.add(bean);
        }

        listView = findViewById(R.id.lv_slide_delete);
        adapter = new SlideDeleteAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(SlideDeleteActivity.this, "Click " + data.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(SlideDeleteActivity.this, "Long click " + data.get(position));
                return true;
            }
        });
    }

    private static class SlideDeleteAdapter extends BaseAdapter {

        private Context context;
        private List<SlideDeleteBean> data;

        public SlideDeleteAdapter(Context context, List<SlideDeleteBean> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public SlideDeleteBean getItem(int position) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_slide_delete, parent, false);

                holder.tvTitle = view.findViewById(R.id.tv_slide_delete_item_title);
                holder.tvContent = view.findViewById(R.id.tv_slide_delete_item_content);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder)view.getTag();
            }

            holder.tvTitle.setText(data.get(position).getTitle());
            holder.tvContent.setText(data.get(position).getContent());
            return view;
        }

        private static class ViewHolder {
            public TextView tvTitle;
            public TextView tvContent;
        }
    }
}
