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

public class MultiList1Activity extends AppCompatActivity {
    private static final String TAG = MultiList1Activity.class.getSimpleName();

    private ListView listView;
    private Multi1Adapter adapter;
    private List<String> data;

    private LinearLayout llMultiChoiceTitle;
    private Button btnDelete;

    private TextView tvSelectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list1);

        init();
    }

    private void init() {
        llMultiChoiceTitle = findViewById(R.id.ll_multi_title1);
        btnDelete = findViewById(R.id.btn_multi_delete1);
        tvSelectResult = findViewById(R.id.tv_multi_select_result1);

        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_multi_test1);
        adapter = new Multi1Adapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList1Activity.this, data.get(position) + " Selection!!!");

                if(listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
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
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(listView.getChoiceMode() != AbsListView.CHOICE_MODE_MULTIPLE) {
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                    adapter.setSelectMulti(true);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private static class Multi1Adapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        /** 是否进入多选模式 */
        private boolean isSelectMulti;

        public Multi1Adapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
            this.isSelectMulti = false;
        }

        public boolean isSelectMulti() {
            return isSelectMulti;
        }

        public void setSelectMulti(boolean selectMulti) {
            isSelectMulti = selectMulti;
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
                view = LayoutInflater.from(context).inflate(R.layout.item_multi_list1, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_multi_item_content1);
                holder.imgSelect = view.findViewById(R.id.img_multi_item_content1);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));
            holder.imgSelect.setVisibility(isSelectMulti ? View.VISIBLE : View.GONE);

            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
            public ImageView imgSelect;
        }
    }
}
