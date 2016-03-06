package com.example.zenghui.overyearspaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zenghui on 16/1/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showToast(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        doOther();
    }

    abstract void initViews();

    abstract void initDatas();

    abstract void doOther();

}
