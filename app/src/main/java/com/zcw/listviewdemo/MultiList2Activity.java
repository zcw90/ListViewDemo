package com.zcw.listviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
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

public class MultiList2Activity extends AppCompatActivity {
    private static final String TAG = MultiList2Activity.class.getSimpleName();

    private ListView listView;
    private MyAdapter adapter;
    private List<String> data;

    private LinearLayout llMultiChoiceTitle;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list);

        init();
    }

    private void init() {
        llMultiChoiceTitle = findViewById(R.id.ll_title);
        btnDelete = findViewById(R.id.btn_delete);

        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_test);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(multiChoiceModeListener);
        adapter = new MyAdapter(this, data);
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE_MODAL) {
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                }
                else {
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
                }
                return true;
            }
        });
    }

    /**
     * 进入多选模式
     */
    private void enterMultiChoice() {
        llMultiChoiceTitle.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
    }

    /**
     * 退出多选模式
     */
    private void exitMultiChoice() {
        llMultiChoiceTitle.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            listView.clearChoices();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class MyAdapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        public MyAdapter(Context context, List<String> data) {
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
            MyAdapter.ViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new MyAdapter.ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_normal_list, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_normal_item_content);
                view.setTag(holder);
            }
            else {
                holder = (MyAdapter.ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));

            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
            public ImageView imgSelect;
        }
    }

    private AbsListView.MultiChoiceModeListener multiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            LogUtil.e(TAG, "onItemCheckedStateChanged");
            CommonUtils.toast(MultiList2Activity.this, "onItemCheckedStateChanged");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            LogUtil.e(TAG, "进入多选模式");
            CommonUtils.toast(MultiList2Activity.this, "进入多选模式");
            enterMultiChoice();
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            LogUtil.e(TAG, "onPrepareActionMode");
            CommonUtils.toast(MultiList2Activity.this, "onPrepareActionMode");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            LogUtil.e(TAG, "onPrepareActionMode");
            CommonUtils.toast(MultiList2Activity.this, "onPrepareActionMode");
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            LogUtil.e(TAG, "退出多选模式");
            CommonUtils.toast(MultiList2Activity.this, "退出多选模式");
            exitMultiChoice();
        }
    };
}
