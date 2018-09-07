package com.zcw.listviewdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.zcw.base.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class MultiList3Activity extends AppCompatActivity {
    private static final String TAG = MultiList3Activity.class.getSimpleName();

    private ListView listView;
    private Multi3Adapter adapter;
    private List<String> data;

    private LinearLayout llMultiChoiceTitle;
    private Button btnDelete;

    private TextView tvSelectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list3);

        init();
    }

    private void init() {
        llMultiChoiceTitle = findViewById(R.id.ll_multi_title3);
        btnDelete = findViewById(R.id.btn_multi_delete3);
        tvSelectResult = findViewById(R.id.tv_multi_select_result3);

        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_multi_test3);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
        adapter = new Multi3Adapter(this, data);
        listView.setAdapter(adapter);
        listView.setItemChecked(0, true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList3Activity.this, data.get(position) + " Selection!!!");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList3Activity.this, data.get(position) + " Long click!!!");
                return true;
            }
        });
    }

    private static class Multi3Adapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        public Multi3Adapter(Context context, List<String> data) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_multi_list3, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_multi_item_content3);
                holder.imgSelect = view.findViewById(R.id.img_multi_item_content3);
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

    private AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
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

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            View view = LayoutInflater.from(MultiList3Activity.this).inflate(R.layout.multi_select_title, null);
//            mode.setCustomView(view);
            mode.setType(ActionMode.TYPE_FLOATING);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            tvSelectResult.setText("");
        }
    };
}
