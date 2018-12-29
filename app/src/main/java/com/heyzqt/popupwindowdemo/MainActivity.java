package com.heyzqt.popupwindowdemo;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private AlertDialog mTipsDialog;
    private PopupWindow mPopupWindow1;
    private PopupWindow mPopupWindow2;
    private List<PopupSettingItem> mPopupListDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        initTipsDialog();
        showTipsDialog();

        initPopupWindow1();
        initPopupWindow2();
    }

    private void initPopupWindow1() {
        PopupSettingItem item1 = new PopupSettingItem();
        item1.setKey("menu1");
        item1.setText("菜单一");
        PopupSettingItem item2 = new PopupSettingItem();
        item2.setKey("menu2");
        item2.setText("菜单二");
        mPopupListDatas.add(item1);
        mPopupListDatas.add(item2);

        View popupView = LayoutInflater.from(this).inflate(R.layout.layout_popup_settings, null);
        ListView listView = popupView.findViewById(R.id.popup_listview);
        ListViewPopupAdapter adapter = new ListViewPopupAdapter(mPopupListDatas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = mPopupListDatas.get(position).getKey();
                switch (type) {
                    case "menu1":
                        Toast.makeText(MainActivity.this, "menu first", Toast.LENGTH_SHORT).show();
                        break;
                    case "menu2":
                        Toast.makeText(MainActivity.this, "menu second", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        mPopupWindow1 = new PopupWindow(this);
        mPopupWindow1.setContentView(popupView);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable());   //必须设置这个属性，setOutsideTouchable()才有用
        mPopupWindow1.setOutsideTouchable(true);
    }

    private void initPopupWindow2() {
        View view2 = LayoutInflater.from(this).inflate(R.layout.view_popup_2, null);
        mPopupWindow2 = new PopupWindow(this);
        mPopupWindow2.setContentView(view2);
        mPopupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow2.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow2.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow2.setOutsideTouchable(true);
        mPopupWindow2.setAnimationStyle(R.style.PopupWindowAnim);

        TextView delete = view2.findViewById(R.id.delete);
        TextView cancel = view2.findViewById(R.id.cancel);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void initTipsDialog() {
        mTipsDialog = new AlertDialog.Builder(this).create();
        View dialogview = LayoutInflater.from(this).inflate(R.layout.popupwindow_tips, null);
        mTipsDialog.setView(dialogview);
    }

    private void showTipsDialog() {
        if (mTipsDialog != null && mTipsDialog.isShowing()) {
            mTipsDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:  //显示popupwindow1在按钮左侧
                showPopupWindow1Left();
                break;
            case R.id.button2:  //显示popupwindow2
                showPopupWindow2();
                break;
            case R.id.button3:  //显示popupwindow1在按钮中间
                showPopupWindow1Middle();
                break;
            case R.id.button4:  //显示popupwindow1在按钮右侧
                showPopupWindow1Right();
                break;
            case R.id.delete:
                dismissPopupWindow2();
                break;
            case R.id.cancel:
                dismissPopupWindow2();
                break;
        }
    }

    private void showPopupWindow1Left() {
        mPopupWindow1.showAsDropDown(button1, 0, 0);
    }

    private void showPopupWindow1Middle() {
        mPopupWindow1.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int xOff;
        int buttonWidth = button3.getWidth();
        int popupwindowWidth = mPopupWindow1.getContentView().getMeasuredWidth();

        int textWidth = mPopupWindow1.getWidth();

        xOff = buttonWidth / 2 - popupwindowWidth / 2;
        mPopupWindow1.showAsDropDown(button3, xOff, 0);
    }

    private void showPopupWindow1Right() {
        mPopupWindow1.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int xOff;
        int buttonWidth = button3.getWidth();
        int popupwindowWidth = mPopupWindow1.getContentView().getMeasuredWidth();

        xOff = buttonWidth - popupwindowWidth;
        mPopupWindow1.showAsDropDown(button4, xOff, 0);
    }

    private void dismissPopupWindow1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    private void showPopupWindow2() {
        mPopupWindow2.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void dismissPopupWindow2() {
        if (mPopupWindow2 != null && mPopupWindow2.isShowing()) {
            mPopupWindow2.dismiss();
        }
    }
}
