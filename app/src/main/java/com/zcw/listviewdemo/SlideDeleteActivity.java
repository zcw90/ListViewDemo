package com.zcw.listviewdemo;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zcw.base.CommonUtils;
import com.zcw.listviewdemo.bean.SlideDeleteBean;
import com.zcw.listviewdemo.view.SlideDeleteListView;

import java.util.ArrayList;
import java.util.List;

public class SlideDeleteActivity extends AppCompatActivity {

    private SlideDeleteListView listView;
    private SlideDeleteAdapter adapter;
    private List<SlideDeleteBean> data;

    private ConstraintLayout slideDeleteLayout;

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

        slideDeleteLayout = findViewById(R.id.subView_slide_delete);
        slideDeleteLayout.setBackgroundColor(getResources().getColor(R.color.button_normal));
        slideDeleteLayout.findViewById(R.id.tv_slide_delete_item_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.toast(SlideDeleteActivity.this, "zcw");
            }
        });

        slideDeleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.toast(SlideDeleteActivity.this, "zcw2222222");
            }
        });
        slideDeleteLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CommonUtils.toast(SlideDeleteActivity.this, "zcw33333333");
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
