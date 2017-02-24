/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-2-4 下午5:28
 * ********************************************************
 */

package com.zcolin.frame.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zcolin.frame.app.ResultActivityHelper;
import com.zcolin.frame.permission.PermissionHelper;
import com.zcolin.frame.permission.PermissionsResultAction;
import com.zcolin.frame.utils.ActivityUtil;
import com.zcolin.frame.utils.ToastUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llContent;
    private ArrayList<Button> listButton = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        llContent = getView(R.id.ll_content);
        listButton.add(addButton("Http演示"));
        listButton.add(addButton("Db演示"));
        listButton.add(addButton("权限处理和回传数据演示"));

        for (Button btn : listButton) {
            btn.setOnClickListener(this);
        }
    }

    private Button addButton(String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(mActivity);
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        llContent.addView(button, params);
        return button;
    }

    @Override
    public void onClick(View v) {
        if (v == listButton.get(0)) {
            ActivityUtil.startActivity(this, HttpDemoActivity.class);
        } else if (v == listButton.get(1)) {
            ActivityUtil.startActivity(this, DbDemoActivity.class);
        } else if (v == listButton.get(2)) {
            PermissionHelper.requestPermission(mActivity, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, new PermissionsResultAction() {
                @Override
                public void onGranted() {
                    Intent intent = new Intent(mActivity, PermissionAndActivityResultActivity.class);
                    intent.putExtra("data", "传入数据-xxx");
                    startActivityWithCallback(intent, new ResultActivityHelper.ResultActivityListener() {
                        @Override
                        public void onResult(int requestCode, int resultCode, Intent data) {
                            if (resultCode == RESULT_OK) {
                                if (data != null) {
                                    ToastUtil.toastShort(data.getStringExtra("data"));
                                }
                            }
                        }
                    });
                }

                @Override
                public void onDenied(String permission) {
                    ToastUtil.toastShort("请赋予本程序拨打电话和摄像头权限!");
                }
            });
        }
    }
}
