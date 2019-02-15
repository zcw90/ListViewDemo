package com.zcw.listviewdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zcw.base.CommonUtils;
import com.zcw.base.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class MultiList3Activity extends AppCompatActivity {
    private static final String TAG = MultiList3Activity.class.getSimpleName();

    private ListView listView;
    private Multi3Adapter adapter;
    private List<String> data;

    private ImageView imgDelete;

    private TextView tvChoiceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list3);

        init();
    }

    private void init() {
        imgDelete = findViewById(R.id.img_multi_delete3);
        tvChoiceResult = findViewById(R.id.tv_multi_select_result3);
        tvChoiceResult.setMovementMethod(ScrollingMovementMethod.getInstance());

        data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("Data " + (i + 1));
        }

        listView = findViewById(R.id.lv_multi_test3);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
        adapter = new Multi3Adapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList3Activity.this, data.get(position) + " Choice!!!");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList3Activity.this, data.get(position) + " Long click!!!");
                return true;
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getCheckedItemCount() == 0) {
                    CommonUtils.toast(MultiList3Activity.this, R.string.choice_delete_item);
                    return ;
                }

                final CustomDialog dialog = new CustomDialog(MultiList3Activity.this);
                dialog.withButton1("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        // 删除选中的项
                        List<String> deleteData = new ArrayList<>();
                        SparseBooleanArray booleanArray = listView.getCheckedItemPositions();
                        for(int i = 0;i < booleanArray.size(); i++) {
                            int key = booleanArray.keyAt(i);
                            if(booleanArray.get(key)) {
                                deleteData.add(adapter.getItem(key));
                            }
                        }

                        // 退出多选
                        for(int i = 0; i < adapter.getCount(); i++) {
                            listView.setItemChecked(i, false);
                        }

                        listView.clearChoices();
                        data.removeAll(deleteData);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.withButton2("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.withMessage(String.format(getString(R.string.delete_hint), listView.getCheckedItemCount()));
                dialog.show();
            }
        });
    }

    private static class Multi3Adapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        /** 是否进入多选模式 */
        private boolean isChoiceMulti;

        public Multi3Adapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
            this.isChoiceMulti = false;
        }

        public boolean isChoiceMulti() {
            return isChoiceMulti;
        }

        public void setChoiceMulti(boolean choiceMulti) {
            isChoiceMulti = choiceMulti;
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
                holder.imgChecked = view.findViewById(R.id.img_multi_item_content3);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder)view.getTag();
            }

            holder.tvContent.setText(data.get(position));
            holder.imgChecked.setVisibility(isChoiceMulti ? View.VISIBLE : View.GONE);

            return view;
        }

        private static class ViewHolder {
            public TextView tvContent;
            public ImageView imgChecked;
        }
    }

    private AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {

        private TextView tvChoiceAllOrNone;

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            showChoiceResult();
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            View view = LayoutInflater.from(MultiList3Activity.this).inflate(R.layout.multi_select_title, null);
            mode.setCustomView(view);
            initView(view);

            adapter.setChoiceMulti(true);
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
            adapter.setChoiceMulti(false);
            tvChoiceResult.setText("");
            imgDelete.setVisibility(View.GONE);
        }

        private void initView(View view) {
            imgDelete.setVisibility(View.VISIBLE);

            tvChoiceAllOrNone = view.findViewById(R.id.tv_multi_choice_all_none3);
            tvChoiceAllOrNone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tvChoiceAllOrNone.getText().toString().contains(getString(R.string.choice_all))) {
                        tvChoiceAllOrNone.setText(R.string.choice_none2);

                        // 设置全选
                        for(int i = 0; i < adapter.getCount(); i++) {
                            listView.setItemChecked(i, true);
                        }
                    }
                    else {
                        tvChoiceAllOrNone.setText(R.string.choice_all2);

                        // 设置全不选
                        listView.clearChoices();
                    }

                    showChoiceResult();
                    adapter.notifyDataSetChanged();
                }
            });
        }

        /**
         * 显示选择结果
         */
        private void showChoiceResult() {
            // 设置按钮全选/全不选
            if(listView.getCheckedItemCount() == adapter.getCount()) {
                tvChoiceAllOrNone.setText(R.string.choice_none2);
            }
            else {
                tvChoiceAllOrNone.setText(R.string.choice_all2);
            }

            SparseBooleanArray booleanArray = listView.getCheckedItemPositions();
            StringBuilder builder = new StringBuilder();
            for(int i = 0;i < booleanArray.size(); i++) {
                int key = booleanArray.keyAt(i);
                if(booleanArray.get(key)) {
                    builder.append(adapter.getItem(key) + "\n");
                }
            }

            String text = tvChoiceAllOrNone.getText().toString();
            tvChoiceAllOrNone.setText(String.format(text, listView.getCheckedItemCount()));
            tvChoiceResult.setText(builder.toString());
        }
    };
}
