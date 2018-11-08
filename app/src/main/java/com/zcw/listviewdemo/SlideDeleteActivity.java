package com.zcw.listviewdemo;

import android.content.Context;
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
        adapter.setListView(listView);

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

        private SlideDeleteListView listView;

        public SlideDeleteAdapter(Context context, List<SlideDeleteBean> data) {
            this.context = context;
            this.data = data;
        }

        public void setListView(SlideDeleteListView listView) {
            this.listView = listView;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_slide_delete, parent, false);

                holder.tvTitle = view.findViewById(R.id.tv_slide_delete_item_title);
                holder.tvContent = view.findViewById(R.id.tv_slide_delete_item_content);
                holder.tvSlideMenu = view.findViewById(R.id.tv_slide_delete_item_icon);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder)view.getTag();
            }

            holder.tvTitle.setText(data.get(position).getTitle());
            holder.tvContent.setText(data.get(position).getContent());
            holder.tvSlideMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.toast(context, "Delete");
                    data.remove(position);
                    notifyDataSetChanged();

                    if(listView != null) {
                        listView.smoothCloseSlideMenu();
                    }
                }
            });

            return view;
        }

        private static class ViewHolder {
            public TextView tvTitle;
            public TextView tvContent;

            public TextView tvSlideMenu;
        }
    }
}
