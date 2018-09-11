package com.zcw.listviewdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zcw.base.CommonUtils;
import com.zcw.base.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class MultiList1Activity extends AppCompatActivity {
    private static final String TAG = MultiList1Activity.class.getSimpleName();

    private ListView listView;
    private Multi1Adapter adapter;
    private List<String> data;

    private LinearLayout llMultiChoiceTitle;
    private TextView tvCancel;
    private TextView tvChoiceCount;
    private TextView tvChoiceAllOrNone;
    private ImageView imgDelete;

    private TextView tvChoiceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_list1);

        init();
    }

    private void init() {
        llMultiChoiceTitle = findViewById(R.id.ll_multi_title1);
        tvCancel = llMultiChoiceTitle.findViewById(R.id.tv_multi_choice_cancel1);
        tvChoiceCount = llMultiChoiceTitle.findViewById(R.id.tv_multi_choice_count1);
        tvChoiceAllOrNone = llMultiChoiceTitle.findViewById(R.id.tv_multi_choice_all_none1);
        imgDelete = findViewById(R.id.img_multi_delete1);
        tvChoiceResult = findViewById(R.id.tv_multi_choice_result1);
        tvChoiceResult.setMovementMethod(ScrollingMovementMethod.getInstance());

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
                CommonUtils.toast(MultiList1Activity.this, data.get(position) + " Choice!!!");

                if(adapter.isChoiceMulti()) {
                    showChoiceResult();

                    // 设置按钮全选/全不选
                    if(listView.getCheckedItemCount() == adapter.getCount()) {
                        tvChoiceAllOrNone.setText(R.string.choice_none);
                    }
                    else {
                        tvChoiceAllOrNone.setText(R.string.choice_all);
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtils.toast(MultiList1Activity.this, "Long click " + data.get(position));

                enterMultiChoice(position);
                return true;
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitMultiChoice();
            }
        });

        tvChoiceAllOrNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvChoiceAllOrNone.getText().toString().equals(getString(R.string.choice_all))) {
                    tvChoiceAllOrNone.setText(R.string.choice_none);

                    // 设置全选
                    for(int i = 0; i < adapter.getCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
                else {
                    tvChoiceAllOrNone.setText(R.string.choice_all);

                    // 设置全不选
                    listView.clearChoices();
                }

                showChoiceResult();
                adapter.notifyDataSetChanged();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getCheckedItemCount() == 0) {
                    CommonUtils.toast(MultiList1Activity.this, R.string.choice_delete_item);
                    return ;
                }

                final CustomDialog dialog = new CustomDialog(MultiList1Activity.this);
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

                        listView.clearChoices();
                        data.removeAll(deleteData);

                        exitMultiChoice();
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

    /**
     * 进入多选
     * @param position 默认选中的项
     * @return 由非多选进入多选，返回true；否在返回false。
     */
    private boolean enterMultiChoice(int position) {
        if(!adapter.isChoiceMulti()) {
            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            listView.clearChoices();

            if(position >= 0 && position < adapter.getCount()) {
                listView.setItemChecked(position, true);
            }
            adapter.setChoiceMulti(true);
            adapter.notifyDataSetChanged();

            llMultiChoiceTitle.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            tvChoiceAllOrNone.setText(R.string.choice_all);
            showChoiceResult();
            return true;
        }
        return false;
    }

    /**
     * 退出多选
     * @return 如果是由多选退出到非多选，返回true；否在返回false。
     */
    private boolean exitMultiChoice() {
        if(adapter.isChoiceMulti()) {
            tvChoiceResult.setText("");
            llMultiChoiceTitle.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);

            listView.clearChoices();
            adapter.setChoiceMulti(false);
            adapter.notifyDataSetChanged();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                }
            }, 10);
            return true;
        }
        return false;
    }

    /**
     * 显示选择结果
     */
    private void showChoiceResult() {
        SparseBooleanArray booleanArray = listView.getCheckedItemPositions();
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < booleanArray.size(); i++) {
            int key = booleanArray.keyAt(i);
            if(booleanArray.get(key)) {
                builder.append(adapter.getItem(key) + "\n");
            }
        }

        tvChoiceCount.setText(String.format(getString(R.string.choice_count), listView.getCheckedItemCount()));
        tvChoiceResult.setText(builder.toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(exitMultiChoice())
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class Multi1Adapter extends BaseAdapter {

        private Context context;
        private List<String> data;

        /** 是否进入多选模式 */
        private boolean isChoiceMulti;

        public Multi1Adapter(Context context, List<String> data) {
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
                view = LayoutInflater.from(context).inflate(R.layout.item_multi_list1, parent, false);

                holder.tvContent = view.findViewById(R.id.tv_multi_item_content1);
                holder.imgChecked = view.findViewById(R.id.img_multi_item_content1);
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
}
